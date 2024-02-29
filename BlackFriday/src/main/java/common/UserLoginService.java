package common;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class UserLoginService {
    public Subject login() throws LoginException {
        LoginContext loginContext = new LoginContext("BlackFriday", new ConsoleCallbackHandler());
        loginContext.login();
        return loginContext.getSubject();
    }
}