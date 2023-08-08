package com.waibaoservice.timertask;

import com.waibaoservice.mapper.GasMapper;
import com.waibaoservice.pojo.GasStove;
import com.waibaoservice.utils.BeanUtils.SpringContextUtils;
import com.waibaoservice.utils.DateUtils.DateUtils;
import com.waibaoservice.utils.MqttUtils.MqttUtils;
import com.waibaoservice.utils.WeiXinUtils.WeiXinRequestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author DJS
 * Date create 3:33 2023/3/10
 * Modified By DJS
 **/

// 任务类定时操作
public class GasTask implements Runnable{
    public static List<GasStove> gases;
    public static boolean loopCondition = true;
    public static boolean mapperCondition = false;

    // 获取mapper实例
    GasMapper mapper = SpringContextUtils.getBean(GasMapper.class);

    public GasTask() {}

    @Override
    public void run() {
        System.out.println("GasTask start");
        while (loopCondition) {
            if (gases == null && !mapperCondition) {
                gases = mapper.selectAll();
                mapperCondition = true;
            }
            // 先更新缓存
            gases = mapper.selectAll();
            if (gases != null) {
                for (GasStove gas : gases) {
                    Date currentDate = new Date();
                    String endTime = gas.getEnd_time();
                    if (endTime != null) {
                        Date endDate = DateUtils.parseDateStr(endTime);
                        // 已经过时的任务
                        if (currentDate.after(endDate)) {
                            // 从数据库移除
                            int ret = mapper.removeTimer(gas.getDevice_id());
                            if (ret == 1) {
                                WeiXinRequestUtils.pushMessage(gas.getOpenId(), "定时结束");
                                // 发送Mqtt
                                new Thread(() -> {
                                    MqttUtils.setTopic("/test/djs/" + gas.getDevice_id() + "/taskend/gas_stove");
                                    MqttUtils.publish("1");
                                }).start();
                            }
                            else {
                                // 更新缓存
                                gases = mapper.selectAll();
                                break;
                            }
                            gases.remove(gas);
                            break;

                        }
                    }

                }
            }
        }
    }
}

