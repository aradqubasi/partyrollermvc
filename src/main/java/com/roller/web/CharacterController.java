package com.roller.web;

import com.roller.domain.Player;
import com.roller.domain.UserPrincipal;
import com.roller.domain.Character;
import com.roller.service.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.roller.service.CharacterRepository;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(value = "/character/{id}", method = GET)
    public String character(@PathVariable("id") long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentUser = (UserPrincipal)auth.getPrincipal();
//        System.out.print(currentUser.getUser().getUsername());

        Character character = characterRepository.findOne(id);
        model.addAttribute("name", character == null ? "default" : character.getName());
        model.addAttribute("bliss", character == null ? "default" : character.getBliss());
        model.addAttribute("passion", character == null ? "default" : character.getPassion());
        model.addAttribute("depression", character == null ? "default" : character.getDepression());
//        Hibernate.initialize(currentUser.getUser().getUsername());
        Player user = playerRepository.findOne(1L);
        model.addAttribute("owner", "");
        return "character";
    }

}