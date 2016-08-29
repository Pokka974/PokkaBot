/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.pokkabot;

import javax.sound.sampled.UnsupportedAudioFileException;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import re.pokka.command.BatonScore;
import re.pokka.command.Boobs;
import re.pokka.command.Legs;
import re.pokka.command.Nappe;
import re.pokka.command.Ping;
import re.pokka.command.Rate;
import re.pokka.command.Scoring;
import re.pokka.command.Say;

/**
 *
 * @author Pokka
 */
public class Command {
    
    String commandName, args;
    GuildMessageReceivedEvent event;

    public Command(String commandName, String args, GuildMessageReceivedEvent event) throws UnsupportedAudioFileException {
        this.commandName = commandName;
        this.args = args;
        this.event = event;
        
        executeCommand(commandName, args, event);
    }

    public void executeCommand(String cmd, String args, GuildMessageReceivedEvent event) throws UnsupportedAudioFileException
    {
        switch(cmd)
        {
            case "!ping":
                
                Ping ping = new Ping(cmd, event);
                ping.execute();
                System.out.println("\tCommand " + cmd + " launched !");
                break;
              
            case "!ia":
                
                if(EventManager.ia)
                    EventManager.ia = false;
                else
                    EventManager.ia = true;
                
                break;
            
            case "!say":
                
                Say say = new Say(cmd, args, event);
                say.execute();
                System.out.println("\tCommand " + cmd + " launched !");
                
                break;
                
            case "!rate":
                
                if(args != null)
                {
                    Rate rate = new Rate(cmd, args, event);
                    rate.execute();
                    
                    System.out.println("\tCommand " + cmd + " launched !");
                }
                else
                    event.getChannel().sendMessage("Qui ça?");
                break;
            
            case "!def":
                
                    BatonScore batonScore = new BatonScore(cmd, args, event);
                    batonScore.getScore();
                    
                    System.out.println("\tCommand " + cmd + " launched !");
                    
                break;
            case "!score":
                
               
                    Scoring scoring = new Scoring(cmd, args, event);
                    scoring.getScore();
                    System.out.println("\tCommand " + cmd + " launched !");
                
                break;
                
            case "!setscore":
                
                if(args != null)
                {
                    Scoring scoring2 = new Scoring(cmd, args, event);
                    scoring2.setScore();
                    System.out.println("\tCommand " + cmd + " launched !");
                }
                
                break;
        
                
            case "!boobs":
                
                Boobs boobs = new Boobs(cmd, args, event);
                boobs.execute();
                System.out.println("\tCommand " + cmd + " launched !");
                break;
            case "!legs":
                
                Legs legs = new Legs(cmd, args, event);
                legs.execute();
                System.out.println("\tCommand " + cmd + " launched !");
                
                break;
            
            case "!nappe":
                
                Nappe nappe = new Nappe(cmd, args, event);
                nappe.execute();
                System.out.println("\tCommand " + cmd + " launched !");
                
                break;
                
//            case "!play":
//                
//                Play play1 = new Play(cmd, args, event);
//                play1.execute();
//                System.out.println("\tCommand " + cmd + " launched !");
//                break;
//                
//            case "!leave":
//                
//                Play play2 = new Play(cmd, args, event);
//                play2.leave();
//                System.out.println("\tCommand " + cmd + " launched !");
//                break;
//                
//            case "!pause":
//                
//                Play play3 = new Play(cmd, args, event);
//                play3.pause();
//                System.out.println("\tCommand " + cmd + " launched !");
//                break;
//                
//            case "!stop":
//                
//                Play play4 = new Play(cmd, args, event);
//                play4.stop();
//                System.out.println("\tCommand " + cmd + " launched !");
//                break;
//                
//            case "!restart":
//                
//                Play play5 = new Play(cmd, args, event);
//                play5.restart();
//                System.out.println("\tCommand " + cmd + " launched !");
//                break;
            default:
                
                System.out.println("Not a Valid Command");
                
                break;
        }
    }
}
