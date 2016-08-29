/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import re.pokka.db.MemberManager;
import re.pokka.pokkabot.Members;

/**
 *
 * @author Pokka
 */
public class BatonScore {
    String cmd, args;
    GuildMessageReceivedEvent event;

    public BatonScore(String cmd, String args, GuildMessageReceivedEvent event) {
        this.cmd = cmd;
        this.args = args;
        this.event = event;
    }
    
    public void getScore()
    {
        ArrayList<Members> all = MemberManager.get_Instance().getGMembersList();
        String result = "";
        
        if(args == null)
        {
            Collections.sort(all, new Comparator<Members>() {
                @Override
                public int compare(Members o1, Members o2) {
                    return o2.getDefaites() - o1.getDefaites();
                }
            });

            //String affichage = "```java\n";

            for(int i = 0; i < 10; i++)
            {
                result += all.get(i).getPseudo() +"  :  "+ all.get(i).getDefaites() +" défaites\n";
            }

            //affichage += "```";

            event.getChannel().sendMessage("```java\n"+result+"```");
        }
        else
        {
            for(Members m2 : all)
            {
                if(m2.getPseudo().toLowerCase().contains(args))
                {
                   event.getChannel().sendMessage("```java\n"+ m2.getPseudo() + " : " + m2.getDefaites() + " défaites\n```");
                   return;
                }
            }
            
            for(Members m2 : all)
            {
                if(m2.getId().equals(args))
                {
                    event.getChannel().sendMessage("```java\n"+ m2.getPseudo() + " : " + m2.getDefaites() + " défaites\n```");
                   return;
                }
            }
        }
    }
}
