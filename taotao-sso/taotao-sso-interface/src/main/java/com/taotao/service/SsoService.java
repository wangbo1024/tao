package com.taotao.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface SsoService {
    TaotaoResult addUser(TbUser tbUser);

    TaotaoResult checkData(String param, Integer type);

    TaotaoResult findUserByUsernameAndPassword(String username, String password);

    TaotaoResult findUserByToken(String token);

    TaotaoResult logout(String token);

    long findAllUser();
}
