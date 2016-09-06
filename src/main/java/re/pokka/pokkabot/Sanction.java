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
public class Sanction {
    int id;
    String idStaffConcerne, idMembreSanctionne;
    String raison, type;
    String date;

    public Sanction() {
    }

    public Sanction(int id, String idStaffConcerne, String idMembreSanctionne, String raison, String type, String date) {
        this.id = id;
        this.idStaffConcerne = idStaffConcerne;
        this.idMembreSanctionne = idMembreSanctionne;
        this.raison = raison;
        this.type = type;
        this.date = date;
    }

    public String getIdStaffConcerne() {
        return idStaffConcerne;
    }

    public void setIdStaffConcerne(String idStaffConcerne) {
        this.idStaffConcerne = idStaffConcerne;
    }

    public String getIdMembreSanctionne() {
        return idMembreSanctionne;
    }

    public void setIdMembreSanctionne(String idMembreSanctionne) {
        this.idMembreSanctionne = idMembreSanctionne;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

  
}
