package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyClass {

    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        // Logique pour récupérer des données
        return ResponseEntity.ok("Données de l'API");
    }

    @PostMapping("/save")
    public String saveData(@RequestBody String data) {
        // Logique pour enregistrer des données
        return "Données enregistrées : " + data;
    }
}
