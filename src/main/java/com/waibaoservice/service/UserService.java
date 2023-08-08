package com.waibaoservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.waibaoservice.pojo.User;


public interface UserService {
    User userLogin(String json_str) throws JsonProcessingException;
}
