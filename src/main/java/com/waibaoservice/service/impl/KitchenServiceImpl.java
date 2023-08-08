package com.waibaoservice.service.impl;

import com.waibaoservice.mapper.KitchenMapper;
import com.waibaoservice.pojo.KitchenHood;
import com.waibaoservice.service.KitchenService;
import com.waibaoservice.utils.DateUtils.DateUtils;
import com.waibaoservice.utils.MqttUtils.MqttUtils;
import com.waibaoservice.utils.WeiXinUtils.WeiXinRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author DJS
 * Date create 3:14 2023/3/16
 * Modified By DJS
 **/

@Service
public class KitchenServiceImpl implements KitchenService {

    @Autowired
    KitchenMapper mapper;

    @Override
    public int addTimer(KitchenHood kitchenHood) {
        KitchenHood k = mapper.selectKitChenHoodById(kitchenHood.getDevice_id());
        String endTime = DateUtils.getEndTimeStr(new Date(), kitchenHood.getPositive_time(), ":");
        System.out.println(endTime);
        // 没有定时任务
        int ret;
        if (k == null) {
            ret = mapper.addTimer(
                    kitchenHood.getDevice_id(),
                    kitchenHood.getOpenId(),
                    endTime);
        } else {
            ret = mapper.updateTimer(
                    kitchenHood.getDevice_id(),
                    endTime);
        }
        if (ret == 1) {
            System.out.println("添加/更新定时任务成功");
            new Thread(() -> {
                String topic = "/test/djs/" + kitchenHood.getDevice_id() + "/mini";
                MqttUtils.setTopic(topic);
                String[] times = endTime.split(":");
                int hour = Integer.parseInt(times[0]);
                int minute = Integer.parseInt(times[1]);
                String m = String.valueOf(hour * 60 + minute);
                String msg = "1:1:" + m;
                MqttUtils.publish(msg);
            }).start();
        } else System.out.println("添加/更新定时任务失败");
        return ret;
    }

    @Override
    public int shutdown(KitchenHood kitchenHood) {
        int ret = mapper.removeTimer(kitchenHood.getDevice_id());
        if (ret == 1) System.out.println("任务结束");
        else System.out.println("无任务");
        int status = kitchenHood.getStatus();
        if (status == 1) WeiXinRequestUtils.pushMessage(kitchenHood.getOpenId(), "油烟机关机");
        else WeiXinRequestUtils.pushMessage(kitchenHood.getOpenId(), "油烟机开机");
        new Thread(() -> {
            String msg = status == 1 ? "3:1" : "4:1";
            String topic = "/test/djs/" + kitchenHood.getDevice_id() + "/mini";
            MqttUtils.setTopic(topic);
            MqttUtils.publish(msg);
        }).start();
        return status;
    }

    @Override
    public String getEndTime(KitchenHood kitchenHood) {
        KitchenHood k = mapper.selectEndTimeById(kitchenHood.getDevice_id());
        if (k == null) return "";
        else {
            if (k.getEnd_time() == null) return "";
        }
        System.out.println(k.getEnd_time());
        return k.getEnd_time();
    }
}
