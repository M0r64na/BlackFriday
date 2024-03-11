package common;

import application.service.interfaces.IUserService;
import common.module.UserLoginModule;

import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class UserLoginService {
    private final LoginContext loginContext;
    private final IUserService userService;

    @Inject
    public UserLoginService(IUserService userService) {
        this.userService = userService;

        try {
            loginContext = new LoginContext("BlackFriday", new ConsoleCallbackHandler());
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public Subject login() throws LoginException {
        UserLoginModule.setUserService(this.userService);

        loginContext.login();
        return loginContext.getSubject();
    }

    public void logout() throws LoginException {
        loginContext.logout();
    }
}