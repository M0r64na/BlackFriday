package common;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class UserLoginService {
    private static final LoginContext loginContext;

    static {
        try {
            loginContext = new LoginContext("BlackFriday", new ConsoleCallbackHandler());
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public Subject login() throws LoginException {
        loginContext.login();
        return loginContext.getSubject();
    }

    public void logout() throws LoginException {
        loginContext.logout();
    }
}