import application.service.interfaces.*;
import common.config.UserLoginConfiguration;
import common.interfaces.IUserLoginService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import javax.security.auth.login.Configuration;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    /*
    * TODO
    * add menu
    * add dto-s and MAPSTRUCT
    * Docker DB script
     */

    public static void main(String[] args) {
        Weld weld = new Weld();

        try(WeldContainer weldContainer = weld.initialize()) {
            IUserService userService = weldContainer.select(IUserService.class).get();
            IProductService productService = weldContainer.select(IProductService.class).get();
            IOrderService orderService = weldContainer.select(IOrderService.class).get();
            ICampaignService campaignService = weldContainer.select(ICampaignService.class).get();

            Configuration.setConfiguration(new UserLoginConfiguration());
            IUserLoginService userLoginService = weldContainer.select(IUserLoginService.class).get();

            IUserService userServiceStub = (IUserService) UnicastRemoteObject.exportObject(userService, 0);
            IProductService productServiceStub = (IProductService) UnicastRemoteObject.exportObject(productService, 0);
            IOrderService orderServiceStub = (IOrderService) UnicastRemoteObject.exportObject(orderService, 0);
            ICampaignService campaignServiceStub = (ICampaignService) UnicastRemoteObject.exportObject(campaignService, 0);
            IUserLoginService userLoginServiceStub = (IUserLoginService) UnicastRemoteObject.exportObject(userLoginService, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("UserService", userServiceStub);
            registry.bind("ProductService", productServiceStub);
            registry.bind("OrderService", orderServiceStub);
            registry.bind("CampaignService", campaignServiceStub);
            registry.bind("UserLoginService", userLoginServiceStub);

            System.out.println("Server is running...");
        }
        catch (Exception ex) {
            System.out.printf("Requested operation cannot be executed. Reason: %s", ex.getMessage()).println();
        }
    }
}