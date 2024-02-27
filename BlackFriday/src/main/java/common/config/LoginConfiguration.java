package common.config;

import com.sun.security.auth.module.NTLoginModule;
import common.module.UserLoginModule;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import java.util.HashMap;
import java.util.Map;

public class LoginConfiguration extends Configuration {
    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        Map<String, Object> options = new HashMap<>();
        options.put("debug", "true");

        return new AppConfigurationEntry[] {
                new AppConfigurationEntry(UserLoginModule.class.getName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options)
        };
    }
}