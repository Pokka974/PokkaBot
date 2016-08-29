/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.pokkabot;


/**
 *
 * @author Pokka
 */
public class Members {
    
    String id, pseudo, guildID;
    int score, partiesJouees, defaites;

    public Members() {
    }

    
    public Members(String id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
    }

    public int getPartiesJouees() {
        return partiesJouees;
    }

    public void setPartiesJouees(int partiesJouees) {
        this.partiesJouees = partiesJouees;
    }

    public int getDefaites() {
        return defaites;
    }

    public void setDefaites(int defaites) {
        this.defaites = defaites;
    }

    public String getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getGuild() {
        return guildID;
    }

    public int getScore() {
        return score;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setGuildID(String guildID) {
        this.guildID = guildID;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Members{" + "id=" + id + ", pseudo=" + pseudo + ", guildID=" + guildID + ", score=" + score + ", partiesJouees=" + partiesJouees + ", defaites=" + defaites + '}';
    }

    
    
    
    
}
