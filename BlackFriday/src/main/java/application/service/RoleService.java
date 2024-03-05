package application.service;

import application.service.interfaces.IRoleService;
import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import data.repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RoleService implements IRoleService {
    private static final RoleRepository roleRepository = new RoleRepository();

    @Override
    public Optional<Role> getById(UUID id) {
        return roleRepository.getById(id);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.getAll();
    }

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void initialize() {
        if(!roleRepository.getAll().isEmpty()) return;

        roleRepository.create(new Role(RoleName.CLIENT));
        roleRepository.create(new Role(RoleName.EMPLOYEE));
    }
}