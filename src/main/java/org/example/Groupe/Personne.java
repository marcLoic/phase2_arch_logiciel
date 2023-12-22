package org.example.Groupe;

import java.util.List;
import java.util.HashMap;

/************************************************************/
/**
 *
 */
public class Personne extends SqlUtils {
    /**
     *
     */
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
    private String mail;

    /**
     * Constructeur pour un �l�ve.
     *
     * @param id L'identifiant unique de l'�l�ve
     * @param prenom Le pr�nom de l'�l�ve
     * @param nom Le nom de famille de l'�l�ve
     * @param mail L'adresse email de l'�l�ve
     */
    public Personne(String id, String prenom, String nom, String mail) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.mail = mail;
    }

    /**
     * Enregistre un nouvel �l�ve dans la base de donn�es.
     */
    public void save() {
        this.connect();
        this.requestUpdate(String.format("INSERT INTO ELEVE VALUES('%s','%s','%s','%s')",
                this.id, this.prenom, this.nom, this.mail));
        this.disconnect();
    }

    /**
     * Met � jour les informations de l'�l�ve dans la base de donn�es.
     */
    public void update() {
        this.connect();
        this.requestUpdate(String.format("UPDATE ELEVE SET prenom='%s',nom='%s',mail='%s' WHERE id='%s'",
                this.prenom, this.nom, this.mail, this.id));
        this.disconnect();
    }

    // Vous pouvez d�finir des m�thodes suppl�mentaires comme delete(), getById(), getAll(), getAllByFilter() selon vos besoins.

    // Getters et Setters pour chaque propri�t� peuvent �galement �tre ajout�s ici.
};
