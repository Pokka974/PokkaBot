/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import re.pokka.db.MemberManager;
import re.pokka.pokkabot.Members;

/**
 *
 * @author Pokka
 */
public class Scoring {
    String cmd, args;
    GuildMessageReceivedEvent event;

    public Scoring(String cmd, String args, GuildMessageReceivedEvent event) {
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
                    return o2.getScore() - o1.getScore();
                }
            });

            //String affichage = "```java\n";

            for(int i = 0; i < 10; i++)
            {
                result += all.get(i).getPseudo() +"  :  "+ all.get(i).getScore() +"\n";
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
                   event.getChannel().sendMessage("```java\n"+ m2.getPseudo() + " : " + m2.getScore() + "\n```");
                   return;
                }
            }
            
            for(Members m2 : all)
            {
                if(m2.getId().equals(args))
                {
                    event.getChannel().sendMessage("```java\n"+ m2.getPseudo() + " : " + m2.getScore() + "\n```");
                   return;
                }
            }
        }
    }
    
    public void setScore()
    {
        boolean master = event.getAuthor().getId().equals("106672188513353728");
        Members m = new Members();
        String[] str = args.split("=");
        
        if(master)
        {
            m = MemberManager.get_Instance().getMembers(str[0]);
            
            if(m != null)
            {
                m.setScore(Integer.parseInt(str[1]));
                MemberManager.get_Instance().putMembers(m.getId(), m);
                event.getChannel().sendMessage("Mise à jour de " + m.getPseudo() + " enregistrée !");
            }
            else
            {
                event.getChannel().sendMessage("Ce membre n'est pas encore enregistré dans la base de donnée.");
                
                Guild g = event.getGuild();
                List<User> us = g.getUsers();
                
                for(User u : us)
                {
                    if(u.getId().equals(str[0]))
                    {
                        Members m2 = new Members();
                        
                        m2.setId(u.getId());
                        m2.setPseudo(u.getUsername());
                        m2.setScore(Integer.parseInt(str[1]));
                        MemberManager.get_Instance().putMembers(m2.getId(), m2);
                        event.getChannel().sendMessage("Ajout de " + m2.getPseudo() + " enregistrée !");
                        break;
                    }
                }
            }
        }
    }
    
   
}
