import application.service.*;
import application.service.interfaces.*;
import common.HasRoleEmployeeInterceptor;
import common.RolePrincipal;
import common.UserLoginService;
import common.config.UserLoginConfiguration;
import common.module.UserLoginModule;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.login.Configuration;
import java.util.HashMap;
import java.util.Map;

public class Main {
    /*
    * TODO
    * apply DTO pattern
    * apply DI using DI framework + @inject
    * @Singleton, @PostConstruct on role init
    * add menu + program running until exited in separate class
    * client-server part => separate code
    * Docker DB script
    * prettify the console output
     */

    public static void main(String[] args) throws Throwable {
        Weld weld = new Weld();

        try(WeldContainer weldContainer = weld.initialize()) {
            IUserService userService = weldContainer.select(IUserService.class).get();
            IRoleService roleService = weldContainer.select(IRoleService.class).get();
            IStatusService statusService = weldContainer.select(IStatusService.class).get();
            IProductService productService = weldContainer.select(IProductService.class).get();
            IOrderService orderService = weldContainer.select(IOrderService.class).get();
            ICampaignService campaignService = weldContainer.select(ICampaignService.class).get();

            roleService.initialize();
            userService.initialize();
            statusService.initialize();

            userService.create("kiki", "mojesh");

            Configuration.setConfiguration(new UserLoginConfiguration());
            UserLoginService userLoginService = weldContainer.select(UserLoginService.class).get();

            Subject subject = userLoginService.login();

            subject.getPrincipals().forEach(p -> {
                if((p instanceof RolePrincipal)) System.out.println(p.getName());
            });
            System.out.println(subject.getPrincipals().iterator().next() + " successfully logged in");

            productService.create("test product 1", "test descr", 50,
                    8, 90, "kiki");
            productService.create("test product 2", "test descr", 50,
                    8, 98, "kiki");

            Map<String, Double> productNamesAndDiscountPercentages = new HashMap<>();
            productNamesAndDiscountPercentages.put("test product 1", 12.5);
            productNamesAndDiscountPercentages.put("test product 2", 15.0);

            HasRoleEmployeeInterceptor interceptor = new HasRoleEmployeeInterceptor(campaignService);
            // won't work => role CLIENT
            // will work => role EMPLOYEE

            interceptor.invoke(campaignService,
                    CampaignService.class.getMethod("startCampaign", String.class, Map.class),
                    new Object[] {subject.getPrincipals().iterator().next().getName(), productNamesAndDiscountPercentages});


            Map<String, Integer> productNamesAndQuantities = new HashMap<>();
            productNamesAndQuantities.put("test product 1", 12);
            productNamesAndQuantities.put("test product 2", 30);
            orderService.create("kiki", productNamesAndQuantities);

            // userLoginService.logout();

            interceptor.invoke(campaignService,
                    CampaignService.class.getMethod("stopCurrentCampaign", String.class),
                    new Object[] {subject.getPrincipals().iterator().next().getName()});
        }
        catch (Exception ex) {
            System.out.printf("Requested operation cannot be executed. Reason: %s", ex.getMessage()).println();
        }
        // TODO implement DI (dependency injection) to use @PostConstruct
    }
}