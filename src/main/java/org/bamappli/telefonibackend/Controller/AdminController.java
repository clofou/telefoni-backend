package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bamappli.telefonibackend.Entity.Admin;
import org.bamappli.telefonibackend.Services.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "admin")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AdminController {

    private AdminService adminService;

    @PostMapping
    public Admin creer(Admin admin){
        return adminService.creer(admin);
    }

    @GetMapping
    public List<Admin> tout(){
        return adminService.recuperer();
    }
}
