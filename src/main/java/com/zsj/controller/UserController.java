package com.zsj.controller;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zsj on 2017/4/10.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/getUserByName")
    public JSONObject getUserByName(@RequestBody JSONObject object){
        logger.info("传入参数222: "+object);
        String username = object.getString("username");
        return userDao.findUserByName(username);

    }


}
