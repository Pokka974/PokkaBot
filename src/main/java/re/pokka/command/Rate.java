/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;

/**
 *
 * @author Pokka
 */
public class Rate {
    
    String command, args;
    String description = 
            "Je note le membre mentionné sur /10 !";
    GuildMessageReceivedEvent event;

    public Rate(String command, String args, GuildMessageReceivedEvent event) {
        this.command = command;
        this.args = args;
        this.event = event;
    }
    
    public void execute()
    {
        
        MessageChannel chan = event.getChannel();
        
        int rate = random(1, 10);
        String emoji = null;

        if (rate < 6) {
            emoji = ":frowning:";
        } else if (rate > 5 && rate < 10) {
            emoji = ":smiley:";
        } else if (rate == 10) {
            emoji = ":heart_eyes:";
        }

        chan.sendMessage("Je donne à " + args + " un " + rate + "/10 ! " + emoji);
              
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
