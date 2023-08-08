package com.waibaoservice.service;

import com.waibaoservice.pojo.KitchenHood;

public interface KitchenService {
    int addTimer(KitchenHood kitchenHood);
    int shutdown(KitchenHood kitchenHood);
    String getEndTime(KitchenHood kitchenHood);
}
