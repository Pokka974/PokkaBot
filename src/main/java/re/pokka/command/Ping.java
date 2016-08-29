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
public class Ping {
    String command;
    String description = 
            "Renvoi un Pong !";
    GuildMessageReceivedEvent event;

    public Ping(String command, GuildMessageReceivedEvent event) {
        this.command = command;
        this.event = event;
    }
    
    public void execute()
    {
        MessageChannel chan = event.getMessage().getChannel();
        
        chan.sendMessage("Pong !");
    }
    
    public String help()
    {
        return command + " : " + description;
    }
    
}
