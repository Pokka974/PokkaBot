/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;

/**
 *
 * @author Pokka
 */
public class Nappe {
    
    String cmd, args;
    GuildMessageReceivedEvent event;

    public Nappe(String cmd, String args, GuildMessageReceivedEvent event) {
        this.cmd = cmd;
        this.args = args;
        this.event = event;
    }
    
    public void execute()
    {
        User user = event.getAuthor();
        MessageChannel chan = event.getChannel();
        if(event.getGuild().getId().equals("202631064999428098"))
        {
            if(chan.getId().equals("208708392997355520"))
                chan.sendMessage("**"+user.getUsername() + "** vient de napper **" + args + "** :open_hands: :sparkling_heart: ");
            else
                chan.sendMessage("Cette commande n'est disponible que dans le channel : **#bot-games**");
        }
        else
            chan.sendMessage("**"+user.getUsername() + "** vient de napper **" + args + "** :open_hands: :sparkling_heart: ");
    }
}
