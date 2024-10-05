package org.bamappli.telefonidashboard;

import lombok.AllArgsConstructor;
import org.bamappli.telefonidashboard.Entity.Admin;
import org.bamappli.telefonidashboard.Entity.Role;
import org.bamappli.telefonidashboard.Services.AdminService;
import org.bamappli.telefonidashboard.Services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private RoleService roleService;
    private AdminService adminService;

    @Override
    public void run(String... args){
        Role role1 = new Role(null, "ADMIN");
        Role role2 = new Role(null, "CLIENT");
        Role role3 = new Role(null, "BOUTIQUE");
        Role role4 = new Role(null, "REPARATEUR");
        Role role5 = new Role(null, "CONTROLLER");

        roleService.creerRole(role1);
        roleService.creerRole(role2);
        roleService.creerRole(role3);
        roleService.creerRole(role4);
        roleService.creerRole(role5);

        Admin admin = new Admin();
        admin.setEmail("fakoro88@gmail.com");
        admin.setMotDePasse("090909");
        admin.setNom("Fakoro");

        adminService.creerAdmin(admin);
    }
}