import application.service.RoleService;
import application.service.UserService;
import application.service.interfaces.IRoleService;
import application.service.interfaces.IUserService;
import common.RolePrincipal;
import common.UserLoginService;
import common.config.UserLoginConfiguration;
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

        Configuration.setConfiguration(new UserLoginConfiguration());

        UserLoginService userLoginService = new UserLoginService();

        Subject subject = userLoginService.login();
        subject.getPrincipals().forEach(p -> {
            if((p instanceof RolePrincipal)) System.out.println(p.getName());
        });
        System.out.println(subject.getPrincipals().iterator().next() + " successfully logged in");
    }
}