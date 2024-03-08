package common;

import data.model.entity.enums.RoleName;
import javax.security.auth.Subject;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Set;

public class HasRoleEmployeeInterceptor implements java.lang.reflect.InvocationHandler {
    private final Object target;

    public HasRoleEmployeeInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Subject subject = SecurityContext.getSubject();

        if(subject != null) {
            Set<Principal> principals = subject.getPrincipals();
            boolean hasRoleEmployee = false;

            for(Principal principal : principals) {
                if(principal instanceof RolePrincipal && principal.getName().equals(RoleName.EMPLOYEE.name())) hasRoleEmployee = true;
            }

            if(!hasRoleEmployee) throw new SecurityException("Access denied. Employee role required");

            return method.invoke(this.target, args);
        }

        throw new SecurityException("No logged in user present");
    }
}
