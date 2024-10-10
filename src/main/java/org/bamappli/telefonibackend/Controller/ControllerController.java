package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Controller;
import org.bamappli.telefonibackend.Services.ControllerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "controller")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ControllerController {

    private ControllerService controllerService;

    @PostMapping
    public Controller creer(@RequestBody Controller controller){
        return controllerService.creer(controller);
    }

    @GetMapping
    public List<Controller> liste(){
        return controllerService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Controller> un(@PathVariable Long id){
        return controllerService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Controller modifier(@PathVariable Long id, @RequestBody Controller controller){
        return controllerService.modifer(id,controller);
    }
}
