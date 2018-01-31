package com.roller.web;

import com.roller.domain.Player;
import com.roller.domain.UserPrincipal;
import com.roller.domain.Character;
import com.roller.service.PlayerRepository;
import com.roller.viewModels.CharacterFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.roller.service.CharacterRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping(value = "/character/{id}")
    public String getCharacter(@PathVariable("id") Long id, Model model) {
        Character character = characterRepository.findOne(id);
        CharacterFrom form = new CharacterFrom(character);
        model.addAttribute("form", form);
        return "character";
    }

    @GetMapping(value = "/characters")
    public String getCharacters(Model model) {
        List<Character> characters = new ArrayList<>();
        characterRepository.findAll().forEach(characters::add);
        model.addAttribute("characters", characters);
        return "characters";
    }

    @GetMapping(value = "/character/delete/{id}")
    public String deleteCharacter(@PathVariable("id") Long id) {
        characterRepository.delete(id);
        return "redirect:/characters";
    }

    @GetMapping(value = "/character/new")
    public String getCreateForm(Model model) {
        CharacterFrom form = new CharacterFrom();
        model.addAttribute("form", form);
        return "characterNew";
    }

    @PostMapping(value = "/character/new")
    public String create(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid CharacterFrom form, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "characterNew";
        }
        else {
            Player player = playerRepository.findByUsername(userPrincipal.getUsername());
            Character character = new Character(form.getName(), form.getPassion(), form.getBliss(), form.getDepression(), player, null);
            characterRepository.save(character);
            return "redirect:/characters";
        }
    }

}