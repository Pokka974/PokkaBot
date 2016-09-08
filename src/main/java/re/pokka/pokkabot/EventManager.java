/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.pokkabot;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import re.pokka.db.MemberManager;
import re.pokka.db.SanctionManager;

/**
 *
 * @author Pokka
 */
public class EventManager extends ListenerAdapter  {
    
    private final String KEY = "!";
    ChatterBotFactory factory = new ChatterBotFactory();
    ChatterBotSession bot1session;
    ChatterBot bot1;
    User botuser;
    public static User batonPlayer, bombPlayer;
    public static boolean ia = true;
    public static Map<String, Guild> guildList = new HashMap<>();
    
    @Override
    public void onReady(ReadyEvent event)
    {
        System.out.println("\n\tLe Bot est Prêt !\n");
        //List<User> allUserOtaculs = new ArrayList<User>(); 
        //List<User> allUserJapancult = new ArrayList<User>(); 
        botuser = event.getJDA().getSelfInfo();
        
        List<Guild> allGuild = event.getJDA().getGuilds();
        
        for(Guild g : allGuild)
        {
            guildList.put(g.getName(), g);
        }
        
//        Sanction sanction = new Sanction(1, "106808974040444928", "220154861298122752", "Troll", "KICK", "05/09/2016 - 10:54");
//        SanctionManager.get_Instance().putSanction(sanction.getId()+"", sanction);
//        Guild otaculs = botuser.getJDA().getGuildById("106689811900870656");
//        Guild japancult = botuser.getJDA().getGuildById("202631064999428098");
//        
//        allUserOtaculs = otaculs.getUsers();
//        allUserJapancult = japancult.getUsers();
//        
//        for(User u : allUserOtaculs)
//        {
//            Members m = new Members(u.getId(), u.getUsername());
//            m.setScore(0);
//            MemberManager.get_Instance().putMembers(m.getId(), m);
//        }
//        
//        for(User u : allUserJapancult)
//        {
//            Members m = new Members(u.getId(), u.getUsername());
//            m.setScore(0);
//            MemberManager.get_Instance().putMembers(m.getId(), m);
//        }
////        
//        Members titch = new Members();
//        titch.setId("141962573900808193");
//        titch.setPseudo("Titch");
//        titch.setScore(3);
//        MemberManager.get_Instance().putMembers(titch.getId(), titch);
//        
//        Members sandy = new Members();
//        sandy.setId("210502738168119297");
//        sandy.setPseudo("Takoyaki-chan");
//        sandy.setScore(1);
//        MemberManager.get_Instance().putMembers(sandy.getId(), sandy);
//        
//        Members eme = new Members();
//        eme.setId("105463077746569216");
//        eme.setPseudo("Emerani");
//        eme.setScore(2);
//        MemberManager.get_Instance().putMembers(eme.getId(), eme);
//        
//        Members citron = new Members();
//        citron.setId("215479162436190208");
//        citron.setPseudo("Çįtròñ");
//        citron.setScore(1);
//        MemberManager.get_Instance().putMembers(citron.getId(), citron);
//        
//        Members naru = new Members();
//        naru.setId("103836669576228864");
//        naru.setPseudo("Naruchong/Naroule/Nalou");
//        naru.setScore(1);
//        MemberManager.get_Instance().putMembers(naru.getId(), naru);
        
        try {
            
            bot1 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            
        } catch (Exception ex) {
            
            Logger.getLogger(EventManager.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        bot1session = bot1.createSession();
    }
    
    /**
     *
     * @param event
     */
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String content = event.getMessage().getContent();
        String cmd, args = null;
        
        int randomNumber;
        
                /* BombParty */
        
        
        if(event.getMessage().getContent().equals("!bombparty"))
        {
            bombPlayer = event.getAuthor();
            event.getChannel().sendMessage("La Partie va commencer dans 5 secondes ...");
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            event.getChannel().sendFile(new File("C:\\Users\\Pokka\\Documents\\NetBeansProjects\\PokkaBot\\bomb.png"), null);
                            event.getJDA().addEventListener(new Bombparty());
                        }
                    }, 5000);
        }
        
        
                /*Jeu du Bâton */
                
        if(event.getMessage().getContent().equals("!baton"))
        {
            if(event.getGuild().getId().equals("202631064999428098"))
            {
                if(event.getChannel().getId().equals("208708392997355520"))
                {
                    batonPlayer = event.getAuthor();
                    event.getChannel().sendMessage("La Partie va commencer dans 5 secondes ...\nSi le bot tire le dernier bâton, c'est gagné!");
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            event.getChannel().sendMessage("Il reste **21** bâtons \nÀ toi de jouer " + batonPlayer.getAsMention());
                            event.getJDA().addEventListener(new Baton()); //launch game

                        }
                    }, 5000);
                }
                else
                    event.getChannel().sendMessage("Cette commande n'est disponible que dans le channel : **#bot-games**");
            }
        }
        
                /* Stop new Event Listener */
        if(event.getMessage().getContent().equals("!stop"))
        {
            event.getJDA().removeEventListener(new Bombparty());
            event.getJDA().removeEventListener(new Baton());
        }
        
        
        /* Message Bingo */
        
        if(!event.getAuthor().isBot())
        {
            randomNumber = random(1, 300);
            //System.out.println(event.getAuthorName()+" > "+ event.getMessage().getContent() + " > " + randomNumber +"\n");
            
            if(randomNumber == 100)
            {
                if(MemberManager.get_Instance().getMembers(event.getAuthor().getId()) != null)
                {
                    Members winner = MemberManager.get_Instance().getMembers(event.getAuthor().getId());

                    int score = winner.getScore();
                    score++;
                    winner.setScore(score);

                    MemberManager.get_Instance().putMembers(winner.getId(), winner);
                    
                    event.getChannel().sendMessage("OMG OMG OMG OMG :scream: 1 chance sur 300, GG " + event.getAuthorName()
                +"\nVous avez maintenant : **" + winner.getScore() +"** points !");
                    
                  
                }
                else
                {
                    Members newWinner = new Members(event.getAuthor().getId(), event.getAuthorName());
                    newWinner.setScore(1);
                    MemberManager.get_Instance().putMembers(newWinner.getId(), newWinner);
                    
                    event.getChannel().sendMessage("OMG OMG OMG OMG :scream: 1 chance sur 300, GG " + event.getAuthorName()
                +"\nVous avez maintenant : **" + newWinner.getScore() +"** points !");
                    
                }
            }
        }
        
        /* Test si le message est une commande */
        
        if (!content.startsWith(KEY)) {
            //System.out.println("Not a Command");
            return;
        }
        
        cmd = content.toLowerCase();
        
        /* Séparer la commande de l'argument */
        
        if (content.contains(" ")) {
            System.out.println("Commands contain args");
            cmd = cmd.split(" ")[0];
            args = content.substring(content.indexOf(' ') + 1);
        }
        
        try {
            
            Command command = new Command(cmd, args, event);
            
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(EventManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        
        /* Si le bot est Mentionné */
        
                if (event.getMessage().isMentioned(botuser) && ia && !event.getAuthor().isBot()) 
                {
                    String s = event.getMessage().getContent();
                    System.out.println(s);

                    try {
                        s = bot1session.think(s);
                    } catch (Exception ex) {
                        Logger.getLogger(EventManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    System.out.println("bot1> " + s);
                    event.getChannel().sendMessage(s + " "+ event.getAuthor().getAsMention());
                }
                
    }
       
    @Override
    public void onGuildMemberNickChange(GuildMemberNickChangeEvent event) 
    {
        if(event.getUser().getId().equals("204323518412881920"))
            return;
        
        User changer = event.getUser();
        Guild g = event.getGuild();
        TextChannel tc = event.getJDA().getTextChannelById("202631064999428098");
        
        if(g.getId().equals("202631064999428098"))
            g.getPublicChannel().sendMessage("**"+event.getPrevNick() + "** a changé son pseudo pour **" + event.getNewNick()+"**");
        
        if(g.getId().equals("106689811900870656"))
            g.getPublicChannel().sendMessage("**"+event.getPrevNick() + "** a changé son pseudo pour **" + event.getNewNick()+"**");
    }
    
    int random(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
