package org.example.Groupe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/************************************************************/
/**
 *
 */
public class Sujet extends SqlUtils {
    /**
     *
     */
    private String id;
    /**
     *
     */
    private String titre;
    /**
     *
     */
    private LocalTime fin;
    /**
     *
     */
    private LocalDate jour;
    /**
     *
     */
    public Eleve eleve;
    public UniteEnseignement uniteEnseignement;

    /**
     *
     * @param id
     * @param titre
     * @param fin
     * @param jour
     */
    public Sujet(String id, String titre, LocalTime fin, LocalDate jour) {
        this.id = id;
        this.titre = titre;
        this.fin = fin;
        this.jour = jour;
    }

    public void save() {
        this.connect();
        this.requestUpdate(String.format("INSERT INTO SUJET VALUES('%s','%s','%s','%s','%s','%s')", this.id,
                this.titre, this.fin.toString(), this.jour.toString(),
                this.eleve == null ? "" : this.eleve.getId(),
                this.uniteEnseignement == null ? "" : this.uniteEnseignement.getId()));
        this.disconnect();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public LocalDate getJour() {
        return jour;
    }

    public void setJour(LocalDate jour) {
        this.jour = jour;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public UniteEnseignement getUniteEnseignement() {
        return uniteEnseignement;
    }

    public void setUniteEnseignement(UniteEnseignement uniteEnseignement) {
        this.uniteEnseignement = uniteEnseignement;
    }

    public void update() {
        this.connect();
        this.requestUpdate(String.format(
                "UPDATE SUJET SET titre='%s',fin='%s',jour='%s',eleve='%s',uniteEnseignement='%s' WHERE id='%s'",
                this.id, this.titre, this.fin.toString(), this.jour.toString(),
                this.eleve == null ? "" : this.eleve.getId(),
                this.uniteEnseignement == null ? "" : this.uniteEnseignement.getId(), this.id));
        this.disconnect();
    }

    public void delete() {
        this.connect();
        this.requestUpdate(String.format("DELETE FROM SUJET WHERE id='%s'", this.id));
        this.disconnect();

    }

    public static Sujet getById(String id) {
        SqlUtils sql = new SqlUtils();
        sql.connect();
        ResultSet set = sql.requestSelect(String.format("SELECT * FROM SUJET WHERE id='%s'", id));

        try {
            Sujet sujet = new Sujet(set.getString("id"), set.getString("titre"),
                    LocalTime.parse(set.getString("fin")), LocalDate.parse(set.getString("jour")));
            sql.disconnect();
            return sujet;
        } catch (SQLException e) {
            e.printStackTrace();
            sql.disconnect();
            return null;
        }
    }

    public static List<Sujet> getAll() {
        SqlUtils sql = new SqlUtils();
        sql.connect();
        ResultSet set = sql.requestSelect(String.format("SELECT * FROM SUJET"));

        List<Sujet> result = new ArrayList<Sujet>();

        try {
            while (set.next()) {
                Sujet sujet = new Sujet(set.getString("id"), set.getString("titre"),
                        LocalTime.parse(set.getString("fin")), LocalDate.parse(set.getString("jour")));
                result.add(sujet);
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
        return "" + id + " ; " + titre + " ; " + fin + " ; " + jour + "\n";
    }

};