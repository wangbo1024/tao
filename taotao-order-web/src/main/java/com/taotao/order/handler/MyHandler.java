package com.taotao.order.handler;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.service.SsoService;
import com.taotao.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyHandler implements HandlerInterceptor {
    @Autowired
    private SsoService ssoService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN", true);
        String url = request.getRequestURL().toString();
        if(StringUtils.isBlank(token)){
            /*用户尚未登录*/
            response.sendRedirect("http://localhost:8088/page/login"+"?redirectUrl="+url);
            return false;
        }
        TaotaoResult userByToken = ssoService.findUserByToken(token);
        if (userByToken.getData() == null){
            /*用户登录状态已过期*/
            response.sendRedirect("http://localhost:8088/page/login"+"?redirectUrl="+url);
            return false;
        }
        TbUser user = (TbUser) userByToken.getData();
        request.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
