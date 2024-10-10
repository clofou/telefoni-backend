package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Tags;
import org.bamappli.telefonibackend.Services.TagsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "tags")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class TagsController {

    private TagsService tagsService;

    @PostMapping
    public Tags creer(@RequestBody Tags tags){
        return tagsService.creer(tags);
    }

    @GetMapping
    public List<Tags> liste(){
        return tagsService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Tags> un(@PathVariable Long id){
        return tagsService.trouver(id);
    }

    @PatchMapping(path = "/{id}")
    public Tags modifier(@PathVariable Long id, @RequestBody Tags tags){
        return tagsService.modifer(id,tags);
    }

    @DeleteMapping(path = "/{id}")
    public void supprimer(@PathVariable Long id){
        tagsService.supprimer(id);
    }

}
