package common.module;

import application.service.IUserService;
import application.service.UserService;
import com.sun.security.auth.UserPrincipal;
import data.model.entity.User;
import data.util.PasswordEncoder;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

public class UserLoginModule implements LoginModule {
    private static final IUserService userService = new UserService();

    private Subject subject;
    private CallbackHandler callbackHandler;
    private String username;
    private Principal principal;
    private boolean isLoginSuccessful = false;

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
            this.username = nameCallback.getName();
            String password = new String(passwordCallback.getPassword());

            User user = userService.getByUsername(this.username);
            if(PasswordEncoder.verifyPasswords(user.getPassword(), password)) this.isLoginSuccessful = true;

            passwordCallback.clearPassword();
        }
        // Collapsed catch block
        catch (IOException | UnsupportedCallbackException ex) {
            throw new LoginException("Invalid username or password");
        }

        return this.isLoginSuccessful;
    }

    @Override
    public boolean commit() throws LoginException {
        if(!this.isLoginSuccessful) return false;

        this.principal = new UserPrincipal(this.username);
        subject.getPrincipals().add(this.principal);

        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return this.logout();
    }

    @Override
    public boolean logout() throws LoginException {
        return this.subject.getPrincipals().remove(this.principal);
    }
}