package org.bamappli.telefonibackend.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Admin;
import org.bamappli.telefonibackend.Entity.Role;
import org.bamappli.telefonibackend.Entity.Utilisateur;
import org.bamappli.telefonibackend.Entity.Wallet;
import org.bamappli.telefonibackend.Repository.AdminRepo;
import org.bamappli.telefonibackend.Repository.RoleRepo;
import org.bamappli.telefonibackend.Repository.UtilisateurRepo;
import org.bamappli.telefonibackend.Repository.WalletRepo;
import org.bamappli.telefonibackend.Utils.UtilService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService implements CrudService<Long, Admin> {

    private final AdminRepo adminRepo;
    private final RoleRepo roleRepo;
    private final UtilisateurRepo utilisateurRepo;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;

    @Override
    @Transactional
    public Admin creer(Admin admin) {
        // Verifier si le mail et le mot de passe sont valides
        UtilService.checkEmailAndPassword(admin.getEmail(), admin.getMotDePasse());


        Utilisateur utilisateur = utilisateurRepo.findByEmail(admin.getEmail());
        if (utilisateur != null){
            throw new RuntimeException("L'utilisateur existe deja");
        }

        // Attribution d'un role
        Role role = roleRepo.findByNom("ADMIN");
        if (role == null){
            throw new RuntimeException("Le role n'existe pas");
        }
        admin.setRole(role);

        admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));

        // Creer Un Compte par defaut
        Wallet compte = new Wallet();
        compte.setCodeSecret("000000");
        admin.setCompte(walletService.creer(compte));

        return adminRepo.save(admin);
    }

    @Override
    public Admin modifer(Long id, Admin admin){
        Optional<Admin> admin1 = adminRepo.findById(id);
        if (admin1.isPresent()){
            Admin theAdmin = admin1.get();
            if (admin.getNom() != null) theAdmin.setNom(admin.getNom());
            if (admin.getNumeroDeTelephone() != null) theAdmin.setNumeroDeTelephone(admin.getNumeroDeTelephone());
            return adminRepo.save(theAdmin);
        }
        return null;
    }

    @Override
    public Optional<Admin> trouver(Long id){
        return adminRepo.findById(id);
    }

    @Override
    public List<Admin> recuperer(){
        return adminRepo.findAll();
    }

    @Override
    public void supprimer(Long id){
        Optional<Admin> admin1 = adminRepo.findById(id);
        admin1.ifPresent(adminRepo::delete);
    }
}