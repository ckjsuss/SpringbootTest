package com.zyzh.controller;

import com.zyzh.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
@RequestMapping(value = "/Test" , produces = "application/json;charset=UTF-8")
@Controller
public class TestController {

    @RequestMapping(value = "/testRedis")
    @ResponseBody
    public String testRedis(){
        Jedis jedis = RedisUtils.getJedis();
        return jedis.ping();
    }
}
