package com.riffo.users.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class PartenaireRequest {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas depasser 100 caracteres")
    private String nom;

    @NotBlank(message = "La categorie est obligatoire")
    @Size(max = 50, message = "La categorie ne doit pas depasser 50 caracteres")
    private String categorie;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 255, message = "L'adresse ne doit pas depasser 255 caracteres")
    private String adresse;

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 50, message = "La ville ne doit pas depasser 50 caracteres")
    private String ville;

    @NotBlank(message = "Le telephone est obligatoire")
    @Size(max = 20, message = "Le telephone ne doit pas depasser 20 caracteres")
    private String telephone;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit etre valide")
    @Size(max = 100, message = "L'email ne doit pas depasser 100 caracteres")
    private String email;

    @NotNull(message = "La latitude est obligatoire")
    @DecimalMin(value = "-90.0", message = "La latitude doit etre >= -90")
    @DecimalMax(value = "90.0", message = "La latitude doit etre <= 90")
    private Double latitude;

    @NotNull(message = "La longitude est obligatoire")
    @DecimalMin(value = "-180.0", message = "La longitude doit etre >= -180")
    @DecimalMax(value = "180.0", message = "La longitude doit etre <= 180")
    private Double longitude;

    @NotBlank(message = "Le statut est obligatoire")
    @Size(max = 20, message = "Le statut ne doit pas depasser 20 caracteres")
    private String statut;

    @NotNull(message = "Le plafond de prise en charge est obligatoire")
    @PositiveOrZero(message = "Le plafond de prise en charge doit etre positif ou nul")
    private Double plafondPriseEnCharge;

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
}
