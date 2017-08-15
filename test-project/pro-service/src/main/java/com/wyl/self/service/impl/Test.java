package com.wyl.self.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wyl.self.mapper.MyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hc-3020-i3
 * @create 2017-07-31 17:15
 * @desc ${DESCRIPTION}
 **/
@Service("test")
public class Test implements com.wyl.self.service.Test {


    @Autowired
    private MyUserMapper myUserMapper;

    @Override
    public String test(String name) throws Exception {



        List<Map<String, String>> list=myUserMapper.query();

        System.out.println(JSONObject.toJSONString(list) +"****************************");
        return JSONObject.toJSONString(list);
    }
}
