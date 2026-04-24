package com.riffo.users.service.impl;

import com.riffo.users.entity.Partenaire;
import com.riffo.users.exception.DuplicateResourceException;
import com.riffo.users.exception.InvalidRequestException;
import com.riffo.users.exception.ResourceNotFoundException;
import com.riffo.users.repository.PartenaireRepository;
import com.riffo.users.service.PartenaireService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des partenaires
 */
@Service
@Transactional
public class PartenaireServiceImpl implements PartenaireService {

    private final PartenaireRepository partenaireRepository;

    public PartenaireServiceImpl(PartenaireRepository partenaireRepository) {
        this.partenaireRepository = partenaireRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partenaire> getAllPartenaires() {
        return partenaireRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Partenaire> getPartenaireById(Long id) {
        validateId(id);
        return partenaireRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Partenaire> getPartenaireByNom(String nom) {
        return partenaireRepository.findByNom(nom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partenaire> getPartenairesByCategorie(String categorie) {
        return partenaireRepository.findByCategorie(categorie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partenaire> getPartenairesByStatut(String statut) {
        return partenaireRepository.findByStatut(statut);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partenaire> getPartenairesByVille(String ville) {
        return partenaireRepository.findByVille(ville);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Partenaire> getPartenaireByEmail(String email) {
        return partenaireRepository.findByEmail(email);
    }

    @Override
    public Partenaire addPartenaire(Partenaire partenaire) {
        if (partenaire == null) {
            throw new InvalidRequestException("Le partenaire ne peut pas etre null");
        }

        if (partenaireRepository.existsByEmail(partenaire.getEmail())) {
            throw new DuplicateResourceException("Un partenaire avec cet email existe deja");
        }

        return partenaireRepository.save(partenaire);
    }

    @Override
    public Partenaire updatePartenaire(Long id, Partenaire partenaire) {
        validateId(id);

        if (partenaire == null) {
            throw new InvalidRequestException("Le partenaire ne peut pas etre null");
        }

        Partenaire existing = partenaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le partenaire avec l'ID " + id + " n'existe pas"));

        if (partenaireRepository.existsByEmailAndIdNot(partenaire.getEmail(), id)) {
            throw new DuplicateResourceException("Un partenaire avec cet email existe deja");
        }

        existing.setNom(partenaire.getNom());
        existing.setCategorie(partenaire.getCategorie());
        existing.setAdresse(partenaire.getAdresse());
        existing.setVille(partenaire.getVille());
        existing.setTelephone(partenaire.getTelephone());
        existing.setEmail(partenaire.getEmail());
        existing.setLatitude(partenaire.getLatitude());
        existing.setLongitude(partenaire.getLongitude());
        existing.setStatut(partenaire.getStatut());
        existing.setPlafondPriseEnCharge(partenaire.getPlafondPriseEnCharge());

        return partenaireRepository.save(existing);
    }

    @Override
    public void deletePartenaire(Long id) {
        validateId(id);

        if (!partenaireRepository.existsById(id)) {
            throw new ResourceNotFoundException("Le partenaire avec l'ID " + id + " n'existe pas");
        }

        partenaireRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countPartenaires() {
        return partenaireRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidRequestException("L'email est obligatoire");
        }

        return partenaireRepository.existsByEmail(email);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("L'ID du partenaire est invalide");
        }
    }
}
