package application.service;

import application.service.interfaces.IRoleService;
import application.service.interfaces.IUserService;
import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import data.model.entity.User;
import application.util.PasswordEncoder;
import data.repository.interfaces.IUserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO map dto-s to entities => MAPSTRUCT
public class UserService implements IUserService {
//    private static final UserRepository userRepository = new UserRepository();
//    private static final IRoleService roleService = new RoleService();
    private final IUserRepository userRepository;
    private final IRoleService roleService;

    @Inject
    public UserService(IUserRepository userRepository, IRoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public void create(String username, String password) {
        String encodedPassword = PasswordEncoder.encodePassword(password);
        Role clientRole = roleService.findByName(RoleName.CLIENT);
        User user = new User(username, encodedPassword);
        user.getRoles().add(clientRole);

        userRepository.create(user);
    }

    @Override
    public User update(String username, String password) {
        User user = this.getByUsername(username);
        this.updatePassword(user);

        return userRepository.update(user);
    }

    @Override
    public Optional<User> getById(UUID id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public void initialize() {
        if(!userRepository.getAll().isEmpty()) return;

        this.create("employee", "employee");

        User employee = this.getByUsername("employee");
        employee.getRoles().add(roleService.findByName(RoleName.EMPLOYEE));

        userRepository.update(employee);
    }

    private void updatePassword(User user) {
        String encodedPassword = this.getById(user.getId()).orElseThrow().getPassword();

        if(!encodedPassword.equals(user.getPassword())) {
            encodedPassword = PasswordEncoder.encodePassword(user.getPassword());
            user.setPassword(encodedPassword);
        }
    }
}