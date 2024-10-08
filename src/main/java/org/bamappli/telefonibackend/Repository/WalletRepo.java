package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet, Long> {

}
