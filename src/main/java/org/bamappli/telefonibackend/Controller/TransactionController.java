package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.DTO.PayerTransactionDTO;
import org.bamappli.telefonibackend.Entity.Transaction;
import org.bamappli.telefonibackend.Services.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "transaction")
@CrossOrigin("http://localhost:4200")
public class TransactionController {

    private TransactionService transactionService;

    @PostMapping
    public Transaction creer(@RequestBody Transaction transaction){
        return transactionService.creer(transaction);
    }

    @GetMapping
    public List<Transaction> liste(){
        return transactionService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Transaction> un(@PathVariable Long id){
        return transactionService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Transaction modifier(@PathVariable Long id, @RequestBody Transaction transaction){
        return transactionService.modifer(id,transaction);
    }

    @PatchMapping(path = "/payer/{id}")
    public Transaction modifier1(@PathVariable Long id, @RequestBody String codeSecret){
        return transactionService.payer(id,codeSecret );
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        transactionService.supprimer(id);
    }
}
