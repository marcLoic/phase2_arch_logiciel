package org.example.Groupe;


public interface GroupeInterface {
    /**
     *
     */
    public String createEU(String JSONEntry);

    /**
     *
     */
    public String createSujet(String JSONEntry);

    /**
     *
     */
    public String createEleve(String JSONEntry);

    /**
     *
     */
    public String createGroupe(String JSONEntry);

    /**
     *
     */
    public String changeSujetGroupe(String JSONEntry);

    /**
     *
     */
    public String createGroupeSujet(String JSONEntry);

    /**
     *
     */
    public String deleteEU(String JSONEntry);

    /**
     *
     */
    public String deleteSujet(String JSONEntry);

    /**
     *
     */
    public String deleteGroupe(String JSONEntry);

    /**
     *
     */
    public String deleteEleve(String JSONEntry);

    public String getEleve(String JSONEntry);
    public String getGroupe(String JSONEntry);
    public String getSujet(String JSONEntry);
    public String getUE(String JSONEntry);
    public String listEleve();
    public String listGroupe();
    public String listSujet();
    public String listEU();
};