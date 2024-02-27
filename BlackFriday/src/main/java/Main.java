import application.service.IUserService;
import application.service.UserService;
import data.model.entity.User;

public class Main {
    private static final IUserService userService = new UserService();

    public static void main(String[] args) {
        User user = new User("test", "test");
        userService.create(user);
    }
}