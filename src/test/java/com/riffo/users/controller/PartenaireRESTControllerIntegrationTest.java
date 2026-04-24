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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    void getPartenaireByIdShouldReturnPartnerWhenExists() throws Exception {
        Partenaire partenaire = partenaireRepository.save(buildEntity("id@edu.sn", "Test ID"));

        mockMvc.perform(get("/partenaires/{id}", partenaire.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(partenaire.getId()))
                .andExpect(jsonPath("$.nom").value("Test ID"));
    }

    @Test
    void getPartenaireByIdShouldReturnNotFoundWhenDoesNotExist() throws Exception {
        mockMvc.perform(get("/partenaires/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPartenaireByNomShouldReturnPartnerWhenExists() throws Exception {
        partenaireRepository.save(buildEntity("maguette@edu.sn", "Maguette"));

        mockMvc.perform(get("/partenaires/search/nom").param("nom", "Maguette"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Maguette"));
    }

    @Test
    void getPartenaireByNomShouldReturnNotFoundWhenDoesNotExist() throws Exception {
        mockMvc.perform(get("/partenaires/search/nom").param("nom", "Non Existent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPartenairesByCategorieShouldReturnList() throws Exception {
        partenaireRepository.save(buildEntity("adama@edu.sn", "P1"));
        partenaireRepository.save(buildEntity("awa@edu.sn", "P2"));

        mockMvc.perform(get("/partenaires/search/categorie").param("categorie", "Sante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getPartenairesByStatutShouldReturnList() throws Exception {
        partenaireRepository.save(buildEntity("fatou@edu.sn", "Fatou"));

        mockMvc.perform(get("/partenaires/search/statut").param("statut", "ACTIF"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getPartenairesByVilleShouldReturnList() throws Exception {
        partenaireRepository.save(buildEntity("moussa@edu.sn", "Moussa"));

        mockMvc.perform(get("/partenaires/search/ville").param("ville", "Dakar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getPartenaireByEmailShouldReturnPartnerWhenExists() throws Exception {
        partenaireRepository.save(buildEntity("saly@edu.sn", "Saly"));

        mockMvc.perform(get("/partenaires/search/email").param("email", "saly@edu.sn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("saly@edu.sn"));
    }

    @Test
    void getPartenaireByEmailShouldReturnNotFoundWhenDoesNotExist() throws Exception {
        mockMvc.perform(get("/partenaires/search/email").param("email", "john@edu.sn"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePartenaireShouldUpdateAndReturnPartner() throws Exception {
        Partenaire partenaire = partenaireRepository.save(buildEntity("mcdonald@edu.sn", "McDonald"));
        PartenaireRequest request = buildRequest("abc@edu.sn");
        request.setNom("ABC");

        mockMvc.perform(put("/partenaires/{id}", partenaire.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("ABC"))
                .andExpect(jsonPath("$.email").value("abc@edu.sn"));
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

    @Test
    void countPartenairesShouldReturnTotalCount() throws Exception {
        partenaireRepository.save(buildEntity("maguette1@edu.sn", "Maguette 1"));
        partenaireRepository.save(buildEntity("maguette2@edu.sn", "Maguette 2"));

        mockMvc.perform(get("/partenaires/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    void existsByEmailShouldReturnTrueWhenExists() throws Exception {
        partenaireRepository.save(buildEntity("exists@edu.sn", "P1"));

        mockMvc.perform(get("/partenaires/exists/email").param("email", "exists@edu.sn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void existsByEmailShouldReturnFalseWhenDoesNotExist() throws Exception {
        mockMvc.perform(get("/partenaires/exists/email").param("email", "notfound@edu.sn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
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
