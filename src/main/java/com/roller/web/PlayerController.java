package com.roller.web;

import com.roller.domain.Player;
import com.roller.service.PlayerRepository;
import com.roller.viewModels.PlayerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/players")
    public String players(Model model) {
        List<Player> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        model.addAttribute("players", players);
        return "players";
    }

//    @GetMapping("/player/view/{id}")
//    public String player(@PathVariable("id") long id, Model model) {
//        String username = playerRepository.findOne(id).getUsername();
//        model.addAttribute("username", username);
//        return "player";
//    }

    @GetMapping("/player/new")
    public String player(PlayerForm playerForm) {
        return "playerNew";
    }

    @GetMapping("/player/{action}/{id}")
    public String player(@PathVariable("id") long id, @PathVariable("action") String action, PlayerForm playerForm) {
        if ("edit".equals(action)) {
            playerForm.Copy(playerRepository.findOne(id));
            return "playerEdit";
        }
        else if ("delete".equals(action)) {
            playerRepository.delete(id);
            return "redirect:/players";
        }
        else if ("view".equals(action)) {
            playerForm.setUsername(playerRepository.findOne(id).getUsername());
            return "player";
        }
        throw new NotImplementedException();
    }

    @PostMapping("/player/new")
    public String player(@Valid PlayerForm playerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "playerNew";
        }
        else {
            Player player = new Player(playerForm.getEmail(), playerForm.getUsername(), playerForm.getPassword());
            playerRepository.save(player);
            return "redirect:/players";
        }
    }

    @PostMapping("/player/edit/{id}")
    public String player(@PathVariable("id") long id, @Valid PlayerForm playerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "playerEdit";
        }
        else {
            Player player = playerRepository.findOne(id);
            player.setEmail(playerForm.getEmail());
            player.setUsername(playerForm.getUsername());
            player.setPassword(playerForm.getPassword());
            playerRepository.save(player);
            return "redirect:/players";
        }
    }

//    @DeleteMapping("/player/delete/{id}")
//    public String player(@PathVariable("id") long id) {
//        playerRepository.delete(id);
//        return "redirect:/players";
//    }

}
