package com.roller.domain;

import javax.persistence.*;

@Entity
public class Character {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer passion;
    private Integer bliss;
    private Integer depression;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player owner;

    @ManyToOne
    private Party game;

    protected Character() {}

    public Character(String name, Integer passion, Integer bliss, Integer depression, Player owner, Party game) {

        this.name = name;
        this.passion = passion;
        this.bliss = bliss;
        this.depression = depression;
        this.owner = owner;
        this.game = game;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, name='%s', passion='%d', bliss='%d', depression='%d']",
                id, name, passion, bliss, depression);
    }

    public Integer getDepression() {
        return depression;
    }

    public void setDepression(Integer depression) {
        this.depression = depression;
    }

    public Integer getBliss() {
        return bliss;
    }

    public void setBliss(Integer bliss) {
        this.bliss = bliss;
    }

    public Integer getPassion() {
        return passion;
    }

    public void setPassion(Integer passion) {
        this.passion = passion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Party getGame() {
        return game;
    }

    public void setGame(Party game) {
        this.game = game;
    }
}
