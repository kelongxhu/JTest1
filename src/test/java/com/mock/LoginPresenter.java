package com.mock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ke.long
 * @since 2019/5/8 10:17
 */
@Slf4j
public class LoginPresenter {
    private UserManager userManager = new UserManager();

    private PasswordValidator passwordValidator;

    public void login(String username, String password) {
        if (username == null || username.length() == 0) { return; }
        if (!passwordValidator.verifyPassword(password)) {
            log.info("verify error,{}", password);
            return;
        }
        userManager.performLogin(username, password);
    }

    public void loginCallbackVersion(String username, String password) {
        if (username == null || username.length() == 0) { return; }
        //假设我们对密码强度有一定要求，使用一个专门的validator来验证密码的有效性
        if (passwordValidator.verifyPassword(password)) { return; }
        //login的结果将通过callback传递回来。
        userManager.performLogin(username, password, new NetworkCallback() {  //<==

            @Override
            public void onSuccess(Object data) {
                log.info("on Success,{}", data);
            }

            @Override
            public void onFailure(int code, String msg) {
                log.info("on Failure,{}", code);
            }
        });
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setPasswordValidator(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    interface NetworkCallback {
        void onSuccess(Object data);

        void onFailure(int code, String msg);
    }
}
