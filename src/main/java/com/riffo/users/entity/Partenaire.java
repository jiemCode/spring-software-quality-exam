package com.riffo.users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

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
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas depasser 100 caracteres")
    private String nom;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "La categorie est obligatoire")
    @Size(max = 50, message = "La categorie ne doit pas depasser 50 caracteres")
    private String categorie;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 255, message = "L'adresse ne doit pas depasser 255 caracteres")
    private String adresse;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 50, message = "La ville ne doit pas depasser 50 caracteres")
    private String ville;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Le telephone est obligatoire")
    @Size(max = 20, message = "Le telephone ne doit pas depasser 20 caracteres")
    private String telephone;

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit etre valide")
    @Size(max = 100, message = "L'email ne doit pas depasser 100 caracteres")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "La latitude est obligatoire")
    @DecimalMin(value = "-90.0", message = "La latitude doit etre >= -90")
    @DecimalMax(value = "90.0", message = "La latitude doit etre <= 90")
    private Double latitude;

    @Column(nullable = false)
    @NotNull(message = "La longitude est obligatoire")
    @DecimalMin(value = "-180.0", message = "La longitude doit etre >= -180")
    @DecimalMax(value = "180.0", message = "La longitude doit etre <= 180")
    private Double longitude;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Le statut est obligatoire")
    @Size(max = 20, message = "Le statut ne doit pas depasser 20 caracteres")
    private String statut;

    @Column(nullable = false)
    @NotNull(message = "Le plafond de prise en charge est obligatoire")
    @PositiveOrZero(message = "Le plafond de prise en charge doit etre positif ou nul")
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
