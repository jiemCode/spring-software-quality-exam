package com.riffo.users.entity;

import jakarta.persistence.*;

/**
 * Entité représentant un partenaire
 */
@Entity
@Table(name = "partenaires")
public class Partenaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 50)
    private String categorie;

    @Column(nullable = false, length = 255)
    private String adresse;

    @Column(nullable = false, length = 50)
    private String ville;

    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false, length = 20)
    private String statut;

    @Column(nullable = false)
    private Double plafondPriseEnCharge;

    // Constructeurs
    public Partenaire() {
    }

    public Partenaire(String nom, String categorie, String adresse, String ville, 
                      String telephone, String email, Double latitude, Double longitude, 
                      String statut, Double plafondPriseEnCharge) {
        this.nom = nom;
        this.categorie = categorie;
        this.adresse = adresse;
        this.ville = ville;
        this.telephone = telephone;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.statut = statut;
        this.plafondPriseEnCharge = plafondPriseEnCharge;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Double getPlafondPriseEnCharge() {
        return plafondPriseEnCharge;
    }

    public void setPlafondPriseEnCharge(Double plafondPriseEnCharge) {
        this.plafondPriseEnCharge = plafondPriseEnCharge;
    }

    @Override
    public String toString() {
        return "Partenaire{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", categorie='" + categorie + '\'' +
                ", adresse='" + adresse + '\'' +
                ", ville='" + ville + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", statut='" + statut + '\'' +
                ", plafondPriseEnCharge=" + plafondPriseEnCharge +
                '}';
    }
}
