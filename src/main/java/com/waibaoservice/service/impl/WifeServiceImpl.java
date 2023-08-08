package com.waibaoservice.service.impl;

import com.waibaoservice.mapper.WifeMapper;
import com.waibaoservice.pojo.Wife;
import com.waibaoservice.service.WifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DJS
 * Date create 1:24 2023/3/16
 * Modified By DJS
 **/

@Service
public class WifeServiceImpl implements WifeService {

    @Autowired
    WifeMapper mapper;

    @Override
    public boolean getWifeStatus(String deviceId) {
        final Wife wife = mapper.selectWifeById(deviceId);
        System.out.println("wife service");
        System.out.println(wife);
        return wife != null;
    }

    @Override
    public int setWife(Wife wife) {
        System.out.println("set wife service");
        return mapper.addWife(wife.getDeviceId(), wife.getAccount(), wife.getPsd());
    }

    @Override
    public int modifyWife(Wife wife) {
        System.out.println("modify wife service");
        return mapper.modifyWife(wife.getDeviceId(), wife.getAccount(), wife.getPsd());
    }

}
