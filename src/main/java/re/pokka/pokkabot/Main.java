/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.pokkabot;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.JDABuilder;

/**
 *
 * @author Pokka
 */
public class Main {
    final static String TOKEN = "MjA3ODYwNzE1NDM0OTM0Mjcy.CqdJDA.FCVNf57HB_wvMOny5p_i4Bh2gzc";
    public static void main(String[] args)
    {
        try
        {
            new JDABuilder()
                    .setBotToken(TOKEN)
                    .setAudioEnabled(true)
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
