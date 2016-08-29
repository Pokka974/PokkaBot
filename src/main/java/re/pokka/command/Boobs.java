/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import java.io.File;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;

/**
 *
 * @author Pokka
 */
public class Boobs {
    
    String cmd, args;
    GuildMessageReceivedEvent event;

    public Boobs(String cmd, String args, GuildMessageReceivedEvent event) {
        this.cmd = cmd;
        this.args = args;
        this.event = event;
    }
    
    public void execute()
    {
        User user = event.getAuthor();
        MessageChannel chan = event.getChannel();
        
        boolean master = user.getId().equals("106672188513353728");
        
        File dir = new File("C:\\Users\\Pokka\\Documents\\NetBeansProjects\\PokkaBot\\joke\\boobs");
        File[] allBoobs = dir.listFiles();
        int row = 0;
        int randomBoobs;
        
        if(chan.getId().equals("202689183188385792")  || chan.getId().equals("216998365114662922") || chan.getId().equals("211955285785313281"))
        {
            if (args != null) {
                row = Integer.parseInt(args);
            } else {
                row = 0;
            }

            if (row > 0) {
                if (master) {
                    for (int i = 0; i < row; i++) {
                        randomBoobs = random(0, allBoobs.length);
                        chan.sendFile(allBoobs[randomBoobs], null);
                    }
                } else {
                    chan.sendMessage("Je ne te connais pas assez.\nTu n'auras droit qu'à une seule photo.");
                    randomBoobs = random(0, allBoobs.length);
                    chan.sendFile(allBoobs[randomBoobs], null);
                }
            } else {
                randomBoobs = random(0, allBoobs.length);
                chan.sendFile(allBoobs[randomBoobs], null);
            }
        }
        else
            chan.sendMessage("Ceci est un channel safe, pas ici désolé !");
    }
     
    int random(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
