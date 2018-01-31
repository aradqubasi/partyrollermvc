package com.roller.viewModels;

import com.roller.domain.Character;
import com.roller.domain.Party;
import com.roller.domain.Player;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PartyForm {

    private long id;

    @NotNull
    @Size(min = 1)
    private String name;

    private String description;

    private Map<Long,String> players = new HashMap<>();

    private Map<Long,String> characters = new HashMap<>();

    private String master;

    private String newPlayer;

    private String newCharacter;

    public PartyForm() {}

    public PartyForm(Party party) {
        id = party.getId();
        name = party.getName();
        description = party.getDescription();
        master = party.getMaster().getUsername();
        players = party.getPlayers().stream().collect(Collectors.toMap(Player::getId, Player::getUsername));
        characters = party.getCharacters().stream().collect(Collectors.toMap(Character::getId, Character::getName));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Long, String> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Long, String> players) {
        this.players = players;
    }

    public Map<Long, String> getCharacters() {
        return characters;
    }

    public void setCharacters(Map<Long, String> characters) {
        this.characters = characters;
    }

    public String getNewPlayer() {
        return newPlayer;
    }

    public void setNewPlayer(String newPlayer) {
        this.newPlayer = newPlayer;
    }

    public String getNewCharacter() {
        return newCharacter;
    }

    public void setNewCharacter(String newCharacter) {
        this.newCharacter = newCharacter;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String owner) {
        this.master = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
