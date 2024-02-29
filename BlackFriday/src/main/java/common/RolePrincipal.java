package common;

import data.model.entity.Role;
import lombok.AllArgsConstructor;
import java.security.Principal;

@AllArgsConstructor
public class RolePrincipal implements Principal {
    private Role role;

    @Override
    public String getName() {
        return role.getRoleName().name();
    }
}