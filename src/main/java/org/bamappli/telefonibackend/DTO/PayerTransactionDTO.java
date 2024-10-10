package org.bamappli.telefonibackend.DTO;

import lombok.Data;
import org.bamappli.telefonibackend.Entity.Transaction;

@Data
public class PayerTransactionDTO {
    private String codeSecret;
    private Transaction transaction;
}
