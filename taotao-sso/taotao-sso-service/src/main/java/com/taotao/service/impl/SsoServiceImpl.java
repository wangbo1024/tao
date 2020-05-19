package com.taotao.service.impl;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.service.JedisClient;
import com.taotao.service.SsoService;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class SsoServiceImpl implements SsoService {
    private final int max = 3600;
    private final int min = 1800;
    public static int num = 0;
    public static int register = 0;
    private Random random = new Random();
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;;

    /**
     * 用户输入信息的校验
     * @param param 用户输入的值 用户名、电话号码、邮箱地址
     * @param type 数据类型：1--username、2--telephone、3--Email
     * @return
     */
    @Override
    public TaotaoResult checkData(String param, Integer type) {
        int i = 0;
        if (type == 1){
            i = tbUserMapper.findUserByUsername(param);
        } else if (type == 2){
            i = tbUserMapper.findUserByPhone(param);
        } else if (type == 3){
            i = tbUserMapper.findUserByEmail(param);
        } else {
            return TaotaoResult.build(500,"输入信息有误，请先校验");
        }
        if (i != 0){
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);
    }

    /**
     * 用户注册
     * @param tbUser
     * @return
     */
    @Override
    public TaotaoResult addUser(TbUser tbUser) {
        Date date = new Date();
        if (StringUtils.isBlank(tbUser.getUserName())){
//            用户名username为空
            return TaotaoResult.build(500,"用户名不能为空");
        }
        if (StringUtils.isBlank(tbUser.getPassWord())){
            return TaotaoResult.build(400,"密码不能为空");
        }
        if (StringUtils.isBlank(tbUser.getPhone())){
            return TaotaoResult.build(400,"电话号码不能为空");
        }
        if (StringUtils.isBlank(tbUser.getEmail())){
            return TaotaoResult.build(400,"邮箱地址不能为空");
        }
        TaotaoResult result = checkData(tbUser.getUserName(),1);
        if (!(boolean)result.getData()){
            return TaotaoResult.build(500,"用户名已存在");
        }
        result = checkData(tbUser.getPhone(),2);
        if (!(boolean)result.getData()){
            return TaotaoResult.build(500,"该手机号已被使用");
        }
        result = checkData(tbUser.getEmail(),3);
        if (!(boolean)result.getData()){
            return TaotaoResult.build(500,"该邮箱地址已被使用");
        }
        String password = DigestUtils.md5DigestAsHex(tbUser.getPassWord().getBytes());
        tbUser.setPassWord(password);
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        tbUser.setStatus(0);
        int i = tbUserMapper.addUser(tbUser);
        if (i <= 0){
            return TaotaoResult.build(400,"注册失败");
        }
        jedisClient.incr("NEW_ADDUSER_NUM");
        if (register == 0){
            jedisClient.expire("NEW_ADDUSER_NUM",60);
            register = 1;
        }
        return TaotaoResult.ok();
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public TaotaoResult findUserByUsernameAndPassword(String username, String password) {
        int seconds = random.nextInt(max)%(max-min+1) + min;
        String token =  UUID.randomUUID().toString();
        token = token.replace("-","");
        String mdsPass = DigestUtils.md5DigestAsHex(password.getBytes());
        TbUser tbUser = tbUserMapper.findUserByUsernameAndPassword(username,mdsPass);
        if (tbUser == null){
            jedisClient.set("USER_INFO"+":"+ token, "null");
            jedisClient.expire("USER_INFO"+":"+ token,seconds);
            return TaotaoResult.build(400,"登录失败，用户名或密码错误","null");
        }
        if (tbUser.getStatus() == 0){
            tbUser.setStatus(1);
            //修改MySQL数据库中当天用户的登录状态用以统计当天日活
            tbUserMapper.updateUserStatus(1,tbUser.getId());
            //每登录一个用户当天日活就加一，记录每日用活
            jedisClient.incr("USER_LOGIN_STATUS");
            if (num == 0){
                jedisClient.expire("USER_LOGIN_STATUS",60);
                num = 1;
            }
        }
        //将用户登录信息存入redis缓存中
        jedisClient.set("USER_INFO"+":"+ token, JsonUtils.objectToJson(tbUser));
        jedisClient.expire("USER_INFO"+":"+ token,seconds);
        return TaotaoResult.ok(token);
    }

    /**
     * 通过token查询用户信息
     * @param token
     * @return
     */
    @Override
    public TaotaoResult findUserByToken(String token) {
        String json = jedisClient.get("USER_INFO"+":"+ token);
        if (StringUtils.isBlank(json) || json.equals("null")){
            return TaotaoResult.build(400,"登录已失效，请重新登录");
        }
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        tbUser.setPassWord(null);
        return TaotaoResult.ok(tbUser);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @Override
    public TaotaoResult logout(String token) {
        jedisClient.del("USER_INFO"+":"+ token);
        return TaotaoResult.ok();
    }

    /**
     * 统计用户总数
     * @return
     */
    @Override
    public long findAllUser() {
        long countUser = tbUserMapper.findAllUser();
        return countUser;
    }

    public void updateUserStatus(){
        tbUserMapper.updateAllUserStatus(0);
        register = 0;
        num = 0;
    }
}
