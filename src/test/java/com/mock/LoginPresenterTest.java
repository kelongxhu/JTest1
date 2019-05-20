package com.mock;

import com.mock.LoginPresenter.NetworkCallback;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;

/**
 * @author ke.long
 * @since 2019/5/8 10:20
 */
public class LoginPresenterTest {
    @Test
    public void testLogin() {
        UserManager mockUserManager = Mockito.mock(UserManager.class);
        LoginPresenter loginPresenter = new LoginPresenter();
        loginPresenter.setUserManager(mockUserManager);
        //loginPresenter.login("xiaochuang", "xiaochuang password");
        //Mockito.verify(mockUserManager).performLogin("xiaochuang", "xiaochuang password");
        //Mockito.verify(mockUserManager, Mockito.times(3)).performLogin("xiaochuang", "xiaochuang password");

        PasswordValidator mockValidator = Mockito.mock(PasswordValidator.class);
        Mockito.when(mockValidator.verifyPassword("aaaa")).thenReturn(true);
        Mockito.when(mockValidator.verifyPassword("bbbb")).thenReturn(false);
        loginPresenter.setPasswordValidator(mockValidator);
        loginPresenter.login("xiaochuang", "bbbb");

    }

    @Test
    public void testSpyLogin() {
        UserManager mockUserManager = spy(UserManager.class);
        LoginPresenter loginPresenter = new LoginPresenter();
        loginPresenter.setUserManager(mockUserManager);
        loginPresenter.login("xiaochuang", "xiaochuang password");
        Mockito.verify(mockUserManager).performLogin("xiaochuang", "xiaochuang password");
    }

    @Test
    public void testLoginCallbackVersion() {
        UserManager mockUserManager = Mockito.mock(UserManager.class);
        LoginPresenter loginPresenter = new LoginPresenter();
        loginPresenter.setUserManager(mockUserManager);
        PasswordValidator mockValidator = Mockito.mock(PasswordValidator.class);
        Mockito.when(mockValidator.verifyPassword("aaaa")).thenReturn(true);
        loginPresenter.setPasswordValidator(mockValidator);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                //这里可以获得传给performLogin的参数
                Object[] arguments = invocation.getArguments();
                System.out.println("==="+arguments[0]+"x==="+arguments[1]);
                //callback是第三个参数
                NetworkCallback callback = (NetworkCallback) arguments[2];

                callback.onFailure(500, "Server error");
                return 500;
            }
        }).when(mockUserManager).performLogin(anyString(), anyString(), any(NetworkCallback.class));
        loginPresenter.loginCallbackVersion("aaaa","bbbb");

    }
}
