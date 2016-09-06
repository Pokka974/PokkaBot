/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.db;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import re.pokka.pokkabot.Sanction;

/**
 *
 * @author Pokka
 */
public class SanctionBinding extends TupleBinding {

    @Override
    public Object entryToObject(TupleInput input) {
        
        int id = input.readInt();
        String idStaffConcerne = input.readString();
        String idMembreSanctionne = input.readString();
        String raison = input.readString();
        String type = input.readString();
        String date = input.readString();
        
        Sanction sanction = new Sanction();
        
        sanction.setId(id);
        sanction.setIdStaffConcerne(idStaffConcerne);
        sanction.setIdMembreSanctionne(idMembreSanctionne);
        sanction.setRaison(raison);
        sanction.setType(type);
        sanction.setDate(date);
        
        return sanction;
        
    }

    @Override
    public void objectToEntry(Object e, TupleOutput output) {
        Sanction sanction = (Sanction) e;
        
        output.writeInt(sanction.getId());
        output.writeString(sanction.getIdStaffConcerne());
        output.writeString(sanction.getIdMembreSanctionne());
        output.writeString(sanction.getRaison());
        output.writeString(sanction.getType());
        output.writeString(sanction.getDate());
    }
    
}
