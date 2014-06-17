
package photosort_login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Console;
import java.util.Arrays;
import java.io.IOException;

/**
 *
 * @author eccomania
 */
public class PhotoSort_Login {
    
    public static void main (String args[]) throws IOException {
        
        Console c = System.console();
        if (c == null) {
            System.err.println("No Console!");
            System.exit(1);
        }
        
        String login = c.readLine("Enter your login: ");
        char [] oldPassword = c.readPassword("Enter your old password: ");
        
        if (verify(login, oldPassword)) {
            boolean noMatch = true;
            do {
                char [] newPassword1 = c.readPassword("Enter your new password: ");
                char [] newPassword2 = c.readPassword("Enter your new password again: ");
                if (noMatch) {
                    c.format("Passwords don't match. Try again.%n");
                } else {
                    change(login, newPassword1);
                    c.format("Password for %s changed.%n", login);
                }
                Arrays.fill(newPassword1, ' ');
                Arrays.fill(newPassword2, ' ');
            } while (noMatch);
        }
        Arrays.fill(oldPassword, ' ');
    }
    
    static boolean verify(String login, char[] password){
        
        if (password.toString() == login){
            return true;
        } else {
            return false;
        }
    }
    
    static void change (String login, char[] password) {
        
    }
    
}