/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import entity.Cesta;
import entity.Rezervace;
import entity.Schvaleni;
import entity.Ucastnik;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author ivo
 */
public class JsonUtil {

    public static String getJsonCesta(Cesta cesta) {
        StringWriter stringWriter = new StringWriter();
        JsonObjectBuilder jsonRoot = Json.createObjectBuilder();
        JsonObjectBuilder jsonUcObj;
        JsonObjectBuilder jsonRezObj;
        JsonArrayBuilder jsonUcArr = Json.createArrayBuilder();
        JsonArrayBuilder jsonRezArr = Json.createArrayBuilder();
        if (cesta == null) {
            jsonRoot.add("Cesta", "NULL");
            return jsonRoot.build().toString();
        }
        jsonRoot.add("cesta-UUID", jsonString(cesta.getId()))
                .add("cesta-popis", jsonString(cesta.getPopis()))
                .add("cesta-komentar", jsonString(cesta.getKomentar()))
                .add("cesta-zaloha", cesta.getZaloha())
                .add("cesta-platiod", jsonString(cesta.getPlatiod()))
                .add("cesta-platido", jsonString(cesta.getPlatido()))
                .add("cesta-osoba-popis", jsonString(cesta.getIdoso().getPopis()))
                .add("cesta-osoba-komentar", jsonString(cesta.getIdoso().getKomentar()));
        for (Ucastnik ucastnik : cesta.getUcastnikList()) {
            jsonUcObj = Json.createObjectBuilder()
                    .add("ucastnik-UUID", jsonString(ucastnik.getId()))
                    .add("ucastnik-popis", jsonString(ucastnik.getIdtypucast().getPopis()))
                    .add("ucastnik-platiod", jsonString(ucastnik.getPlatiod()))
                    .add("ucastnik-platido", jsonString(ucastnik.getPlatido()))
                    .add("ucastnik-osoba-popis", jsonString(ucastnik.getIdoso().getPopis()))
                    .add("ucastnik-osoba-komentar", jsonString(ucastnik.getIdoso().getKomentar()))
                    .add("ucastnik-schvaleni-stav", 0)
                    .add("ucastnik-schvaleni-uroven", 0)
                    .add("ucastnik-schvaleni-popis", " ")
                    .add("ucastnik-schvaleni-komentar", " ")
                    .add("ucastnik-schvaleni-platiod", " ")
                    .add("ucastnik-schvaleni-platido", " ")
                    .add("ucastnik-schvaleni-osoba-popis", " ")
                    .add("ucastnik-schvaleni-osoba-komentar", " ");
            if (ucastnik.getSchvList() != null && !ucastnik.getSchvList().isEmpty()) {
                Schvaleni schvaleni = ucastnik.getSchvList().get(ucastnik.getSchvList().size() - 1);
                jsonUcObj.add("ucastnik-schvaleni-stav", schvaleni.getStav())
                        .add("ucastnik-schvaleni-uroven", schvaleni.getUroven())
                        .add("ucastnik-schvaleni-popis", jsonString(schvaleni.getPopis()))
                        .add("ucastnik-schvaleni-komentar", jsonString(schvaleni.getKomentar()))
                        .add("ucastnik-schvaleni-platiod", jsonString(schvaleni.getPlatiod()))
                        .add("ucastnik-schvaleni-platido", jsonString(schvaleni.getPlatido()))
                        .add("ucastnik-schvaleni-osoba-popis", jsonString(schvaleni.getIdoso().getPopis()))
                        .add("ucastnik-schvaleni-osoba-komentar", jsonString(schvaleni.getIdoso().getKomentar()));
            }
            jsonUcArr.add(jsonUcObj);
        }
        for (Rezervace rezervace : cesta.getRezervaceList()) {
            jsonRezObj = Json.createObjectBuilder()
                    .add("rezervace-UUID", jsonString(rezervace.getId()))
                    .add("rezervace-platiod", jsonString(rezervace.getPlatiod()))
                    .add("rezervace-platido", jsonString(rezervace.getPlatido()))
                    .add("rezervace-popis", jsonString(rezervace.getPopis()))
                    .add("rezervace-komentar", jsonString(rezervace.getKomentar()))
                    .add("rezervace-zdroj-popis", jsonString(rezervace.getIdzdr().getPopis()))
                    .add("rezervace-zdroj-komentar", jsonString(rezervace.getIdzdr().getKomentar()))
                    .add("rezervace-zdroj-spz", jsonString(rezervace.getIdzdr().getSpz()))
                    .add("rezervace-schvaleni-stav", 0)
                    .add("rezervace-schvaleni-uroven", 0)
                    .add("rezervace-schvaleni-popis", " ")
                    .add("rezervace-schvaleni-komentar", " ")
                    .add("rezervace-schvaleni-platiod", " ")
                    .add("rezervace-schvaleni-platido", " ")
                    .add("rezervace-schvaleni-osoba-popis", " ")
                    .add("rezervace-schvaleni-osoba-komentar", " ");
            if (rezervace.getSchvList() != null && !rezervace.getSchvList().isEmpty()) {
                Schvaleni schvaleni = rezervace.getSchvList().get(rezervace.getSchvList().size() - 1);
                jsonRezObj.add("rezervace-schvaleni-stav", schvaleni.getStav())
                        .add("rezervace-schvaleni-uroven", schvaleni.getUroven())
                        .add("rezervace-schvaleni-popis", jsonString(schvaleni.getPopis()))
                        .add("rezervace-schvaleni-komentar", jsonString(schvaleni.getKomentar()))
                        .add("rezervace-schvaleni-platiod", jsonString(schvaleni.getPlatiod()))
                        .add("rezervace-schvaleni-platido", jsonString(schvaleni.getPlatido()))
                        .add("rezervace-schvaleni-osoba-popis", jsonString(schvaleni.getIdoso().getPopis()))
                        .add("rezervace-schvaleni-osoba-komentar", jsonString(schvaleni.getIdoso().getKomentar()));
            }
            jsonRezArr.add(jsonRezObj);
        }
        // Zapsani do hlavniho JSON kmene
        jsonRoot.add("UCASTNICI", jsonUcArr);
        jsonRoot.add("REZERVACE", jsonRezArr);
        // Zapis do retezce 
        Json.createWriter(stringWriter).writeObject(jsonRoot.build());
        try {
            File outFile;
//            outFile = new File(System.getenv("TEMP") + "\\Cesta.json");
            outFile = File.createTempFile("cesta", ".json", new File(System.getenv("TEMP")));
            try (FileWriter fw = new FileWriter(outFile)) {
                fw.write(stringWriter.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stringWriter.toString();
    }

    private static String jsonString(Object obj) {
        StringBuilder strBuilder = new StringBuilder();
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            try {
//                byte[] pole=((String) obj).getBytes("UTF-8");
                byte[] pole=((String) obj).getBytes("cp1250");
                obj = new String(pole,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            strBuilder.append(obj);
        }
        if (obj instanceof Date) {
            SimpleDateFormat sdt = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault());
            strBuilder.append(sdt.format(obj));
        }
        if (obj instanceof UUID) {
            strBuilder.append(obj.toString());
        }

        return strBuilder.toString();
    }
}
