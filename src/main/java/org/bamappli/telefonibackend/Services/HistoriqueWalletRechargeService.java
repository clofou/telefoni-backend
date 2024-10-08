package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.HistoriqueWalletRecharge;
import org.bamappli.telefonibackend.Repository.HistoriqueWalletRechargeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoriqueWalletRechargeService implements CrudService<Long, HistoriqueWalletRecharge>{

    private final HistoriqueWalletRechargeRepo historiqueWalletRechargeRepo;

    @Override
    public HistoriqueWalletRecharge creer(HistoriqueWalletRecharge historiqueWalletRecharge) {
        return historiqueWalletRechargeRepo.save(historiqueWalletRecharge);
    }

    @Override
    public HistoriqueWalletRecharge modifer(Long aLong, HistoriqueWalletRecharge historiqueWalletRecharge) {
        return null;
    }

    @Override
    public Optional<HistoriqueWalletRecharge> trouver(Long id) {
        return historiqueWalletRechargeRepo.findById(id);
    }

    @Override
    public List<HistoriqueWalletRecharge> recuperer() {
        return historiqueWalletRechargeRepo.findAll();
    }

    @Override
    public void supprimer(Long aLong) {

    }
}
