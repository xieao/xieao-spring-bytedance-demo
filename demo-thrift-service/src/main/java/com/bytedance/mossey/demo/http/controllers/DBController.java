package com.bytedance.mossey.demo.http.controllers;

import com.bytedance.mossey.demo.dal.db.entity.UserInfoDo;
import com.bytedance.mossey.demo.dal.db.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * DBController
 *
 * @author xieao.mossey
 * @version 2024/03/29 18:07
 **/
@Slf4j
@RestController
@RequestMapping("/db")
public class DBController {

    private final UserInfoService userInfoService;

    public DBController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping(value = "user/get")
    public UserInfoDo getUser(@RequestParam("name") String name) throws IOException {
        return userInfoService.getById(1);
    }
}
