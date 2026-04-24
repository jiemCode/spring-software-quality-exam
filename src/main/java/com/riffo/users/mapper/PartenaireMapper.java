package com.riffo.users.mapper;

import com.riffo.users.dto.PartenaireRequest;
import com.riffo.users.dto.PartenaireResponse;
import com.riffo.users.entity.Partenaire;
import org.springframework.stereotype.Component;

@Component
public class PartenaireMapper {

    public Partenaire toEntity(PartenaireRequest request) {
        Partenaire partenaire = new Partenaire();
        partenaire.setNom(request.getNom());
        partenaire.setCategorie(request.getCategorie());
        partenaire.setAdresse(request.getAdresse());
        partenaire.setVille(request.getVille());
        partenaire.setTelephone(request.getTelephone());
        partenaire.setEmail(request.getEmail());
        partenaire.setLatitude(request.getLatitude());
        partenaire.setLongitude(request.getLongitude());
        partenaire.setStatut(request.getStatut());
        partenaire.setPlafondPriseEnCharge(request.getPlafondPriseEnCharge());
        return partenaire;
    }

    public PartenaireResponse toResponse(Partenaire partenaire) {
        PartenaireResponse response = new PartenaireResponse();
        response.setId(partenaire.getId());
        response.setNom(partenaire.getNom());
        response.setCategorie(partenaire.getCategorie());
        response.setAdresse(partenaire.getAdresse());
        response.setVille(partenaire.getVille());
        response.setTelephone(partenaire.getTelephone());
        response.setEmail(partenaire.getEmail());
        response.setLatitude(partenaire.getLatitude());
        response.setLongitude(partenaire.getLongitude());
        response.setStatut(partenaire.getStatut());
        response.setPlafondPriseEnCharge(partenaire.getPlafondPriseEnCharge());
        return response;
    }
}
