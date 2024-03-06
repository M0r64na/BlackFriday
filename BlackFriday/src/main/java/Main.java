import application.service.*;
import application.service.interfaces.*;
import common.RolePrincipal;
import common.UserLoginService;
import common.config.UserLoginConfiguration;
import data.model.entity.User;
import javax.security.auth.Subject;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    /*
    * TODO
    * add Product entity + functionality
    * add authorization using Subject, RolePrincipal + @ (incl validator / interceptor / aspect)
    * apply DTO pattern
    * apply DI using DI framework + @inject
    * @Singleton, @PostConstruct on role init
    * add menu + program running until exited in separate class
    * client-server part => separate code
    * Docker DB script
    * prettify the console output
    *
    * !!! remove constructors in repos
     */
    private static final IUserService userService = new UserService();
    private static final IRoleService roleService = new RoleService();
    private static final IStatusService statusService = new StatusService();
    private static final IProductService productService = new ProductService();
    private static final IOrderService orderService = new OrderService();

    public static void main(String[] args) throws LoginException, IOException {
        // TODO implement DI (dependency injection) to use @PostConstruct
        roleService.initialize();
        statusService.initialize();

        userService.create("kiki", "mojesh");

        Configuration.setConfiguration(new UserLoginConfiguration());

        UserLoginService userLoginService = new UserLoginService();

        Subject subject = userLoginService.login();
        subject.getPrincipals().forEach(p -> {
            if((p instanceof RolePrincipal)) System.out.println(p.getName());
        });
        System.out.println(subject.getPrincipals().iterator().next() + " successfully logged in");

        productService.create("test product 1", "test descr", 50,
                8, 9, "kiki");
        productService.create("test product 2", "test descr", 50,
                8, 9, "kiki");
        Map<String, Integer> productNamesAndQuantities = new HashMap<>();
        productNamesAndQuantities.put("test product 1", 12);
        productNamesAndQuantities.put("test product 2", 30);
        orderService.create("kiki", productNamesAndQuantities);
    }
}