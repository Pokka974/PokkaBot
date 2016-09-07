/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.pokkabot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.JDABuilder;

/**
 *
 * @author Pokka
 */
public class Main {
    public static Scanner scanner;
    static String TOKEN = "";
    public static void main(String[] args) throws FileNotFoundException
    {
        scanner = new Scanner(new File("C:\\Users\\Pokka\\Documents\\NetBeansProjects\\PokkaBot\\TOKEN.txt"));
        
        TOKEN = scanner.nextLine();
        
        try
        {
            new JDABuilder()
                    .setBotToken(TOKEN)
                    .setAudioEnabled(true)
                    .setAutoReconnect(true)
                    .addListener(new EventManager())
                    .buildBlocking();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("The config was not populated. Please enter a bot token.");
        }
        catch (LoginException e)
        {
            System.out.println("The provided bot token was incorrect. Please provide valid details.");
        }
        catch (InterruptedException e)
        {
        }
    }
}
