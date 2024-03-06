package application.service;

import application.service.interfaces.IRoleService;
import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import data.repository.RoleRepository;

public class RoleService implements IRoleService {
    private static final RoleRepository roleRepository = new RoleRepository();

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