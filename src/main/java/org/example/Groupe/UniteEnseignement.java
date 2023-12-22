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
public class UniteEnseignement extends SqlUtils {
    /**
     *
     */
    private String id;
    /**
     *
     */
    private String code;
    /**
     *
     */
    private String intitule;
    /**
     *
     */
    private float cours;
    /**
     *
     */
    private float td;
    /**
     *
     */
    private float tp;
    /**
     *
     */
    private float valeur;
    /**
     *
     */
    public Eleve[] eleve;
    /**
     *
     */
    public Sujet[] sujet;

    /**
     *
     * @param id
     * @param code
     * @param intitule
     * @param cours
     * @param td
     * @param tp
     * @param valeur
     */
    public UniteEnseignement(String id, String code, String intitule, float cours, float td, float tp, float valeur) {
        this.id = id;
        this.code = code;
        this.intitule = intitule;
        this.cours = cours;
        this.td = td;
        this.tp = tp;
        this.valeur = valeur;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public float getCours() {
        return cours;
    }

    public void setCours(float cours) {
        this.cours = cours;
    }

    public float getTd() {
        return td;
    }

    public void setTd(float td) {
        this.td = td;
    }

    public float getTp() {
        return tp;
    }

    public void setTp(float tp) {
        this.tp = tp;
    }

    public float getValeur() {
        return valeur;
    }

    public void setValeur(float valeur) {
        this.valeur = valeur;
    }

    public Eleve[] getEleve() {
        return eleve;
    }

    public void setEleve(Eleve[] eleve) {
        this.eleve = eleve;
    }

    public Sujet[] getSujet() {
        return sujet;
    }

    public void setSujet(Sujet[] sujet) {
        this.sujet = sujet;
    }

    public void save() {
        this.connect();

        this.requestUpdate(String.format("INSERT INTO UniteEnseignement VALUES('%s','%s','%s',%s,%s,%s,%s)", this.id,
                this.code, this.intitule, String.valueOf(this.cours), String.valueOf(this.td), String.valueOf(this.tp),
                String.valueOf(this.valeur)));
        this.disconnect();
    }

    public void update() {
        this.connect();
        this.requestUpdate(String.format(
                "UPDATE UniteEnseignement SET code='%s',intitule='%s',cours=%s,td=%s,tp=%s,valeur=%s WHERE id='%s'", this.code,
                this.intitule, String.valueOf(this.cours), String.valueOf(this.td), String.valueOf(this.tp),
                String.valueOf(this.valeur), this.id));
        this.disconnect();
    }

    public void delete() {
        this.connect();
        this.requestUpdate(String.format("DELETE FROM UniteEnseignement WHERE id='%s'", this.id));
        this.disconnect();

    }

    public static UniteEnseignement getById(String id) {
        SqlUtils sql = new SqlUtils();
        sql.connect();
        ResultSet set = sql.requestSelect(String.format("SELECT * FROM UniteEnseignement WHERE id='%s'", id));


        try {
            UniteEnseignement ue = new UniteEnseignement(set.getString("id"), set.getString("code"),
                    set.getString("intitule"), set.getFloat("cours"), set.getFloat("td"), set.getFloat("tp"),
                    set.getFloat("valeur"));
            sql.disconnect();
            return ue;
        } catch (SQLException e) {
            e.printStackTrace();
            sql.disconnect();
            return null;
        }

    }

    public static List<UniteEnseignement> getAll() {
        SqlUtils sql = new SqlUtils();
        sql.connect();
        ResultSet set = sql.requestSelect(String.format("SELECT * FROM UniteEnseignement "));

        List<UniteEnseignement> result = new ArrayList<UniteEnseignement>();

        try {
            while (set.next()) {
                UniteEnseignement ue = new UniteEnseignement(set.getString("id"), set.getString("code"),
                        set.getString("intitule"), set.getFloat("cours"), set.getFloat("td"), set.getFloat("tp"),
                        set.getFloat("valeur"));
                result.add(ue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sql.disconnect();
            return null;
        }
        sql.disconnect();
        return result;
    }

    @Override
    public String toString() {
        return "" + id + " ; " + code + " ; " + intitule + " ; " + cours
                + " ; " + td + " ; " + tp + " ; " + valeur + " \n " ;
    }

};
