package common;

import application.service.interfaces.IUserService;
import common.interfaces.IUserLoginService;
import common.module.UserLoginModule;
import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class UserLoginService implements IUserLoginService {
    private final LoginContext loginContext;
    private final IUserService userService;

    @Inject
    public UserLoginService(IUserService userService) throws LoginException {
        this.userService = userService;
        this.loginContext = new LoginContext("BlackFriday", new ConsoleCallbackHandler());
    }

    @Override
    public void login() throws LoginException {
        UserLoginModule.setUserService(this.userService);

        loginContext.login();
    }

    @Override
    public void logout() throws LoginException {
        loginContext.logout();
    }

    @Override
    public Subject getSubject() {
        return this.loginContext.getSubject();
    }
}