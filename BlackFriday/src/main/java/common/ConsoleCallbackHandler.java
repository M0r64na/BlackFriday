package common;

import javax.security.auth.callback.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleCallbackHandler implements CallbackHandler {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
         for(Callback c : callbacks) {
            if(c instanceof NameCallback nameCallback) {
                this.handleNameCallback(nameCallback);
                continue;
            }
            else if(c instanceof PasswordCallback passwordCallback) {
                this.handlePasswordCallback(passwordCallback);
                continue;
            }

            throw new UnsupportedCallbackException(c);
        }
    }

    private void handleNameCallback(NameCallback callback) throws IOException {
        System.out.print(callback.getPrompt());

        callback.setName(reader.readLine());
    }

    private void handlePasswordCallback(PasswordCallback callback) throws IOException {
        System.out.print(callback.getPrompt());

        char[] password = reader.readLine().toCharArray();
        callback.setPassword(password);
    }
}