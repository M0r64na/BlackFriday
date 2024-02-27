package common;

import javax.security.auth.callback.*;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleCallbackHandler implements CallbackHandler {
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
         for(Callback c : callbacks) {
            // nameCallback and passwordCallback are pattern variables
            if(c instanceof NameCallback nameCallback) {
                // nameCallback.setName(console.readLine(nameCallback.getPrompt()));
                this.handleNameCallback(nameCallback);
                continue;
            }
            else if(c instanceof PasswordCallback passwordCallback) {
                // passwordCallback.setPassword(console.readPassword(passwordCallback.getPrompt()));
                this.handlePasswordCallback(passwordCallback);
                continue;
            }

            throw new UnsupportedCallbackException(c);
        }
    }

    private void handleNameCallback(NameCallback callback) throws IOException {
        System.out.print(callback.getPrompt());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        callback.setName(reader.readLine());
    }

    private void handlePasswordCallback(PasswordCallback callback) throws IOException {
        System.out.print(callback.getPrompt());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        char[] password = reader.readLine().toCharArray();
        callback.setPassword(password);
    }
}