/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.pokkabot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.exceptions.RateLimitedException;
import net.dv8tion.jda.hooks.ListenerAdapter;

/**
 *
 * @author Pokka
 */
public class Bombparty extends ListenerAdapter {

    User botuser;

    ArrayList<String> wordList = new ArrayList<>();
    Scanner scanner = null;
    String syllabe = null;
    String currentWord;
    int vie = 3, compteur = 0, mots = 0;
    boolean first = true;
    Timer timer;
    User player = EventManager.bombPlayer;
    ArrayList<Message> msg = new ArrayList<Message>();
    
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        
        botuser = event.getJDA().getSelfInfo();
        User user = event.getAuthor();
        
        MessageChannel chan = event.getChannel();
        String content = event.getMessage().getContent();

        /* Chargement des mots dans la List */
        try {
            scanner = new Scanner(new File("C:\\Users\\Pokka\\Documents\\dicofr - Copie.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bombparty.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (scanner.hasNext()) {
            wordList.add(scanner.nextLine());
        }
        
        if (user.getId().equals(botuser.getId()) || vie == 0) {
            return;
        }
        
        if(first)
        {
            loadGame(chan, event);
            first = false;
        }
        
        System.out.println("Mot : " + currentWord + " Syllabe " + syllabe + " Content : " + content);

            if(wordList.contains(content) && content.contains(syllabe))
            {
                msg.add(chan.sendMessage("Bravo !"));
                mots++;
                compteur++;
                timer.cancel();
                deleteAllMsg(chan, event);

                if(compteur == 5)
                {
                    if(vie < 3)
                    {
                        compteur = 0;
                        vie++;
                        msg.add(chan.sendMessage("Vous avez gagné une vie ! **"+vie+"** vies"));

                    }
                }
            }

            if (content.equals("!stop"))
            {
                event.getJDA().removeEventListener(this);
                timer.cancel();
                deleteAllMsg(chan, event);
            }
            
    }

    public void launchBomb(GuildMessageReceivedEvent event, MessageChannel chan) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                vie--;

                if (vie > 1) {
                    msg.add(chan.sendMessage("BOOM ! :bomb:\nIl vous reste **" + vie + "** vies"));
                    compteur = 0;
                    loadGame(chan, event);
                } else if (vie == 1) {
                    msg.add(chan.sendMessage("BOOM ! :bomb:\nIl ne vous reste plus qu'**1** vie :scream:"));
                    compteur = 0;
                    loadGame(chan, event);
                } else if (vie == 0) {
                    msg.add(chan.sendMessage("BOOM ! :bomb:\nC'est perdu !\nScore : **" + mots + "** mots"));
                    event.getJDA().removeEventListener(this);
                    deleteAllMsg(chan, event);
                }
            }
        }, 6000);
    }

    public void loadGame(MessageChannel chan, GuildMessageReceivedEvent event) {
        Timer timer2 = new Timer();
        int firstLetter;
        currentWord = wordList.get(random(0, wordList.size()));
        firstLetter = random(0, currentWord.length() - 3);
        syllabe = currentWord.substring(firstLetter, firstLetter + 3);

        System.out.println("Mot : " + currentWord + " Syllabe : " + syllabe);

        try {
            msg.add(chan.sendMessage("Je pense à un mot ... :thinking: \n-->    **" + syllabe + "**"));
          
            launchBomb(event, chan);
        } catch (RateLimitedException e) {
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    msg.add(chan.sendMessage("Je pense à un mot ... :thinking: \n-->    **" + syllabe + "**"));
                    launchBomb(event, chan);
                }
            }, e.getAvailTime());
        }
    }
    
    public void deleteAllMsg(MessageChannel chan, GuildMessageReceivedEvent event)
    {
        
        if(!msg.isEmpty())
        {
            msg.stream().forEach((m) -> {
                chan.deleteMessageById(m.getId());
            });
            
            msg.clear();
        }
        
        if(vie != 0)
            loadGame(chan, event);
    }
    
    int random(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
