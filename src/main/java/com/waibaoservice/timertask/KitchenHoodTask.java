package com.waibaoservice.timertask;

import com.waibaoservice.mapper.GasMapper;
import com.waibaoservice.mapper.KitchenMapper;
import com.waibaoservice.pojo.GasStove;
import com.waibaoservice.pojo.KitchenHood;
import com.waibaoservice.utils.BeanUtils.SpringContextUtils;
import com.waibaoservice.utils.DateUtils.DateUtils;
import com.waibaoservice.utils.MqttUtils.MqttUtils;
import com.waibaoservice.utils.WeiXinUtils.WeiXinRequestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author DJS
 * Date create 19:37 2023/3/14
 * Modified By DJS
 **/

public class KitchenHoodTask implements Runnable{
    public static List<KitchenHood> kitchenHoods;
    public static boolean loopCondition = true;
    public static boolean mapperCondition = false;

    // 获取mapper实例
    KitchenMapper mapper = SpringContextUtils.getBean(KitchenMapper.class);

    public KitchenHoodTask() {}

    @Override
    public void run() {
        System.out.println("KitchenTask start");
        while (loopCondition) {
            if (kitchenHoods == null && !mapperCondition) {
                kitchenHoods = mapper.selectAll();
                mapperCondition = true;
            }
            // 先更新缓存
            kitchenHoods = mapper.selectAll();
            if (kitchenHoods != null) {
                for (KitchenHood kitchenHood : kitchenHoods) {
                    synchronized (this) {
                        Date currentDate = new Date();
                        String endTime = kitchenHood.getEnd_time();
                        if (endTime != null) {
                            Date endDate;
                            endDate = DateUtils.parseDateStr(endTime);
                            // 已经过时的任务
                            if (currentDate.after(endDate)) {
                                // 从数据库移除
                                int ret = mapper.removeTimer(kitchenHood.getDevice_id());
                                if (ret == 1) {
                                    WeiXinRequestUtils.pushMessage(kitchenHood.getOpenId(), "定时结束");
                                    // 发送Mqtt
                                    new Thread(() -> {
                                        MqttUtils.setTopic("/test/djs/" + kitchenHood.getDevice_id() + "/taskend/kitchen_hood");
                                        MqttUtils.publish("1");
                                    }).start();
                                }
                                else {
                                    // 更新缓存
                                    kitchenHoods = mapper.selectAll();
                                    break;
                                }
                                kitchenHoods.remove(kitchenHood);
                                break;
                            }
                        }

                    }
                }
            }
        }
    }
}
