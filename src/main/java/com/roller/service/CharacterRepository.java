package com.roller.service;

import com.roller.domain.Character;
import com.roller.domain.Party;
import com.roller.domain.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterRepository extends CrudRepository<Character, Long> {

    Character findByName(String name);

    List<Character> findCharactersByGame(Party game);

    List<Character> findCharactersByGameAndOwner(Party game, Player owner);
}