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
public class Say {
    String cmd, args;
    GuildMessageReceivedEvent event;

    public Say(String cmd, String args, GuildMessageReceivedEvent event) {
        this.cmd = cmd;
        this.args = args;
        this.event = event;
    }
    
    public void execute()
    {
        event.getChannel().sendMessage(args);
    }
}
