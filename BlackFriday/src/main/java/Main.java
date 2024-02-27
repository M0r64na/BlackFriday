import application.service.IUserService;
import application.service.UserService;
import common.LoginService;
import common.config.LoginConfiguration;
import data.model.entity.User;

import javax.security.auth.Subject;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginException;

public class Main {
    private static final IUserService userService = new UserService();
    public static void main(String[] args) throws LoginException {
        User user = new User("kiki", "mojesh");
        userService.create(user);

        Configuration.setConfiguration(new LoginConfiguration());

        LoginService loginService = new LoginService();
        Subject subject = loginService.login();

        System.out.println(subject.getPrincipals().iterator().next() + " successfully logged in");
    }
}