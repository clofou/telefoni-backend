package org.bamappli.telefonidashboard.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telefonidashboard.Entity.Admin;
import org.bamappli.telefonidashboard.Entity.Role;
import org.bamappli.telefonidashboard.Entity.Utilisateur;
import org.bamappli.telefonidashboard.Repository.AdminRepo;
import org.bamappli.telefonidashboard.Repository.UtilisateurRepo;
import org.bamappli.telefonidashboard.Utils.UtilService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepo adminRepo;
    private final UtilisateurRepo utilisateurRepo;
    private final PasswordEncoder passwordEncoder;

    public Admin creerAdmin(Admin admin) {
        if (!UtilService.isValidEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Email invalide");
        }
        if (!UtilService.isValidPassword(admin.getMotDePasse())){
            throw new IllegalArgumentException("Password invalide");
        }

        Utilisateur utilisateur = utilisateurRepo.findByEmail(admin.getEmail());
        if (utilisateur != null){
            throw new RuntimeException("L'utilisateur existe déjà");
        }

        Role role = new Role(1L, "ADMIN");


        admin.setRole(role);
        admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));

        return adminRepo.save(admin);
    }

    public Admin modiferAdmin(Long id, Admin admin){
        Optional<Admin> admin1 = adminRepo.findById(id);
        if (admin1.isPresent()){
            Admin theAdmin = admin1.get();
            if (admin.getNom() != null) admin.setNom(admin.getNom());
            if (admin.getTelephone() != null) admin.setTelephone(admin.getTelephone());
            return adminRepo.save(theAdmin);
        }
        return null;
    }

    public Optional<Admin> trouverAdmin(Long id){
        return adminRepo.findById(id);
    }

    public void supprimerAdmin(Long id){
        Optional<Admin> admin1 = adminRepo.findById(id);
        admin1.ifPresent(adminRepo::delete);
    }
}
