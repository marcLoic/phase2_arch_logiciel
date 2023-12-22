package org.example.Groupe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/************************************************************/
/**
 *
 */
public class Eleve extends SqlUtils {
    /**
     *
     */
    // test commit

    private String id;
    /**
     *
     */
    private String prenom;
    /**
     *
     */
    private String nom;
    /**
     *
     */
    public Personne[] personne;

    /**
     *
     * @param id
     * @param prenom
     * @param nom
     */
    public Eleve(String id, String prenom, String nom) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    public void save() {
        this.connect();
        this.requestUpdate(String.format("INSERT INTO ELEVE (id, prenom, nom) VALUES('%s','%s','%s')",
                this.id, this.prenom, this.nom));
        this.disconnect();
    }

    public void update() {
        this.connect();
        this.requestUpdate(String.format("UPDATE ELEVE SET prenom=%s,nom='%s' WHERE id='%s'",
                this.prenom, this.nom, this.id));
        this.disconnect();
    }

    public void delete() {
        this.connect();
        this.requestUpdate(String.format("DELETE FROM ELEVE WHERE id='%s'", this.id));
        this.disconnect();

    }

    public static Eleve getById(String id) {
        SqlUtils sql = new SqlUtils();
        sql.connect();
        ResultSet set = sql.requestSelect(String.format("SELECT * FROM ELEVE WHERE id='%s'", id));


        try {
            Eleve eleve = new Eleve(set.getString("id"), set.getString("prenom"), set.getString("nom"));
            sql.disconnect();
            return eleve;
        } catch (SQLException e) {
            e.printStackTrace();
            sql.disconnect();
            return null;
        }

    }

    @Override
    public String toString() {
        return "" + id + " ; " + prenom + " ; " + nom + " \n";
    }

    public static List<Eleve> getAll() {
        SqlUtils sql = new SqlUtils();
        sql.connect();
        ResultSet set = sql.requestSelect(String.format("SELECT * FROM ELEVE "));

        List<Eleve> result = new ArrayList<Eleve>();

        try {
            while (set.next()) {
                Eleve eleve = new Eleve(set.getString("id"), set.getString("prenom"), set.getString("nom"));
                result.add(eleve);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sql.disconnect();
            return null;
        }
        sql.disconnect();
        return result;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setnom(String nom) {
        this.nom = nom;
    }

    public Personne[] getPersonne() {
        return personne;
    }

    public void setPersonne(Personne[] personne) {
        this.personne = personne;
    }
};