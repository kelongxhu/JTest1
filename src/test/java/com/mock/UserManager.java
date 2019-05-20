package com.mock;

import com.mock.LoginPresenter.NetworkCallback;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ke.long
 * @since 2019/5/8 10:18
 */
@Slf4j
public class UserManager {
    public void performLogin(String userName, String password) {
        log.info("login,{},{}", userName, password);
        System.out.println("login,+++++" + userName);
    }

    public void performLogin(String userName, String password, NetworkCallback callback){
        if ("succss".equals(userName)){
            callback.onSuccess(userName);
        }else {
            callback.onFailure(1,password);
        }
    }



}
