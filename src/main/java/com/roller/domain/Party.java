package com.roller.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 4096)
    private String description;

    @ManyToOne
    private Player master;

    @ManyToMany
    private Set<Player> players;

    @OneToMany(mappedBy = "game")
    private Set<com.roller.domain.Character> characters;

    @OneToMany(mappedBy = "game")
    private Set<Message> posts;

    protected Party() { }

    public Party(String name, Player master) {
        this.name = name;
        this.master = master;
    }

    public Long getId() {
        return id;
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

    public Player getMaster() {
        return master;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<com.roller.domain.Character> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<com.roller.domain.Character> characters) {
        this.characters = characters;
    }
}
