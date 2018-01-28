package com.roller.viewModels;

import com.roller.domain.Player;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;

public class PlayerForm {

    private long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "invalid email")
    private String email;

    @NotNull
    @Size(min = 1)
    private String username;

    @NotNull
    @Pattern(regexp = "^.*(?=.{6,})(?=.*[a-zA-Z])[a-zA-Z0-9]+$", message = "must be at least six characters; must contain only letters or numbers; must contain at least one letter")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void Copy(Player player) {
        id = player.getId();
        email = player.getEmail();
        username = player.getUsername();
        password = player.getPassword();
    }

//    private ArrayList<String> roles = new ArrayList<>();

}
