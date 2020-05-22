package com.taotao.mapper;


import com.taotao.pojo.TbUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface TbUserMapper {

    int addUser(TbUser tbUser);

    int findUserByUsername(String userName);

    int findUserByPhone(String phone);

    int findUserByEmail(String email);

    TbUser findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    @Select("SELECT COUNT(*) FROM tbuser")
    long findAllUser();

    @Update("UPDATE tbuser SET `status` = 1 WHERE id = #{id}")
    int updateUserStatus(Long id);

    @Update("UPDATE tbuser SET `status` = 0")
    int updateAllUserStatus();



}