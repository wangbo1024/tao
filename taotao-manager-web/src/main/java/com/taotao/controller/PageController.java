package com.taotao.controller;

import com.taotao.service.JedisClient;
import com.taotao.service.SsoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(value = "后台首页",tags = "PageController",description = "后台首页")
public class PageController {
    @Autowired
    private SsoService ssoService;
    @Autowired
    private JedisClient jedisClient;

    @RequestMapping("/")
    @ApiOperation(value = "首页展示",notes = "首页展示")
    public String showIndex(Model model){
        long countUser = ssoService.findAllUser();
        model.addAttribute("countUser",countUser);
        //每日新增用户数
        String new_adduser_num = jedisClient.get("NEW_ADDUSER_NUM");
        model.addAttribute("new_adduser_num",new_adduser_num);
        //每日用活
        String user_login_status = jedisClient.get("USER_LOGIN_STATUS");
        model.addAttribute("user_login_status",user_login_status);
        return "index";
    }
}
