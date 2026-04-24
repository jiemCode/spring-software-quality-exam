package com.riffo.users.service.impl;

import com.riffo.users.entity.Partenaire;
import com.riffo.users.exception.DuplicateResourceException;
import com.riffo.users.exception.InvalidRequestException;
import com.riffo.users.repository.PartenaireRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartenaireServiceImplTest {

    @Mock
    private PartenaireRepository partenaireRepository;

    @InjectMocks
    private PartenaireServiceImpl partenaireService;

    @Test
    void addPartenaireShouldCreatePartnerWhenEmailDoesNotExist() {
        Partenaire partenaire = buildPartenaire();
        Partenaire savedPartenaire = buildPartenaire();
        savedPartenaire.setId(1L);

        when(partenaireRepository.existsByEmail(partenaire.getEmail())).thenReturn(false);
        when(partenaireRepository.save(partenaire)).thenReturn(savedPartenaire);

        Partenaire result = partenaireService.addPartenaire(partenaire);

        assertEquals(1L, result.getId());
        assertEquals("Hopital Central", result.getNom());
        verify(partenaireRepository).existsByEmail(partenaire.getEmail());
        verify(partenaireRepository).save(partenaire);
    }

    @Test
    void getPartenaireByIdShouldReturnPartnerWhenIdExists() {
        Partenaire partenaire = buildPartenaire();
        partenaire.setId(5L);
        when(partenaireRepository.findById(5L)).thenReturn(Optional.of(partenaire));

        Optional<Partenaire> result = partenaireService.getPartenaireById(5L);

        assertTrue(result.isPresent());
        assertSame(partenaire, result.get());
        verify(partenaireRepository).findById(5L);
    }

    @Test
    void addPartenaireShouldThrowWhenEmailAlreadyExists() {
        Partenaire partenaire = buildPartenaire();
        when(partenaireRepository.existsByEmail(partenaire.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> partenaireService.addPartenaire(partenaire));

        verify(partenaireRepository).existsByEmail(partenaire.getEmail());
        verify(partenaireRepository, never()).save(partenaire);
    }

    @Test
    void getPartenaireByIdShouldThrowWhenIdIsInvalid() {
        assertThrows(InvalidRequestException.class, () -> partenaireService.getPartenaireById(0L));

        verify(partenaireRepository, never()).findById(0L);
    }

    @Test
    void addPartenaireShouldThrowWhenPartnerIsNull() {
        assertThrows(InvalidRequestException.class, () -> partenaireService.addPartenaire(null));

        verify(partenaireRepository, never()).existsByEmail(org.mockito.ArgumentMatchers.anyString());
        verify(partenaireRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    private Partenaire buildPartenaire() {
        Partenaire partenaire = new Partenaire();
        partenaire.setNom("Centre National Oncologie de Diamniadio");
        partenaire.setCategorie("Santé");
        partenaire.setAdresse("23 rue Stade Abdoulaye Wade");
        partenaire.setVille("Diamniadio");
        partenaire.setTelephone("771234567");
        partenaire.setEmail("contact@cnod.sn");
        partenaire.setLatitude(14.7306);
        partenaire.setLongitude(-17.1948);
        partenaire.setStatut("ACTIF");
        partenaire.setPlafondPriseEnCharge(150000.0);
        return partenaire;
    }
}
