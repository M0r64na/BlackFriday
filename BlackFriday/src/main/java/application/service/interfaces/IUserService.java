package application.service.interfaces;

import data.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO add dto objects
public interface IUserService {
    void create(User user);
    User update(User user);
    Optional<User> getById(UUID id);
    List<User> getAll();
    void deleteById(UUID id);
    User getByUsername(String username);
}