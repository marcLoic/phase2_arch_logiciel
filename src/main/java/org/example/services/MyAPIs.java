package org.example.services;

import org.example.Groupe.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RestController
@RequestMapping("/api")
public class MyAPIs {

    GroupeImplementation groupeImplementation = new GroupeImplementation();
    @GetMapping("/listgroups")
    public List<Groupe> listGroups() {
        List<Groupe> groupList = new ArrayList<>();
        groupList = Groupe.getAll();
        return groupList;
    }

    @GetMapping("/listeleves")
    public List<Eleve> listEleves() {
        List<Eleve> eleveList = new ArrayList<>();
        eleveList = Eleve.getAll();
        return eleveList;
    }

    @GetMapping("/listsujets")
    public List<Sujet> listSujets() {
        List<Sujet> sujetList = new ArrayList<>();
        sujetList = Sujet.getAll();
        return sujetList;
    }

    @GetMapping("/listue")
    public List<UniteEnseignement> listUE() {
        List<UniteEnseignement> ueList = new ArrayList<>();
        ueList = UniteEnseignement.getAll();
        return ueList;
    }

    @PostMapping("/eleve/addeleve")
    public Eleve addEleve(@RequestBody Eleve newEleve) {
        Random random = new Random();
        int randomId;
        randomId = random.nextInt(100) + 1;

        // Convertissez le nombre en une chaîne
        String id = Integer.toString(randomId);

        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("prenom", newEleve.getPrenom());
        obj.put("nom", newEleve.getNom());
        JSONObject objRet = new JSONObject(groupeImplementation.createEleve(obj.toString()));
        newEleve.setId(id);
        return newEleve;
    }

    @PostMapping("/group/addgroup")
    public Groupe addGroup(@RequestBody Groupe newGroupe) {
        Random random = new Random();
        int randomId;
        randomId = random.nextInt(100) + 1;

        // Convertissez le nombre en une chaîne
        String id = Integer.toString(randomId);

        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("EleveID", newGroupe.getEleveID());
        obj.put("SujetID", newGroupe.getSujetID());
        obj.put("UEID", newGroupe.getUeID());
        JSONObject objRet = new JSONObject(groupeImplementation.createGroupe(obj.toString()));
        newGroupe.setId(id);
        return newGroupe;
    }

    @PostMapping("/sujet/addsujet")
    public Sujet addSujet(@RequestBody Sujet newSujet) {
        Random random = new Random();
        int randomId;
        randomId = random.nextInt(100) + 1;

        // Convertissez le nombre en une chaîne
        String id = Integer.toString(randomId);

        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("Eleve", newSujet.getEleve());
        obj.put("Fin", newSujet.getFin());
        obj.put("Jour", newSujet.getJour());
        obj.put("UE", newSujet.getUniteEnseignement());
        obj.put("Titre", newSujet.getTitre());
        JSONObject objRet = new JSONObject(groupeImplementation.createGroupe(obj.toString()));
        newSujet.setId(id);
        return newSujet;
    }

    @PostMapping("/ue/addue")
    public UniteEnseignement addSujet(@RequestBody UniteEnseignement newUE) {
        Random random = new Random();
        int randomId;
        randomId = random.nextInt(100) + 1;

        // Convertissez le nombre en une chaîne
        String id = Integer.toString(randomId);

        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("prenom", newUE.getEleve());
        obj.put("sujet", newUE.getSujet());
        obj.put("code", newUE.getCode());
        obj.put("cours", newUE.getCours());
        obj.put("intitule", newUE.getIntitule());
        obj.put("TP", newUE.getTp());
        obj.put("Valeur", newUE.getValeur());
        obj.put("TD", newUE.getTd());
        JSONObject objRet = new JSONObject(groupeImplementation.createGroupe(obj.toString()));
        newUE.setId(id);
        return newUE;
    }

    @GetMapping("/eleve/{id}")
    public Eleve getEleve(@PathVariable("id") String id) {
        //JSONObject req = new JSONObject();
        //req.put("id", id);
        //String rep = groupeImplementation.getEleve(req.toString());
        //Eleve elv = Eleve.getById(id);
        return Eleve.getById(id);
    }
}
