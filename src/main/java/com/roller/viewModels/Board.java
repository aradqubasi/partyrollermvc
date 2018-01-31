package com.roller.viewModels;

import com.roller.domain.Party;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    private Long id;

    private String name;

    private String description;

    private Map<String,Set<String>> otherCharacters = new HashMap<>();

    private Set<String> playerCharacters = new HashSet<>();

    private String master;

    private String newMessage;

    private Set<String> messages = new HashSet<>();

    public Board() {}

    public Board(Party party) {
        id = party.getId();
        name = party.getName();
        description = party.getDescription();
        master = party.getMaster().getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Set<String>> getOtherCharacters() {
        return otherCharacters;
    }

    public void setOtherCharacters(Map<String, Set<String>> otherCharacters) {
        this.otherCharacters = otherCharacters;
    }

    public Set<String> getPlayerCharacters() {
        return playerCharacters;
    }

    public void setPlayerCharacters(Set<String> playerCharacters) {
        this.playerCharacters = playerCharacters;
    }

    public String getMaster() {
        return master;
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public Set<String> getMessages() {
        return messages;
    }

    public void setMessages(Set<String> messages) {
        this.messages = messages;
    }
}
