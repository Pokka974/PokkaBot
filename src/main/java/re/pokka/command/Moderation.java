/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import java.util.List;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.managers.GuildManager;
import re.pokka.pokkabot.EventManager;

/**
 *
 * @author Pokka
 */
public class Moderation {
    String cmd, args;
    GuildMessageReceivedEvent event;
    GuildManager gm;
    Guild currentGuild;
    List<User> userListFromThisGuild;
    TextChannel chan;
    
    public Moderation(String cmd, String args, GuildMessageReceivedEvent guild) {
        this.cmd = cmd;
        this.args = args;
        this.event = guild;
        chan = event.getChannel();
        currentGuild = EventManager.guildList.get(event.getGuild().getName());
    }
    
    public void muteSomeone()
    {
        userListFromThisGuild = currentGuild.getUsers();
        gm = new GuildManager(currentGuild);
        
        userListFromThisGuild.stream().filter((u) -> (u.getId().equals(args))).map((u) -> {
            gm.mute(u);
            return u;
        }).forEach((u) -> {
            chan.sendMessage(u.getUsername() + " a été Mute");
        });
    }
}
