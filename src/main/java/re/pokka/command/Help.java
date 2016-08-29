/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;

/**
 *
 * @author Pokka
 */
public class Help {
    String cmd;
    GuildMessageReceivedEvent event;

    public Help(String cmd, GuildMessageReceivedEvent event) {
        this.cmd = cmd;
        this.event = event;
    }
    
    public void execute()
    {
        
    }
}
