package org.bamappli.telefonibackend.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Client;
import org.bamappli.telefonibackend.Entity.Transaction;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Repository.TransactionRepo;
import org.bamappli.telefonibackend.Utils.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TransactionService implements CrudService<Long, Transaction>{

    private final UserService userService;
    private final TransactionRepo transactionRepo;

    @Override
    public Transaction creer(Transaction transaction) {
        Utilisateur utilisateur = userService.getCurrentUser();
        transaction.setAcheteur((Client) utilisateur);
        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction modifer(Long id, Transaction transaction) {
        Utilisateur user = userService.getCurrentUser();
        Optional<Transaction> transaction1 = transactionRepo.findById(id);

        if (transaction1.isPresent()){
            Transaction transactionExist = transaction1.get();
            if (transaction.getStatut() != null) transactionExist.setStatut(transaction.getStatut());
            return transactionRepo.save(transactionExist);
        }else{
            throw new IllegalArgumentException("La Transaction n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Transaction> trouver(Long id) {
        return transactionRepo.findById(id);
    }

    @Override
    public List<Transaction> recuperer() {
        return transactionRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        transactionRepo.deleteById(id);
    }
}
