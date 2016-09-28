package com.aura.bluetoothphone.biz;

import java.util.HashMap;

import android.text.TextUtils;

import com.aura.bluetoothphone.bean.ResponseBean;
import com.aura.bluetoothphone.configs.ServerConfig;

/**
 * @Name：所有访问网络 基类
 * @author：Administrator Robin
 * @Description：
 * @date：2016-07-13 16:08
 * @Upauthor：Administrator #Update：2016-07-13 16:08
 * @tags：
 */
public class ProjectBiz {



    /**
     * 注册
     *
     * @author Robin
     * @Title:
     * @Description: ${TODO}
     * @param
     * @return    返回类型
     * @throws
     * @date 2016-07-14 10:44
     */
    public ResponseBean register(String phoneData, String password, String validation, String delates) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", phoneData);              //  用户名
        params.put("password", password);               //  密码
        params.put("verifyCode", validation);           //验证码
        //accountType   用户类型
        // 1：注册手机号时，此参数指定为手机用户；
        // 2：注册邮箱时，此参数指定为邮箱用户；
        // 3：注册普通用户名时，此参数指定为普通用户
        params.put("accountType", delates);

        ResponseBean bean = BaseBiz.sendPost(ServerConfig.SERVER_CONNECT_TIMEOUT, params, ServerConfig.SERVER_REGISTER_URL);

        return bean;
    }

    /**
     * 登录
     *
     * @author Robin
     * @Title:
     * @Description: ${TODO}
     * @param
     * @return    返回类型
     * @throws
     * @date 2016-07-14 10:06
     */
    public ResponseBean login(String account, String password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", account);            //参数 1
        params.put("password", password);           //参数 2

        ResponseBean bean = BaseBiz.sendPost(ServerConfig.SERVER_CONNECT_TIMEOUT, params, ServerConfig.SERVER_LONGIN_URL);
        return bean;
    }


    /**
     * 修改密码
     *
     * @author Robin
     * @Title:
     * @Description: ${TODO}
     * @param
     * @return    返回类型
     * @throws
     * @date 2016-07-15 10:18
     */
    public ResponseBean AlterPas(String userNo,String oldpas, String newpas) {
        if (!TextUtils.isEmpty(userNo)){
            return null;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", userNo);
        params.put("oldPassword", oldpas);
        params.put("newPassword", newpas);
        ResponseBean bean = BaseBiz.sendPost(ServerConfig.SERVER_CONNECT_TIMEOUT, params, ServerConfig.SERVER_CHANGEUSERPASSWORD_URL);
        return bean;

    }
}
