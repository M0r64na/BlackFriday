package application.service;

import application.service.interfaces.IRoleService;
import application.service.interfaces.IUserService;
import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import data.model.entity.User;
import application.util.PasswordEncoder;
import data.repository.interfaces.IUserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO map dto-s to entities => MAPSTRUCT
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleService roleService;

    @Inject
    public UserService(IUserRepository userRepository, IRoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public void createUser(String username, String password) throws RemoteException {
        String encodedPassword = PasswordEncoder.encodePassword(password);
        Role clientRole = this.roleService.findRoleByName(RoleName.CLIENT);
        User user = new User(username, encodedPassword);
        user.getRoles().add(clientRole);

        this.userRepository.create(user);
    }

    @Override
    public User updateUser(String username, String password) {
        User user = this.getUserByUsername(username);
        this.updatePassword(user);

        return this.userRepository.update(user);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return this.userRepository.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.getAll();
    }

    @Override
    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getByUsername(username);
    }

    @Override
    @PostConstruct
    public void initializeUsers() throws RemoteException {
        if(!this.userRepository.getAll().isEmpty()) return;

        this.createUser("employee", "employee");

        User employee = this.getUserByUsername("employee");
        employee.getRoles().add(this.roleService.findRoleByName(RoleName.EMPLOYEE));

        this.userRepository.update(employee);
    }

    private void updatePassword(User user) {
        String encodedPassword = this.getUserById(user.getId()).orElseThrow().getPassword();

        if(!encodedPassword.equals(user.getPassword())) {
            encodedPassword = PasswordEncoder.encodePassword(user.getPassword());
            user.setPassword(encodedPassword);
        }
    }
}