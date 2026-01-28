package com.pigierbackend.encaissement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardEncaissementBIService {

    private final EncaissementBIRepository repository;

    public DashboardEncaissementBIDTO getDashboard(String annee, Integer mois, String niveau) {

        List<Object[]> rows = repository.getBaseBI(annee, mois, niveau);

        List<EncaissementEleveBIBaseDTO> base = rows.stream()
                .map(r -> EncaissementEleveBIBaseDTO.builder()
                        .matricule((String) r[0])
                        .nomPrenom((String) r[1])
                        .niveau((String) r[2])
                        .filiere((String) r[3])
                        .montantAttendu(((Number) r[4]).intValue())
                        .montantEncaisse(((Number) r[5]).intValue())
                        .solde(((Number) r[6]).intValue())
                        .build())
                .toList();

        int totalAttendu = base.stream().mapToInt(EncaissementEleveBIBaseDTO::getMontantAttendu).sum();
        int totalEncaisse = base.stream().mapToInt(EncaissementEleveBIBaseDTO::getMontantEncaisse).sum();

        long soldes = base.stream().filter(e -> e.getSolde() == 0).count();
        long nonSoldes = base.size() - soldes;

        int montantDuEleves = base.stream()
                .filter(e -> e.getSolde() < 0)
                .mapToInt(e -> Math.abs(e.getSolde()))
                .sum();

        Map<String, Integer> parNiveau = base.stream()
                .collect(Collectors.groupingBy(
                        EncaissementEleveBIBaseDTO::getNiveau,
                        Collectors.summingInt(EncaissementEleveBIBaseDTO::getMontantEncaisse)
                ));

        Map<String, Integer> parFiliere = base.stream()
                .collect(Collectors.groupingBy(
                        EncaissementEleveBIBaseDTO::getFiliere,
                        Collectors.summingInt(EncaissementEleveBIBaseDTO::getMontantEncaisse)
                ));

        double tauxRecouvrement = totalAttendu == 0 ? 0 : (double) totalEncaisse / totalAttendu * 100;
        double tauxSoldes = base.isEmpty() ? 0 : (double) soldes / base.size() * 100;

        return DashboardEncaissementBIDTO.builder()
                .totalEleves((long) base.size())
                .totalElevesSoldes(soldes)
                .totalElevesNonSoldes(nonSoldes)
                .montantTotalAttendu(totalAttendu)
                .montantTotalEncaisse(totalEncaisse)
                .montantTotalRestant(totalAttendu - totalEncaisse)
                .montantDuAuxEleves(montantDuEleves)
                .tauxRecouvrement(tauxRecouvrement)
                .tauxElevesSoldes(tauxSoldes)
                .encaissementParNiveau(parNiveau)
                .encaissementParFiliere(parFiliere)
                .details(base)
                .build();
    }
}

