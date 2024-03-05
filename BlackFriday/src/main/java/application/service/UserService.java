package application.service;

import application.service.interfaces.IRoleService;
import application.service.interfaces.IUserService;
import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import data.model.entity.User;
import data.repository.UserRepository;
import application.util.PasswordEncoder;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO map dto-s to entities => MAPSTRUCT
public class UserService implements IUserService {
    private static final UserRepository userRepository = new UserRepository();
    private static final IRoleService roleService = new RoleService();

    @Override
    public void create(User user) {
        String encodedPassword = PasswordEncoder.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);

        Role clientRole = roleService.findByName(RoleName.CLIENT);
        user.getRoles().add(clientRole);

        userRepository.create(user);
    }

    @Override
    public User update(User user) {
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

    private void updatePassword(User user) {
        String encodedPassword = this.getById(user.getId()).orElseThrow().getPassword();

        if(!encodedPassword.equals(user.getPassword())) {
            encodedPassword = PasswordEncoder.encodePassword(user.getPassword());
            user.setPassword(encodedPassword);
        }
    }
}