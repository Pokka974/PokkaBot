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
public class Legs {
    String command, args;
    String description = 
            "Je t'envoi des jambes sur commande !\n"
            +"!legs [nombre]";
    GuildMessageReceivedEvent event;

    public Legs(String cmd, String args, GuildMessageReceivedEvent event) {
        this.command = cmd;
        this.args = args;
        this.event = event;
    }
    
    
    public void execute()
    {
        User user = event.getAuthor();
        MessageChannel chan = event.getChannel();
        boolean master = user.getId().equals("106672188513353728");
        
        File dir = new File("C:\\Users\\Pokka\\Documents\\NetBeansProjects\\PokkaBot\\joke\\legs");
        File[] allLegs = dir.listFiles();
        int row = 0;
        int randomLegs;

        if (args != null) {
            row = Integer.parseInt(args);
        } else {
            row = 0;
        }
        if(chan.getId().equals("202689183188385792")  || chan.getId().equals("216998365114662922") || chan.getId().equals("218690966331064320"))
        {
            if (row > 0) {
                if (master) {
                    for (int i = 0; i < row; i++) {
                        randomLegs = random(0, allLegs.length);
                        chan.sendFile(allLegs[randomLegs], null);
                    }
                } else {
                    chan.sendMessage("Je ne te connais pas assez.\nTu n'auras droit qu'à une seule photo.");
                    randomLegs = random(0, allLegs.length);
                    chan.sendFile(allLegs[randomLegs], null);
                }
            } else {
                randomLegs = random(0, allLegs.length);
                chan.sendFile(allLegs[randomLegs], null);
            }
        }
        else
            chan.sendMessage("Ceci est un channel safe, pas ici désolé !");
    }
    
    public String help()
    {
        return command + " : " + description;
    }
    
    int random(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
