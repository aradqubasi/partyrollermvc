package com.roller.web;

import com.roller.domain.*;
import com.roller.domain.Character;
import com.roller.service.CharacterRepository;
import com.roller.service.MessageRepository;
import com.roller.service.PartyRepository;
import com.roller.service.PlayerRepository;
import com.roller.viewModels.Board;
import com.roller.viewModels.PartyForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Controller
public class PartyController {

    private static final Logger log = LoggerFactory.getLogger(PartyController.class);

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/parties")
    public String getParties(Model model) {
        log.debug("PartyController.getParties");

        List<Party> parties = new ArrayList<>();
        partyRepository.findAll().forEach(parties::add);
        model.addAttribute("parties", parties);
        return "parties";
    }

    @GetMapping("/party/new")
    public String getPartyNew(@AuthenticationPrincipal UserPrincipal user, Model model) {
        log.debug("PartyController.getPartyNew");

        PartyForm form = new PartyForm();
        form.setMaster(user.getUser().getUsername());
        model.addAttribute("form", form);
        return "partyNew";
    }

    @PostMapping("/party/new")
    public String createNewParty(@AuthenticationPrincipal UserPrincipal user, @Valid PartyForm partyForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "partyNew";
        }
        else {
            Player master = user.getUser();
            Party party = new Party(partyForm.getName(), master);
            party.setDescription(partyForm.getDescription());
            partyRepository.save(party);

            return "redirect:/party/edit/general/" + party.getId();
        }
    }

    @GetMapping("/party/edit/general/{id}")
    public String getPartyEdit(@PathVariable("id") long id, @AuthenticationPrincipal UserPrincipal user, Model model) {
        log.debug("PartyController.getPartyNew");

        Party party = partyRepository.findOne(id);
        PartyForm form = new PartyForm(party);
        model.addAttribute("form", form);

        return "partyEdit";
    }

    @PostMapping("/party/edit/general/{id}")
    public String editParty(@PathVariable("id") long id, @Valid PartyForm partyForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "partyEdit";
        }
        else {
            Party party = partyRepository.findOne(id);
            party.setName(partyForm.getName());
            party.setDescription(partyForm.getDescription());
            Player newPlayer = playerRepository.findByUsername(partyForm.getNewPlayer());
            if (newPlayer != null) {
                party.getPlayers().add(newPlayer);
            }
            Character newCharacter = characterRepository.findByName(partyForm.getNewCharacter());
            if (newCharacter != null) {
                party.getCharacters().add(newCharacter);
            }
            partyRepository.save(party);
            return "redirect:/party/edit/general/" + party.getId();
        }
    }

    @GetMapping("/party/edit/remove/player/{party}/{player}")
    public String removePlayer(@PathVariable("player") Long playerId, @PathVariable("party") Long partyId) {
        Party party = partyRepository.findOne(partyId);
        Player player = playerRepository.findOne(playerId);
        party.getPlayers().remove(player);
        partyRepository.save(party);
        return "redirect:/party/edit/general/" + party.getId();
    }

    @GetMapping("/party/edit/remove/character/{party}/{character}")
    public String removeCharacter(@PathVariable("character") Long characterId, @PathVariable("party") Long partyId) {
        Party party = partyRepository.findOne(partyId);
        Character character = characterRepository.findOne(characterId);
        party.getCharacters().remove(character);
        partyRepository.save(party);
        return "redirect:/party/edit/general/" + party.getId();
    }

    @GetMapping("/board/{id}")
    public String viewParty(@PathVariable("id") Long partyId, @AuthenticationPrincipal UserPrincipal user, Model model) {
        Player player = user.getUser();
        Party party = partyRepository.findOne(partyId);
        Board form = new Board(party);
        form.setPlayerCharacters(characterRepository.findCharactersByGameAndOwner(party, player).stream().map(Character::getName).collect(Collectors.toSet()));
        Map<String, Set<String>> others = new HashMap<>();
        for (Character character:characterRepository.findCharactersByGame(party)) {
            String username = character.getOwner().getUsername();
            if (!username.equals(user.getUser().getUsername())) {
                others.putIfAbsent(username, new HashSet<>());
                others.get(username).add(character.getName());
            }
        }
        form.setOtherCharacters(others);
        List<Message> messages = messageRepository.findMessagesByGame(party);
        form.setMessages(messages.stream().sorted(Comparator.comparing(m -> m.getTimestamp())).map(m -> m.getText()).collect(Collectors.toSet()));
        model.addAttribute("form", form);
        return "board";
    }

    @PostMapping("/board/{id}")
    public String updateBoard(@AuthenticationPrincipal UserPrincipal user, @PathVariable("id") Long partyId, @Valid Board board, BindingResult bindingResult) {
        Player player = user.getUser();
        Party party = partyRepository.findOne(partyId);

        Message message = new Message();
        message.setGame(party);
        message.setTimestamp(new Date());
        message.setAuthor(player);
        message.setText(board.getNewMessage());
        messageRepository.save(message);

        String rollRequest = board.getNewMessage();
        if (rollRequest.matches("^\\d+d\\d+$")) {
            String rollMessage = "";
            int total = 0;
            int count = Integer.parseInt(rollRequest.split("d")[0]);
            int dice = Integer.parseInt(rollRequest.split("d")[1]);
            for (int i = 0; i < count; i++) {
                int current = ThreadLocalRandom.current().nextInt(1, dice + 1);
                rollMessage += current;
                total += current;
                if (i != count - 1) {
                    rollMessage += ", ";
                }
            }
            rollMessage = rollRequest + " " + total + "{" + rollMessage + "}";
            Message roll = new Message();
            roll.setGame(party);
            roll.setTimestamp(new Date());
            roll.setAuthor(player);
            roll.setText(rollMessage);
            messageRepository.save(roll);
        }

        return "redirect:/board/" + party.getId();
    }

}
