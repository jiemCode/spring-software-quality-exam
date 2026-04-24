package com.riffo.users.controller;

import com.riffo.users.dto.PartenaireRequest;
import com.riffo.users.dto.PartenaireResponse;
import com.riffo.users.entity.Partenaire;
import com.riffo.users.exception.ResourceNotFoundException;
import com.riffo.users.mapper.PartenaireMapper;
import com.riffo.users.service.PartenaireService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des partenaires
 * Base URL: /api/partenaires
 */
@RestController
@RequestMapping("/partenaires")
@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
public class PartenaireRESTController {

    private final PartenaireService partenaireService;
    private final PartenaireMapper partenaireMapper;

    public PartenaireRESTController(PartenaireService partenaireService, PartenaireMapper partenaireMapper) {
        this.partenaireService = partenaireService;
        this.partenaireMapper = partenaireMapper;
    }

    /**
     * Récupère tous les partenaires
     * GET /api/partenaires
     * @return liste de tous les partenaires avec code 200
     */
    @GetMapping
    public ResponseEntity<List<PartenaireResponse>> getAllPartenaires() {
        List<PartenaireResponse> partenaires = partenaireService.getAllPartenaires()
                .stream()
                .map(partenaireMapper::toResponse)
                .toList();
        return ResponseEntity.ok(partenaires);
    }

    /**
     * Récupère un partenaire par son ID
     * GET /api/partenaires/{id}
     * @param id l'ID du partenaire
     * @return le partenaire avec code 200, ou 404 s'il n'existe pas
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartenaireResponse> getPartenaireById(@PathVariable Long id) {
        Partenaire partenaire = partenaireService.getPartenaireById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le partenaire avec l'ID " + id + " n'existe pas"));
        return ResponseEntity.ok(partenaireMapper.toResponse(partenaire));
    }

    /**
     * Récupère un partenaire par son nom
     * GET /api/partenaires/search/nom?nom={nom}
     * @param nom le nom du partenaire
     * @return le partenaire avec code 200, ou 404 s'il n'existe pas
     */
    @GetMapping("/search/nom")
    public ResponseEntity<PartenaireResponse> getPartenaireByNom(@RequestParam @NotBlank String nom) {
        Partenaire partenaire = partenaireService.getPartenaireByNom(nom)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun partenaire trouve pour le nom " + nom));
        return ResponseEntity.ok(partenaireMapper.toResponse(partenaire));
    }

    /**
     * Récupère tous les partenaires d'une categorie
     * GET /api/partenaires/search/categorie?categorie={categorie}
     * @param categorie la categorie
     * @return liste des partenaires de la categorie
     */
    @GetMapping("/search/categorie")
    public ResponseEntity<List<PartenaireResponse>> getPartenairesByCategorie(@RequestParam @NotBlank String categorie) {
        List<PartenaireResponse> partenaires = partenaireService.getPartenairesByCategorie(categorie)
                .stream()
                .map(partenaireMapper::toResponse)
                .toList();
        return ResponseEntity.ok(partenaires);
    }

    /**
     * Récupère tous les partenaires d'un statut
     * GET /api/partenaires/search/statut?statut={statut}
     * @param statut le statut
     * @return liste des partenaires du statut
     */
    @GetMapping("/search/statut")
    public ResponseEntity<List<PartenaireResponse>> getPartenairesByStatut(@RequestParam @NotBlank String statut) {
        List<PartenaireResponse> partenaires = partenaireService.getPartenairesByStatut(statut)
                .stream()
                .map(partenaireMapper::toResponse)
                .toList();
        return ResponseEntity.ok(partenaires);
    }

    /**
     * Récupère tous les partenaires d'une ville
     * GET /api/partenaires/search/ville?ville={ville}
     * @param ville la ville
     * @return liste des partenaires de la ville
     */
    @GetMapping("/search/ville")
    public ResponseEntity<List<PartenaireResponse>> getPartenairesByVille(@RequestParam @NotBlank String ville) {
        List<PartenaireResponse> partenaires = partenaireService.getPartenairesByVille(ville)
                .stream()
                .map(partenaireMapper::toResponse)
                .toList();
        return ResponseEntity.ok(partenaires);
    }

    /**
     * Récupère un partenaire par son email
     * GET /api/partenaires/search/email?email={email}
     * @param email l'email du partenaire
     * @return le partenaire avec code 200, ou 404 s'il n'existe pas
     */
    @GetMapping("/search/email")
    public ResponseEntity<PartenaireResponse> getPartenaireByEmail(@RequestParam @NotBlank @Email String email) {
        Partenaire partenaire = partenaireService.getPartenaireByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun partenaire trouve pour l'email " + email));
        return ResponseEntity.ok(partenaireMapper.toResponse(partenaire));
    }

    /**
     * Ajoute un nouveau partenaire
     * POST /api/partenaires
     * @param partenaire le partenaire à ajouter
     * @return le partenaire enregistré avec code 201
     */
    @PostMapping
    public ResponseEntity<PartenaireResponse> addPartenaire(@Valid @RequestBody PartenaireRequest partenaireRequest) {
        Partenaire nouveauPartenaire = partenaireService.addPartenaire(partenaireMapper.toEntity(partenaireRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(partenaireMapper.toResponse(nouveauPartenaire));
    }

    /**
     * Met à jour un partenaire
     * PUT /api/partenaires/{id}
     * @param id l'ID du partenaire
     * @param partenaire les données mises à jour
     * @return le partenaire mis à jour avec code 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartenaireResponse> updatePartenaire(@PathVariable Long id,
                                                               @Valid @RequestBody PartenaireRequest partenaireRequest) {
        Partenaire partenaireMAJ = partenaireService.updatePartenaire(id, partenaireMapper.toEntity(partenaireRequest));
        return ResponseEntity.ok(partenaireMapper.toResponse(partenaireMAJ));
    }

    /**
     * Supprime un partenaire
     * DELETE /api/partenaires/{id}
     * @param id l'ID du partenaire à supprimer
     * @return code 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartenaire(@PathVariable Long id) {
        partenaireService.deletePartenaire(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Compte le nombre total de partenaires
     * GET /api/partenaires/count
     * @return le nombre de partenaires
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPartenaires() {
        long count = partenaireService.countPartenaires();
        return ResponseEntity.ok(count);
    }

    /**
     * Vérifie si un partenaire existe par son email
     * GET /api/partenaires/exists/email?email={email}
     * @param email l'email du partenaire
     * @return true si le partenaire existe, false sinon
     */
    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam @NotBlank @Email String email) {
        boolean exists = partenaireService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
