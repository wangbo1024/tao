package com.taotao.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckMessage {
    private static final String checkUsername = "^[a-zA-Z][a-zA-Z0-9]{5,17}$";
//    以字母开头，长度在6~18之间，只能包含字符、数字和下划线
    private static final String checkPassword = "^[a-zA-Z]\\w{5,17}$";
    private static final String checkTelephone = "^1[3|4|5|8][0-9]\\d{8}$";
    private static final String checkEmail = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
//     inputStr:用户输入的信息   ckName:用户输入信息所要匹配的正则表达式
    private static boolean ck(String ckName,String inputStr){
        Pattern pat = Pattern.compile(ckName);
        Matcher mat = pat.matcher(inputStr);
        return mat.matches();
    }

    /**
     * 校验用户名，以字母开头，由字母数字组成的6到17个字符
     * @param username
     * @return
     */
    public static boolean ckUsername(String username){
        return ck(checkUsername,username);
    }

    /**
     * 校验密码，以字母开头，长度在6~18之间，只能包含字符、数字和下划线
     * @param password
     * @return
     */
    public static boolean ckPassword(String password){
        return ck(checkPassword,password);
    }

    /**
     * 校验电话号码
     * @param telephone
     * @return
     */
    public static boolean ckTelephone(String telephone){
        return ck(checkTelephone,telephone);
    }

    /**
     * 校验邮箱地址
     * @param email
     * @return
     */
    public static boolean ckEmail(String email){
        return ck(checkEmail,email);
    }
}
