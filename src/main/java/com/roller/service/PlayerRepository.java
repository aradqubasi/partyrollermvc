package com.roller.service;

import com.roller.domain.Player;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {
    Player findByUsername(String username);
}