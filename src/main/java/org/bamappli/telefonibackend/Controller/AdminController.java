package org.bamappli.telefonibackend.Controller;

import org.bamappli.telefonibackend.Entity.Admin;
import org.bamappli.telefonibackend.Services.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(@RequestBody AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public Admin creer(Admin admin){
        return adminService.creer(admin);
    }

    @GetMapping
    public List<Admin> tout(){
        return adminService.recuperer();
    }
}
