import application.service.IRoleService;
import application.service.IUserService;
import application.service.RoleService;
import application.service.UserService;
import common.UserLoginService;
import common.config.LoginConfiguration;
import data.model.entity.User;
import javax.security.auth.Subject;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {
    private static final IUserService userService = new UserService();
    private static final IRoleService roleService = new RoleService();
    public static void main(String[] args) throws LoginException, IOException {
        // TODO implement DI (dependency injection) to use @PostConstruct
        roleService.initialize();

        User user = new User("kiki", "mojesh");
        userService.create(user);

        Configuration.setConfiguration(new LoginConfiguration());

        UserLoginService userLoginService = new UserLoginService();
        Subject subject = userLoginService.login();

        System.out.println(subject.getPrincipals().iterator().next() + " successfully logged in");
    }

}