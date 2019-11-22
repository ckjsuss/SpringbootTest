package com.zyzh.controller;

import com.zyzh.entity.PZdinfo;
import com.zyzh.service.PZdinfoService;
import com.zyzh.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
@RequestMapping(value = "/Test" , produces = "application/json;charset=UTF-8")
@Controller
public class TestController {

    @Autowired
    private PZdinfoService pZdinfoService;

    @RequestMapping(value = "/testRedis")
    @ResponseBody
    public String testRedis(){
        Jedis jedis = RedisUtils.getJedis();
        return jedis.ping();
    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public String test1(){
        pZdinfoService.insert();
        return "";
    }

}
