package com.roller.viewModels;

import com.roller.domain.Character;

import javax.validation.constraints.NotNull;

public class CharacterFrom {

    private Long id;

    @NotNull
    private String name;

    private Integer passion;

    private Integer bliss;

    private Integer depression;

    private String owner;

    private String game;

    public CharacterFrom() { }

    public CharacterFrom(Character character) {
        id = character.getId();
        name = character.getName();
        passion = character.getPassion();
        bliss = character.getBliss();
        depression = character.getDepression();
        owner = character.getOwner().getUsername();
        game = character.getGame().getName();
    }

    public Integer getPassion() {
        return passion;
    }

    public Integer getBliss() {
        return bliss;
    }

    public Integer getDepression() {
        return depression;
    }

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
