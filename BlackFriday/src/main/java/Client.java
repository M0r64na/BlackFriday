import application.service.interfaces.ICampaignService;
import application.service.interfaces.IOrderService;
import application.service.interfaces.IProductService;
import application.service.interfaces.IUserService;
import common.HasRoleEmployeeInterceptor;
import common.interfaces.IUserLoginService;
import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();

            IUserService userService = (IUserService) registry.lookup("UserService");
            IProductService productService = (IProductService) registry.lookup("ProductService");
            IOrderService orderService = (IOrderService) registry.lookup("OrderService");
            ICampaignService campaignService = (ICampaignService) registry.lookup("CampaignService");
            IUserLoginService userLoginService = (IUserLoginService) registry.lookup("UserLoginService");
            HasRoleEmployeeInterceptor interceptor = new HasRoleEmployeeInterceptor(userLoginService);

            // to test - username: employee, password: employee
            // TODO problem to fix => console callback handler - input username and password from client console
            userLoginService.login();
            String username = userLoginService.getSubject().getPrincipals().iterator().next().getName();
            System.out.println(username + " successfully logged in");

            interceptor.invoke(productService,
                    IProductService.class.getMethod("createProduct",
                            String.class, String.class, int.class, BigDecimal.class, BigDecimal.class, String.class),
                            new Object[] {"test product 1", "test descr", 50, BigDecimal.valueOf(8), BigDecimal.valueOf(90), username});

            userLoginService.logout();
            System.out.println("Successful logout :)");
        }
        catch (Throwable ex) {
            System.out.printf("Requested operation cannot be executed. Reason: %s", ex.getMessage()).println();
        }
    }
}