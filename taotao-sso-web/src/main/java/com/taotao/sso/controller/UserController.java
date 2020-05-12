package com.taotao.sso.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SsoService ssoService;

    @RequestMapping(value = "/check/{param}/{type}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkData(@PathVariable("param") String param, @PathVariable("type") Integer type){
        TaotaoResult result = ssoService.checkData(param,type);
        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser tbUser){
        TaotaoResult result = ssoService.addUser(tbUser);
        System.out.println(tbUser);
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username,String password){
        TaotaoResult result = ssoService.findUserByUsernameAndPassword(username,password);
        return result;
    }

    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult queryUserMessage(@PathVariable String token){
        TaotaoResult result = ssoService.findUserByToken(token);
        return result;
    }

    @RequestMapping(value = "/logout/{token}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult logout(@PathVariable String token){
        TaotaoResult result = ssoService.logout(token);
        return result;
    }


}
