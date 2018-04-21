/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasper;

import entity.Cesta;
import entity.Ucastnik;
import org.json.JSONObject;

/**
 *
 * @author sosyn
 */
public class Jasper {

    public Jasper() {
        System.out.println("");
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        Cesta cesta=new Cesta();
        Ucastnik uc=new Ucastnik();
        Ucastnik uc1=new Ucastnik();
        JSONObject jsonRoot=new JSONObject(cesta);
        jsonRoot.append("Ucastnici", new JSONObject(uc) );
        jsonRoot.append("Ucastnici", new JSONObject(uc1) );
        
        System.out.println(jsonRoot.toString());
    }
    
}
