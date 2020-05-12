package com.taotao.mapper;


import com.taotao.pojo.TbUser;
import org.apache.ibatis.annotations.Param;

public interface TbUserMapper {

    int addUser(TbUser tbUser);

    int findUserByUsername(String userName);

    int findUserByPhone(String phone);

    int findUserByEmail(String email);

    TbUser findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}