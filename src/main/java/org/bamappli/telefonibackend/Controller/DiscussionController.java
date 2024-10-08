package org.bamappli.telefonibackend.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telefonibackend.Entity.Brand;
import org.bamappli.telefonibackend.Entity.Discussion;
import org.bamappli.telefonibackend.Services.BrandService;
import org.bamappli.telefonibackend.Services.DiscussionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "discussion")
@AllArgsConstructor
public class DiscussionController {

    private final DiscussionService discussionService;

    @PostMapping
    public Discussion creer(@RequestBody Discussion discussion){
        return discussionService.creer(discussion);
    }

    @GetMapping
    public List<Discussion> liste(){
        return discussionService.recuperer();
    }

    @GetMapping(path = "/{id}")
    public Optional<Discussion> un(@PathVariable Long id){
        return discussionService.trouver(id);
    }

}
