package com.waibaoservice.timertask;

import com.waibaoservice.mapper.DeviceMapper;
import com.waibaoservice.mapper.GasMapper;
import com.waibaoservice.mapper.KitchenMapper;
import com.waibaoservice.pojo.Device;
import com.waibaoservice.pojo.GasStove;
import com.waibaoservice.pojo.KitchenHood;
import com.waibaoservice.utils.BeanUtils.SpringContextUtils;
import com.waibaoservice.utils.MqttUtils.MqttCallBackAdapter;
import com.waibaoservice.utils.MqttUtils.MqttUtils;
import com.waibaoservice.utils.WeiXinUtils.WeiXinRequestUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.LinkedList;
import java.util.List;

/**
 * @author DJS
 * Date create 17:17 2023/3/16
 * Modified By DJS
 **/
// 1412412512352352345634
public class MqttTask {
    final static DeviceMapper deviceMapper = SpringContextUtils.getBean(DeviceMapper.class);
    final static KitchenMapper kitchenMapper = SpringContextUtils.getBean(KitchenMapper.class);
    final static GasMapper gasMapper = SpringContextUtils.getBean(GasMapper.class);
    final static String topic = "/test/djs/#";
    final private static List<String> deviceIds = new LinkedList<>();

    public MqttTask() {
        List<Device> devices = deviceMapper.selectAllDevices();
        for (Device device : devices) {
            deviceIds.add(device.getDevice_id());
        }
    }

    private static String[] getDeviceIdByTopic(String topic) {
        return topic.split("/");
    }

    public static void task() {
        System.out.println("Mqtt task start");
        MqttUtils.subscribe(new MqttCallBackAdapter() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.out.println("connection lost");
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                String mqttMsg = new String(mqttMessage.getPayload());
                String[] topics = getDeviceIdByTopic(s);
                String[] msg = mqttMsg.split(":");
                System.out.println("mqttMsg : " + mqttMsg);
                String frame_head = msg[0];
                String device_type = msg[1];
                String device_id = topics[2];
                // 开机
                if (frame_head.equals("2")) {
                    System.out.println("设备需要开机");
                    String name = device_type.equals("1") ? "油烟机" : "燃气灶";
                    new Thread(() -> {
                        Device device = deviceMapper.getDeviceById(device_id);
                        if (device != null) {
                            WeiXinRequestUtils.pushMessage(device.getOpenid(), name + "开机");
                            if (name.equals("燃气灶")) {
                                GasStove gas = gasMapper.selectGasStoveById(device_id);
                                if (gas == null) gasMapper.addTimer(device_id, device.getOpenid(), null);
                            }
                            else {
                                KitchenHood kitchenHood = kitchenMapper.selectKitChenHoodById(device_id);
                                if (kitchenHood == null) kitchenMapper.addTimer(device_id, device.getOpenid(), null);
                            }
                        }
                    }).start();
                }
                // 定时结束
                if (frame_head.equals("1") || frame_head.equals("3")) {
                    String name = device_type.equals("1") ? "油烟机" : "燃气灶";
                    new Thread(() -> {
                        Device device = deviceMapper.getDeviceById(device_id);
                        if (device != null) {
                            WeiXinRequestUtils.pushMessage(device.getOpenid(), "定时结束");
                            if (name.equals("燃气灶")) {
                                gasMapper.removeTimer(device_id);
                            }
                            else {
                                kitchenMapper.removeTimer(device_id);
                            }
                        }
                    }).start();
                }
                // 警报
                if (frame_head.equals("4")) {
                    new Thread(() -> {
                        Device device = deviceMapper.getDeviceById(device_id);
                        if (device != null) WeiXinRequestUtils.pushMessage(device.getOpenid(), "故障");
                    }).start();
                }
                if (frame_head.equals("5")) {
                    new Thread(() -> {
                        Device device = deviceMapper.getDeviceById(device_id);
                        if (device != null) WeiXinRequestUtils.pushMessage(device.getOpenid(), "报警");
                    }).start();
                }
            }
        }, topic);
    }
}
