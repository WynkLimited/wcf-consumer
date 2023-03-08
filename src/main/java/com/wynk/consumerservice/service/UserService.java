package com.wynk.consumerservice.service;

import com.google.gson.reflect.TypeToken;
import com.wynk.consumerservice.constants.MusicConstants;
import com.wynk.consumerservice.dao.UserDao;
import com.wynk.consumerservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserFromUid(String uid) {
        return userDao.getUserByUid(uid);
    }

    public void updateUser(String uid, Map<String, Object> paramMap) {
        userDao.updateUser(uid, paramMap);
    }
}
