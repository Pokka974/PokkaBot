/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import re.pokka.pokkabot.Bombparty;

/**
 *
 * @author Pokka
 */
public class KeyToSuccess {
    String cmd, args;
    GuildMessageReceivedEvent event;
    Scanner scanner;
    ArrayList<String> keyToSuccess = new ArrayList<>();
    
    public KeyToSuccess(String cmd, String args, GuildMessageReceivedEvent event) {
        this.cmd = cmd;
        this.args = args;
        this.event = event;
    }
    
    public void execute()
    {
        
        
        try {
            scanner = new Scanner(new File("C:\\Users\\Pokka\\Documents\\NetBeansProjects\\joke\\KeyToSuccess.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bombparty.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (scanner.hasNext()) {
            keyToSuccess.add(scanner.nextLine());
        }
        
        int random = random(0, keyToSuccess.size());
        
        if(event.getGuild().getId().equals("202631064999428098"))
        {
            if(event.getChannel().getId().equals("208708392997355520"))
            {
                event.getChannel().sendMessage(":key2: Key to Success : *"+ keyToSuccess.get(random) +"*");
            }
            else
                event.getChannel().sendMessage("Cette commande n'est disponible que dans le channel : **#bot-games**");
        }
        else
            event.getChannel().sendMessage(":key2: Key to Success : *"+ keyToSuccess.get(random) +"*");
    }
    
    int random(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
