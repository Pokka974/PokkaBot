/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import re.pokka.db.SanctionManager;
import re.pokka.pokkabot.Members;
import re.pokka.pokkabot.Sanction;

/**
 *
 * @author Pokka
 */
public class Logs {
    String[] modoID = {
        "171003357299867649",
        "207592594794872833",
        "106808974040444928",
        "106672188513353728",
        "146448493039452161"
    };
    
    String cmd, args;
    GuildMessageReceivedEvent event;
    String[] msgParts;
    String typeOfSanction, userConcerned, typeAndReason, reason;
    List<User> allUser;
    
    TextChannel chan;
    User user;
    boolean modo;
    
    public Logs(String cmd, String args, GuildMessageReceivedEvent event) {
        this.cmd = cmd;
        this.args = args;
        this.event = event;
        this.chan = event.getChannel();
        
        msgParts = args.split(" ");
        typeOfSanction = args.split(" ")[0];
        //reason = args.split(" ")[1];
        reason = "";
        
       
        for(int i = 1; i < msgParts.length; i++)
        {
            if(i < msgParts.length - 1)
            {
                reason += msgParts[i];
                reason += " ";
            }
        }
        for(String s : modoID)
        {
            if(event.getAuthor().getId().equals(s))
                modo = true;
        }
        //typeAndReason = typeOfSanction + " " + reason + " ";
        
        //userConcerned = args.replaceAll(typeAndReason, "");
        
        user = event.getJDA().getUserById(args.split(" ")[msgParts.length-1]);
        
    }
    
    public void addNewLog()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy - HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        Date date = new Date();
        
        if(user == null)
            System.out.println("User null");
        
        if(chan.getId().equals("222040191513133056") && modo && event.getGuild().getId().equals("202631064999428098"))
        {       
            Sanction sanction = new Sanction();
            sanction.setId(SanctionManager.get_Instance().getNumberOfSanction() + 1);
            sanction.setIdStaffConcerne(event.getAuthor().getId());
            sanction.setIdMembreSanctionne(user.getId());
            sanction.setRaison(reason);
            sanction.setType(typeOfSanction);
            sanction.setDate(dateFormat.format(date));
            
            SanctionManager.get_Instance().putSanction(sanction.getId() + "", sanction);
            
                event.getChannel().sendMessage(
                        "**Case** : " + sanction.getId() + "\n" +
                            "**Type** : " + typeOfSanction.toUpperCase() +"\n"
                                +"\n**Utilisateur** : " + user.getAsMention() + " **ID** : " + user.getId()
                                    + "\n**Raison** : " + reason 
                                        + "\n**Staff concerné** : " + event.getAuthor().getAsMention()
                                            + "\n**Date** : " + dateFormat.format(date));
                
                user.getPrivateChannel().sendMessage("**Case** : " + sanction.getId() + "\n" +
                            "**Type** : " + typeOfSanction.toUpperCase() +"\n"
                                +"\n**Utilisateur** : " + user.getAsMention() + " **ID** : " + user.getId()
                                    + "\n**Raison** : " + reason 
                                        + "\n**Staff concerné** : " + event.getAuthor().getAsMention()
                                            + "\n**Date** : " + dateFormat.format(date));
                
                timeForDel(event.getMessage());
           
        }
        
    }
    
    public void getSanction()
    {
        List<User> allU = event.getGuild().getUsers();
        ArrayList<Sanction> allS = SanctionManager.get_Instance().getGSanctionList();
        ArrayList<Sanction> sanctionOK = new ArrayList<>();
        Message m;
        
        User userCon = null;
        
        for(User u : allU)
        {
            if(u.getUsername().toLowerCase().contains(args.toLowerCase()))
                userCon = u;
        }
        
        if(userCon == null)
        {
            m = event.getChannel().sendMessage("Utilisateur introuvable");
            timeForDel(m);
            timeForDel(event.getMessage());
            return;
        }
        
        for(Sanction s : allS)
        {
            if(s.getIdMembreSanctionne().equals(userCon.getId()))
                sanctionOK.add(s);
        }
        
        if(sanctionOK.isEmpty())
        {
            m = event.getChannel().sendMessage("Aucun log trouvé pour **" + args + "**");
            timeForDel(m);
            timeForDel(event.getMessage());
            return;
        }
        else
        {
            String mes = "";
            
            Collections.sort(sanctionOK, (Sanction o1, Sanction o2) -> o2.getId()- o1.getId());
            
            mes = sanctionOK.stream().map((s) -> s.getId() + " : " + s.getType() + " : " + s.getRaison() + " : " + s.getDate() + "\n").reduce(mes, String::concat);
            
            event.getChannel().sendMessage("```java\n" + mes + "```");
        }
        timeForDel(event.getMessage());
    }
    
    public void getSanctionById()
    {
        ArrayList<Sanction> allS = SanctionManager.get_Instance().getGSanctionList();
        ArrayList<Sanction> sanctionOK = new ArrayList<Sanction>();
        
        allS.stream().filter((s) -> (s.getIdMembreSanctionne().equals(args))).forEach((s) -> {
            sanctionOK.add(s);
        });
        
        if(sanctionOK.isEmpty())
        {
            event.getChannel().sendMessage("Aucun log trouvé pour **" + args + "**");
        }
        else
        {
            String mes = "";
            
            Collections.sort(sanctionOK, (Sanction o1, Sanction o2) -> o2.getId()- o1.getId());
            
            mes = sanctionOK.stream().map((s) -> s.getId() + " : " + s.getType() + " : " + s.getRaison() + " : " + s.getDate() + "\n").reduce(mes, String::concat);
            
            event.getChannel().sendMessage("```java\n" + mes + "```");
                
        }
        timeForDel(event.getMessage());
    }

    
    public void deleteLog()
    {
        if(modo)
        {
            SanctionManager.get_Instance().DeleteSanction(args);
            event.getMessage().deleteMessage();
        }
    }
    
    public boolean testValidMsg()
    {
        if(msgParts.length > 2)
        {
            return typeOfSanction.equals("ban") || typeOfSanction.equals("kick") || typeOfSanction.equals("mute");
        }
        else
            return false;
    }
    
    public void timeForDel(Message m)
    {
        Timer time = new Timer();
        
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                m.deleteMessage();
            }
        }, 2000);
    }
}
