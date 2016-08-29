/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.pokka.db;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import re.pokka.pokkabot.Members;

/**
 *
 * @author Pokka
 */
public class MembersBinding extends TupleBinding{

    @Override
    public Object entryToObject(TupleInput input) {
        
        String id = input.readString();
        String pseudo = input.readString();
        int score = input.readInt();
        
        int partiesJouees = input.readInt();
        int defaites = input.readInt();
        
        Members member = new Members();
        
        member.setId(id);
        member.setPseudo(pseudo);
        member.setScore(score);
        
        member.setPartiesJouees(partiesJouees);
        member.setDefaites(defaites);
        
        return member;
        
    }

    @Override
    public void objectToEntry(Object e, TupleOutput output) {
       
        Members member = (Members) e;
        
        output.writeString(member.getId());
        output.writeString(member.getPseudo());
        output.writeInt(member.getScore());
        
        output.writeInt(member.getPartiesJouees());
        output.writeInt(member.getDefaites());
    }
    
}
