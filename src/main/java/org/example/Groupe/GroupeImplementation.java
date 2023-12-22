package org.example.Groupe;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/************************************************************/
/**
 *
 */
public class GroupeImplementation implements GroupeInterface {

    public void initDatabase() throws ClassNotFoundException {
        Connection conn = null;
        String url = "jdbc:sqlite:data.db";
        try {

            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql = "CREATE TABLE IF NOT EXISTS Personne(ID TEXT PRIMARY KEY,prenom TEXT,nom TEXT,mail TEXT,status TEXT)";
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("personne table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS UniteEnseignement(ID TEXT PRIMARY KEY,code TEXT,intitule TEXT,cours REAL,td REAL,tp REAL,valeur REAL)";
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("Ue table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "CREATE TABLE IF NOT EXISTS ELEVE(ID TEXT PRIMARY KEY,prenom TEXT,nom TEXT)";
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("Eleve table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "CREATE TABLE IF NOT EXISTS SUJET(ID TEXT PRIMARY KEY,titre TEXT,fin TEXT,jour TEXT,eleve TEXT,uniteEnseignement TEXT, FOREIGN KEY(eleve) REFERENCES eleve(id),FOREIGN KEY(uniteEnseignement) REFERENCES UniteEnseignement(id))";
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("SUJET table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "CREATE TABLE IF NOT EXISTS GROUPE(ID TEXT PRIMARY KEY, eleveID TEXT, sujetID TEXT, ueID TEXT, FOREIGN KEY(eleveID) REFERENCES ELEVE(ID), FOREIGN KEY(sujetID) REFERENCES SUJET(ID), FOREIGN KEY(ueID) REFERENCES UniteEnseignement(ID))";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("GROUPE table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public String createEU(String JSONEntry) {
        // TODO Auto-generated method stub
        String intitule = null;
        float tp = 0;
        float cours = 0;
        float valeur = 0;
        float td = 0;
        String code = null;
        String id = null;
        JSONObject obj = new JSONObject(JSONEntry);
        try {
            id = obj.getString("id");
            code = obj.getString("code");
            intitule = obj.getString("intitule");
            cours = Float.parseFloat(obj.getString("cours"));
            td = Float.parseFloat(obj.getString("td"));
            tp = Float.parseFloat(obj.getString("tp"));
            valeur = Float.parseFloat(obj.getString("valeur"));

        } catch (JSONException e) {
            System.out.println("Unexpected json file, should be: code,intitule,cours,td,tp,valeur");

        }
        UniteEnseignement UE = new UniteEnseignement(id, code, intitule, cours, td, tp, valeur);

        UE.save();

        String ret = "{ \"id\": \"" + id + "\"}";

        return ret;
    }

    @Override
    public String createSujet(String JSONEntry) {
        // TODO Auto-generated method stub
        // init
        String id = null;
        String titre = null;
        LocalTime fin = LocalTime.now();
        LocalDate jour = LocalDate.now();
        // parse
        JSONObject obj = new JSONObject(JSONEntry);
        id = obj.getString("id");
        titre = obj.getString("titre");
        fin = LocalTime.parse(obj.getString("fin"));

        jour = LocalDate.parse(obj.getString("jour"));
        // create json
        Sujet sujet = new Sujet(id, titre, fin, jour);
        String ret = "{ \"id\": \"" + id + "\"}";
        sujet.save();
        return ret;

    }

    @Override
    public String createEleve(String JSONEntry) {
        // TODO Auto-generated method stub

        String prenom = null;
        String nom = null;
        String id = null;
        JSONObject obj = new JSONObject(JSONEntry);

        try {
            id = obj.getString("id");
            prenom = obj.getString("prenom");
            nom = obj.getString("nom");
        } catch (JSONException e) {

            System.out.println("Unexpected json file, should be: prenom,nom");

        }

        Eleve eleve = new Eleve(id, prenom, nom);

        String ret = "{ \"id\": \"" + id + "\"}";
        eleve.save();
        return ret;

    }

    @Override
    public String createGroupe(String JSONEntry) {
        // Initialize variables
        String eleveID = null;
        String sujetID = null;
        String ueID = null;
        String id = null;
        JSONObject obj = new JSONObject(JSONEntry);

        // Parse the JSON object
        try {
            id = obj.getString("id");
            eleveID = obj.getString("eleveID");
            sujetID = obj.getString("sujetID");
            ueID = obj.getString("ueID");
        } catch (JSONException e) {
            System.out.println("Unexpected JSON format. Expected fields: id, eleveID, sujetID, ueID");
            return "{\"error\": \"Invalid JSON format\"}";
        }

        // Create a Groupe object and save it to the database
        Groupe groupe = new Groupe(id, eleveID, sujetID, ueID);
        groupe.save();

        // Return a confirmation JSON string
        String ret = "{ \"id\": \"" + id + "\"}";
        return ret;
    }


    @Override
    public String changeSujetGroupe(String JSONEntry) {

        // TODO Auto-generated method stub
        String UE = null;
        String eleve = null;
        String sujet = null;
        JSONObject obj = new JSONObject(JSONEntry);
        try {

            eleve = obj.getString("eleve");
            sujet = obj.getString("sujet");

            Eleve objEleve = Eleve.getById(eleve);
            Sujet cr = Sujet.getById(sujet);

            cr.setEleve(objEleve);
            cr.update();

        } catch (JSONException e) {
            System.out.println("Unexpected json file, should be: UE,eleve,sujet");

        }
        // String id = UUID.randomUUID().toString();

        // String ret = "{ \"id\": \"" + id + "\"}";
        return "{ \"result\": \"done\"}";
        // return ret;

    }

    @Override
    public String createGroupeSujet(String JSONEntry) {
        // TODO Auto-generated method stub
        String UE = null;
        String eleve = null;
        String sujet = null;
        JSONObject obj = new JSONObject(JSONEntry);
        try {

            eleve = obj.getString("eleve");
            sujet = obj.getString("sujet");

            Eleve objEleve = Eleve.getById(eleve);
            Sujet cr = Sujet.getById(sujet);

            cr.setEleve(objEleve);
            cr.update();

        } catch (JSONException e) {
            System.out.println("Unexpected json file, should be: UE,eleve,sujet");

        }
        // String id = UUID.randomUUID().toString();

        // String ret = "{ \"id\": \"" + id + "\"}";
        return "{ \"result\": \"done\"}";
        // return ret;
    }

    @Override
    public String deleteEU(String JSONEntry) {
        // TODO Auto-generated method stub
        String UUID = null;
        JSONObject obj = new JSONObject(JSONEntry);
        try {

            UUID = obj.getString("UUID");

        } catch (JSONException e) {
            System.out.println("Unexpected json file, should be: UUID");

        }
        try {
            UniteEnseignement.getById(UUID).delete();
            return "{ \"result\": \"done\" ,  \"type\": \"EU\" , \"UUID\": \"" + UUID + "\"  }";

        } catch (Exception e) {
            return "{ \"result\": \"error\" ,  \"type\": \"EU inexistant\"   }";

        }
    }

    @Override
    public String deleteSujet(String JSONEntry) {
        // TODO Auto-generated method stub
        String UUID = null;
        JSONObject obj = new JSONObject(JSONEntry);
        try {

            UUID = obj.getString("UUID");

        } catch (JSONException e) {
            System.out.println("Unexpected json file, should be: UUID");

        }
        try {
            Sujet.getById(UUID).delete();
            return "{ \"result\": \"done\" ,  \"type\": \"sujet\" , \"UUID\": \"" + UUID + "\"  }";
        } catch (Exception e) {
            return "{ \"result\": \"error\" ,  \"type\": \"sujet inexistant\"   }";

        }
    }

    @Override
    public String deleteGroupe(String JSONEntry) {

        String UUID = null;
        JSONObject obj = new JSONObject(JSONEntry);
        try {

            UUID = obj.getString("UUID");

        } catch (JSONException e) {
            System.out.println("Unexpected json file, should be: UUID");

        }
        try {
            Groupe.getById(UUID).delete();
            return "{ \"result\": \"done\" ,  \"type\": \"Groupe\" , \"GID\": \"" + UUID + "\"  }";
        } catch (Exception e) {
            // String ret = "{ \"id\": \"" + id + "\"}";
            return "{ \"result\": \"error\" ,  \"type\": \"groupe inexistant\"   }";
            // return ret;
        }
    }

    @Override
    public String deleteEleve(String JSONEntry) {
        String UUID = null;
        JSONObject obj = new JSONObject(JSONEntry);
        try {

            UUID = obj.getString("UUID");

        } catch (JSONException e) {
            System.out.println("Unexpected json file, should be: UUID");

        }
        try {
            Eleve.getById(UUID).delete();
            return "{ \"result\": \"done\" ,  \"type\": \"eleve\" , \"UUID\": \"" + UUID + "\"  }";
        } catch (Exception e) {
            return "{ \"result\": \"error\" ,  \"type\": \"eleve inexistant\"   }";

        }
    }

    @Override
    public String getEleve(String JSONEntry) {

        JSONObject obj = new JSONObject(JSONEntry);
        Eleve x = Eleve.getById(obj.getString("id"));
        if (x != null)
        {
            String p = x.getPrenom();
            String n = x.getNom();
            obj.put("prenom", p);
            obj.put("nom", n);
            return obj.toString() ;
        }
        else
            return "erreur";

    }

    @Override
    public String getGroupe(String JSONEntry) {
        JSONObject obj = new JSONObject(JSONEntry);
        Groupe x = Groupe.getById(obj.getString("id"));
        if (x != null)
        {
            String eID = x.getEleveID();
            String sID = x.getSujetID();
            obj.put("eleveID", eID);
            obj.put("sujetID", sID);
            return obj.toString() ;
        }
        else
            return "erreur";
    }

    @Override
    public String getSujet(String JSONEntry) {
        // TODO This should return JSON
        JSONObject obj = new JSONObject(JSONEntry);
        Sujet x = Sujet.getById(obj.getString("id"));
        if (x != null)
        {
            String tit = x.getTitre();
            LocalTime fi = x.getFin();
            LocalDate jo = x.getJour();
            obj.put("titre", tit);
            obj.put("fin",fi.toString());
            obj.put("jour", jo.toString());
            return obj.toString() ;
        }
        else
            return "erreur";

    }

    @Override
    public String getUE(String JSONEntry) {
        // TODO This should return JSON
        JSONObject obj = new JSONObject(JSONEntry);
        UniteEnseignement x = UniteEnseignement.getById(obj.getString("id"));
        if (x != null)
        {
            obj.put("code", x.getCode());
            obj.put("intitule", x.getIntitule());
            obj.put("cours", String.valueOf(x.getCours()));
            obj.put("td", String.valueOf(x.getTd()));
            obj.put("tp", String.valueOf(x.getTp()));
            obj.put("valeur", String.valueOf(x.getValeur()));
            return obj.toString() ;
        }
        else
            return "erreur";


    }

    @Override
    public String listEleve() {

        List <Eleve>  x = Eleve.getAll();
        String resp = "";
        for (int i= 0 ; i < x.size();i++)
        {
            resp = resp + x.get(i).toString();
        }
        JSONObject obj = new JSONObject();
        obj.put("response", resp);

        return obj.toString();
    }

    @Override
    public String listGroupe() {
        List <Groupe>  x = Groupe.getAll();
        String resp = "";
        for (int i= 0 ; i < x.size();i++)
        {
            resp = resp + x.get(i).toString();
        }
        JSONObject obj = new JSONObject();
        obj.put("response", resp);

        return obj.toString();
    }

    @Override
    public String listSujet() {
        // TODO Auto-generated method stub
        List <Sujet>  x = Sujet.getAll();
        String resp = "";
        for (int i= 0 ; i < x.size();i++)
        {
            resp = resp + x.get(i).toString();
        }
        JSONObject obj = new JSONObject();
        obj.put("response", resp);

        return obj.toString();
    }

    @Override
    public String listEU() {
        // TODO Auto-generated method stub
        List <UniteEnseignement>  x = UniteEnseignement.getAll();
        String resp = "";
        for (int i= 0 ; i < x.size();i++)
        {
            resp = resp + x.get(i).toString();
        }
        JSONObject obj = new JSONObject();
        obj.put("response", resp);

        return obj.toString();
    }

    //TODO methods that returns all of everything.



};
