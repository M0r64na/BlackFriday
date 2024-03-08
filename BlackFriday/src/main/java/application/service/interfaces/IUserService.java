package application.service.interfaces;

import data.model.entity.Role;
import data.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

// TODO add dto objects
public interface IUserService {
    void create(String username, String password);
    User update(String username, String password);
    Optional<User> getById(UUID id);
    List<User> getAll();
    void deleteById(UUID id);
    User getByUsername(String username);
    void initialize();
}