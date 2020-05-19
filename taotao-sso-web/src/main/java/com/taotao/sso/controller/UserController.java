package com.taotao.sso.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.service.SsoService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SsoService ssoService;

    @RequestMapping(value = "/check/{param}/{type}",method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public Object checkData(@PathVariable("param") String param, @PathVariable("type") Integer type,String callback){
        TaotaoResult result = ssoService.checkData(param,type);
        if (StringUtils.isNotBlank(callback)){
            MappingJacksonValue jacksonValue = new MappingJacksonValue(callback);
            jacksonValue.setJsonpFunction(result.getData().toString());
            return jacksonValue;
        }
        return JsonUtils.objectToJson(result);
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
    public TaotaoResult login(String userName, String passWord, HttpServletRequest request, HttpServletResponse response){
        TaotaoResult result = ssoService.findUserByUsernameAndPassword(userName,passWord);
        String token = result.getData().toString();
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        return result;
    }

    @RequestMapping(value = "/token/{token}", produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8",
            method = RequestMethod.GET)
    @ResponseBody
    public String queryUserMessage(@PathVariable String token,String callback){
        TaotaoResult result = ssoService.findUserByToken(token);
        if (StringUtils.isNotBlank(callback)){
            String strResult = callback +"("+ JsonUtils.objectToJson(result)+");";
            return strResult;
        }
        return JsonUtils.objectToJson(result);
    }

    @RequestMapping(value = "/logout/{token}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public Object logout(@PathVariable String token,String callback){
        TaotaoResult result = ssoService.logout(token);
        if (StringUtils.isNotBlank(callback)){
            MappingJacksonValue jacksonValue = new MappingJacksonValue(callback);
            jacksonValue.setJsonpFunction(result.getData().toString());
            return "login";
        }
        return "login";
    }


}
