package com.roller.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @Email
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    private ArrayList<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<com.roller.domain.Character> characters = new HashSet<>();

    @ManyToMany
    private Set<Party> games = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<Message> posts;

    protected Player() {

    }

    public Player(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles.add("USER");
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Player setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Player setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Player setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<String> getRoles() {
        return new ArrayList<>(roles);
    }

    public Player setRoles(List<String> roles) {
        this.roles = new ArrayList<>(roles);
        return this;
    }

    public Set<com.roller.domain.Character> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<com.roller.domain.Character> characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, name='%s']",
                id, username);
    }
}
