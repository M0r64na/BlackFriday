package application.service;

import data.model.entity.Role;
import data.model.entity.RoleName;
import data.model.entity.User;
import data.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

// TODO map dto-s to entities => MAPSTRUCT
public class UserService implements IUserService {
    private static final UserRepository userRepository = new UserRepository();
    private static final IRoleService roleService = new RoleService();

    @Override
    public void create(User user) {
        Role clientRole = roleService.findByName(RoleName.CLIENT);
        user.getRoles().add(clientRole);

        userRepository.create(user);
    }

    @Override
    public User update(User user) {
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
}