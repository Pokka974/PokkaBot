/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.pokkabot;

import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import re.pokka.db.MemberManager;

/**
 *
 * @author Pokka
 */
public class Baton extends ListenerAdapter{
    User player = EventManager.batonPlayer;
    int nombreBaton = 21;
    String baton = "bâton";
    String batons = "bâtons";
    Members actualPlayer;
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        
        int nombreDuJoueur = 0, nombreDuBot = 0;
        String content = event.getMessage().getContent();
        MessageChannel chan = event.getChannel();
        
       
        if(event.getAuthor().getId().equals(player.getId()))
        {
            nombreDuJoueur = content.equals("3") ? 3 :
                                content.equals("2") ? 2 :
                                    content.equals("1") ? 1 :
                                        0;
            
            if(nombreDuJoueur > 0 && nombreDuJoueur < 4)
            {
                if(nombreBaton == 1)
                {
                    if(MemberManager.get_Instance().getMembers(player.getId()) != null)
                    {
                        actualPlayer = MemberManager.get_Instance().getMembers(player.getId());
                        if(actualPlayer.getDefaites() > 0)
                        {
                            int defaitesAvant = actualPlayer.getDefaites();
                            actualPlayer.setDefaites(defaitesAvant++);
                        }
                        else
                            actualPlayer.setDefaites(1);

                        if(actualPlayer.getPartiesJouees() > 0)
                        {
                            int partiesJoueesAvant = actualPlayer.getPartiesJouees();
                            actualPlayer.setPartiesJouees(partiesJoueesAvant++);
                        }
                        else
                            actualPlayer.setPartiesJouees(1);
                    }
                    else
                    {
                        actualPlayer = new Members();
                        actualPlayer.setId(player.getId());
                        actualPlayer.setPseudo(player.getUsername());
                        actualPlayer.setDefaites(1);
                        actualPlayer.setPartiesJouees(1);
                    }
                    
                    MemberManager.get_Instance().putMembers(actualPlayer.getId(), actualPlayer);
                    
                    chan.sendMessage("C'est perdu ! Dommage ! :joy:\n"
                            + "Vous avez maintenant **" + actualPlayer.getDefaites() +"** défaites");
                    
                    event.getJDA().removeEventListener(this);
                }
                else
                {
                    nombreBaton -= nombreDuJoueur;
                    
                    if(nombreBaton < 4)
                        nombreDuBot = nombreBaton - 1;
                    else
                        nombreDuBot =  4 - nombreDuJoueur ;

                    if(nombreDuBot > 1)
                        chan.sendMessage("Dans ce cas je vais prendre **" + nombreDuBot + "** bâtons");
                    else
                        chan.sendMessage("Dans ce cas je vais prendre **" + nombreDuBot + "** "+ baton);

                    nombreBaton -= nombreDuBot;


                    if(nombreBaton == 0)
                    {
                        chan.sendMessage("T'as gagné, Bravo... :unamused:");
                        event.getJDA().removeEventListener(this);
                        return;
                    }

                    if(nombreBaton > 1)
                        chan.sendMessage("Il reste **" + nombreBaton + "** bâtons\nÀ toi de jouer " + player.getAsMention());
                    else
                        chan.sendMessage("Il reste **" + nombreBaton + "** " + baton +"\nÀ toi de jouer " + player.getAsMention());
                }
            }
        }
    }
}
