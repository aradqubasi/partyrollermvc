package com.roller.service;

import com.roller.domain.Player;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {
    Player findByUsername(String username);
}