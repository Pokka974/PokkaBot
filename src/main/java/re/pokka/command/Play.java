/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.command;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import net.dv8tion.jda.audio.player.Player;
import net.dv8tion.jda.audio.player.URLPlayer;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.entities.VoiceStatus;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;

/**
 *
 * @author Pokka
 */
public class Play  {
    String cmd, args;
    GuildMessageReceivedEvent event;
    private Map<String,Player> players = new HashMap<>();
    Player player;
    
    public Play(String cmd, String args, GuildMessageReceivedEvent event) {
        this.cmd = cmd;
        this.args = args;
        this.event = event;
    }
    
    public static InputStream openStream(String url) throws IOException {
        final URL url2 = new URL(url);
        final URLConnection con = url2.openConnection();
        con.setRequestProperty("User-Agent", "My Client");
        return con.getInputStream();
    }
    
    public void execute() throws UnsupportedAudioFileException 
    {
        VoiceChannel channel = getVoiceForUser(event.getAuthor(), event.getGuild());
        player = players.get(event.getGuild().getId());
        
        
        if (event.getGuild().getAudioManager().isConnected())
            event.getGuild().getAudioManager().moveAudioConnection(channel);
        else event.getGuild().getAudioManager().openAudioConnection(channel);
            event.getChannel().sendMessage("Joined `" + channel.getName() + "`.");
            
        if(player == null)
        {
            URL audioUrl = null;
            
            try
            {
                
                AudioInputStream ais = AudioSystem.getAudioInputStream(openStream(args));
                player.setAudioSource(ais);
                players.put(event.getGuild().getId(), player);
                
                event.getGuild().getAudioManager().setSendingHandler(player);
                
                player.play();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if (player.isStarted() && player.isStopped())  //If it did exist, has it been stop()'d before?
        {
            event.getChannel().sendMessage("The player has been stopped. To start playback, please use 'restart'");
            return;
        }
        else    //It exists and hasn't been stopped before, so play. Note: if it was already playing, this will have no effect.
        {
            player.play();
        }
    }
    
    public void leave()
    {
        event.getGuild().getAudioManager().closeAudioConnection();
    }
    
    public void pause()
    {
        if(player == null)
        {
            event.getChannel().sendMessage("You need to 'play' before you can preform that command.");
            return;
        }
        player.pause();
    }
    
    public void stop()
    {
        if(player == null)
        {
            event.getChannel().sendMessage("You need to 'play' before you can preform that command.");
            return;
        }
        player.stop();
    }
    
    public void restart()
    {
        if(player == null)
        {
            event.getChannel().sendMessage("You need to 'play' before you can preform that command.");
            return;
        }
        player.restart();
    }
    
    
    public static VoiceChannel getVoiceForUser(User user, Guild guild)
    {
            assert guild != null && user != null;
            VoiceStatus status = guild.getVoiceStatusOfUser(user);
            return status != null ? status.getChannel() : null;
    }
}
