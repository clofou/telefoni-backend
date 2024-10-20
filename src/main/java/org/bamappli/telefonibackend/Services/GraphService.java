package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.VenteTypeDTO;
import org.bamappli.telefonibackend.DTO.VolumeServiceDTO;
import org.bamappli.telefonibackend.Enum.CommandeStatut;
import org.bamappli.telefonibackend.Enum.ReparationStatut;
import org.bamappli.telefonibackend.Enum.TelephoneType;
import org.bamappli.telefonibackend.Enum.TransactionStatut;
import org.bamappli.telefonibackend.Repository.AvisRepository;
import org.bamappli.telefonibackend.Repository.CommandeRepo;
import org.bamappli.telefonibackend.Repository.ReparationRepo;
import org.bamappli.telefonibackend.Repository.TransactionRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GraphService {


    private AvisRepository avisRepo;


    private TransactionRepo transactionRepo;


    private ReparationRepo reparationRepo;


    private CommandeRepo commandeRepo;

    // Méthode pour récupérer les données des 5 derniers jours
    public List<VolumeServiceDTO> getVolumeAndServiceDataForLastFiveDays() {
        List<VolumeServiceDTO> result = new ArrayList<>();

        // Obtenir la date du jour et les 4 jours précédents
        for (int i = 0; i < 5; i++) {
            Date startDate = getStartOfDay(i);
            Date endDate = getEndOfDay(i);

            // Calculer le volume (nombre d'avis, transactions, annonces validées)
            Long totalAvis = avisRepo.countAvisBetweenDates(startDate, endDate);
            Long totalTransactions = transactionRepo.countTransactionsByStatusBetweenDates(TransactionStatut.PAYER, startDate, endDate);

            // Calculer les services (nombre de réparations, commandes livrées)
            Long totalReparations = reparationRepo.countReparationsByStatusBetweenDates(ReparationStatut.TERMINER, startDate, endDate);
            Long totalCommandesLivrees = commandeRepo.countCommandesLivreesBetweenDates(CommandeStatut.LIVRER, CommandeStatut.LIVRER, startDate, endDate);

            // Remplir le DTO
            VolumeServiceDTO dto = new VolumeServiceDTO();
            dto.setDate(startDate);
            dto.setVolume(totalAvis + totalTransactions);  // Volume total
            dto.setServices(totalReparations + totalCommandesLivrees);  // Services total

            result.add(dto);
        }

        return result;
    }

    // Méthode pour obtenir le début de la journée pour un jour donné
    private Date getStartOfDay(int daysAgo) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -daysAgo);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // Méthode pour obtenir la fin de la journée pour un jour donné
    private Date getEndOfDay(int daysAgo) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -daysAgo);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public List<VenteTypeDTO> getVentesOccasionVsNeuf() {
        List<Object[]> results = commandeRepo.findMonthlySalesByPhoneType();
        Map<Integer, VenteTypeDTO> ventesMap = new HashMap<>();

        // Initialiser les mois
        for (int i = 1; i <= 12; i++) {
            ventesMap.put(i, new VenteTypeDTO(i, 0, 0)); // i = mois, 0 ventes d'occasion, 0 ventes de neuf
        }

        // Remplir les données
        for (Object[] result : results) {
            int mois = (int) result[0];
            TelephoneType type = (TelephoneType) result[1];
            Long nombreVentes = (Long) result[2];

            VenteTypeDTO dto = ventesMap.get(mois);

            if (type == TelephoneType.OCCASION) {
                dto.setVentesOccasion(nombreVentes);
            } else if (type == TelephoneType.NEUF) {
                dto.setVentesNeuf(nombreVentes);
            }
        }

        return ventesMap.values().stream()
                .sorted(Comparator.comparingInt(VenteTypeDTO::getMois))
                .collect(Collectors.toList());
    }
}

