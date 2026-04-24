package com.riffo.users.controller;

import com.riffo.users.dto.PartenaireRequest;
import com.riffo.users.entity.Partenaire;
import com.riffo.users.repository.PartenaireRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PartenaireRESTControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartenaireRepository partenaireRepository;

    @BeforeEach
    void setUp() {
        partenaireRepository.deleteAll();
    }

    @Test
    void addPartenaireShouldReturnCreatedAndPersistPartner() throws Exception {
        PartenaireRequest request = buildRequest("contact@clinique.sn");

        mockMvc.perform(post("/partenaires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nom").value("Clinique Horizon"))
                .andExpect(jsonPath("$.email").value("contact@clinique.sn"))
                .andExpect(jsonPath("$.statut").value("ACTIF"));
    }

    @Test
    void getAllPartenairesShouldReturnStoredPartners() throws Exception {
        partenaireRepository.save(buildEntity("contact1@clinique.sn", "Clinique Horizon"));
        partenaireRepository.save(buildEntity("contact2@hopital.sn", "Hopital Central"));

        mockMvc.perform(get("/partenaires"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].nom").isNotEmpty())
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].nom").isNotEmpty());
    }

    @Test
    void deletePartenaireShouldReturnNoContentAndRemovePartner() throws Exception {
        Partenaire partenaire = partenaireRepository.save(buildEntity("delete@clinique.sn", "Centre Medical"));

        mockMvc.perform(delete("/partenaires/{id}", partenaire.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/partenaires"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private PartenaireRequest buildRequest(String email) {
        PartenaireRequest request = new PartenaireRequest();
        request.setNom("Clinique Horizon");
        request.setCategorie("Sante");
        request.setAdresse("15 avenue des Assureurs");
        request.setVille("Dakar");
        request.setTelephone("770001122");
        request.setEmail(email);
        request.setLatitude(14.7167);
        request.setLongitude(-17.4677);
        request.setStatut("ACTIF");
        request.setPlafondPriseEnCharge(200000.0);
        return request;
    }

    private Partenaire buildEntity(String email, String nom) {
        Partenaire partenaire = new Partenaire();
        partenaire.setNom(nom);
        partenaire.setCategorie("Sante");
        partenaire.setAdresse("15 avenue des Assureurs");
        partenaire.setVille("Dakar");
        partenaire.setTelephone("770001122");
        partenaire.setEmail(email);
        partenaire.setLatitude(14.7167);
        partenaire.setLongitude(-17.4677);
        partenaire.setStatut("ACTIF");
        partenaire.setPlafondPriseEnCharge(200000.0);
        return partenaire;
    }

    private String toJson(PartenaireRequest request) {
        return """
                {
                  "nom": "%s",
                  "categorie": "%s",
                  "adresse": "%s",
                  "ville": "%s",
                  "telephone": "%s",
                  "email": "%s",
                  "latitude": %.4f,
                  "longitude": %.4f,
                  "statut": "%s",
                  "plafondPriseEnCharge": %.1f
                }
                """.formatted(
                request.getNom(),
                request.getCategorie(),
                request.getAdresse(),
                request.getVille(),
                request.getTelephone(),
                request.getEmail(),
                request.getLatitude(),
                request.getLongitude(),
                request.getStatut(),
                request.getPlafondPriseEnCharge()
        );
    }
}
