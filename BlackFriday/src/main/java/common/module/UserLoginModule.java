package common.module;

import application.service.interfaces.IUserService;
import com.sun.security.auth.UserPrincipal;
import common.RolePrincipal;
import data.model.entity.User;
import application.util.PasswordEncoder;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

public class UserLoginModule implements LoginModule {
    private static IUserService userService;
    private Subject subject;
    private CallbackHandler callbackHandler;
    private User user;
    private boolean isLoginSuccessful = false;

    public static void setUserService(IUserService userService) {
        UserLoginModule.userService = userService;
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        NameCallback nameCallback = new NameCallback("username: ");
        PasswordCallback passwordCallback = new PasswordCallback("password: ", false);
        Callback[] callbacks = new Callback[] {nameCallback, passwordCallback};

        try {
            this.callbackHandler.handle(callbacks);
            String username = nameCallback.getName();
            String password = new String(passwordCallback.getPassword());

            this.user = userService.getUserByUsername(username);
            if(PasswordEncoder.verifyPasswords(user.getPassword(), password)) this.isLoginSuccessful = true;

            passwordCallback.clearPassword();
        }
        catch (IOException | UnsupportedCallbackException ex) {
            throw new LoginException("Invalid username or password");
        }

        return this.isLoginSuccessful;
    }

    @Override
    public boolean commit() throws LoginException {
        if(!this.isLoginSuccessful) return false;

        Principal principal = new UserPrincipal(this.user.getUsername());
        this.subject.getPrincipals().add(principal);
        this.user.getRoles().forEach(r -> {
            RolePrincipal currRolePrincipal = new RolePrincipal(r);
            this.subject.getPrincipals().add(currRolePrincipal);
        });

        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return this.logout();
    }

    @Override
    public boolean logout() throws LoginException {
        boolean isLogoutSuccessful = true;
        this.subject.getPrincipals().removeIf(principal -> principal instanceof UserPrincipal || principal instanceof RolePrincipal);

        return isLogoutSuccessful;
    }
}