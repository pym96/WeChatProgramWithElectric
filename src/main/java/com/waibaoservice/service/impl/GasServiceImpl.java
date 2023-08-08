package com.waibaoservice.service.impl;

import com.waibaoservice.mapper.GasMapper;
import com.waibaoservice.pojo.GasStove;
import com.waibaoservice.service.GasService;
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
public class GasServiceImpl implements GasService {

    @Autowired
    GasMapper mapper;

    @Override
    public int addTimer(GasStove gasStove) {
        GasStove g = mapper.selectGasStoveById(gasStove.getDevice_id());
        String endTime = DateUtils.getEndTimeStr(new Date(), gasStove.getPositive_time(), ":");
        System.out.println(endTime);
        // 没有定时任务
        int ret;
        if (g == null) {
            ret = mapper.addTimer(
                        gasStove.getDevice_id(),
                        gasStove.getOpenId(),
                        endTime);
        }
        else {
            ret = mapper.updateTimer(
                    gasStove.getDevice_id(),
                    endTime);
        }
        if (ret == 1) {
            System.out.println("添加/更新定时任务成功");
            new Thread(() -> {
                String topic = "/test/djs/" + gasStove.getDevice_id() + "/mini";
                MqttUtils.setTopic(topic);
                String[] times = endTime.split(":");
                int hour = Integer.parseInt(times[0]);
                int minute = Integer.parseInt(times[1]);
                String m = String.valueOf(hour * 60 + minute);
                String msg = "1:2:" + m;
                MqttUtils.publish(msg);
            }).start();
        }
        else System.out.println("添加/更新定时任务失败");
        return ret;
    }

    @Override
    public int shutdown(GasStove gasStove) {
        int ret = mapper.removeTimer(gasStove.getDevice_id());
        if (ret == 1) System.out.println("任务结束");
        else System.out.println("无任务");
        int status = gasStove.getStatus();
        // 推送消息
        if (status == 1) WeiXinRequestUtils.pushMessage(gasStove.getOpenId(), "燃气灶关机");
        else WeiXinRequestUtils.pushMessage(gasStove.getOpenId(), "燃气灶开机");
        // 发送Mqtt
        new Thread(() -> {
            String msg = status == 1 ? "3:2:0" : "4:2:0";
            String topic = "/test/djs/" + gasStove.getDevice_id() + "/mini";
            MqttUtils.setTopic(topic);
            MqttUtils.publish(msg);
        }).start();

        return status;
    }

    @Override
    public String getEndTime(GasStove gasStove) {
        System.out.println(gasStove);
        GasStove g = mapper.selectEndTimeById(gasStove.getDevice_id());
        if (g == null) return "";
        else {
            if (g.getEnd_time() == null) return "";
        }
        System.out.println(g.getEnd_time());
        return g.getEnd_time();
    }
}
