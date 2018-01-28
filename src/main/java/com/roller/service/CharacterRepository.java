package com.roller.service;

import com.roller.domain.Character;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterRepository extends CrudRepository<Character, Long> {

    List<Character> findByName(String lastName);

}