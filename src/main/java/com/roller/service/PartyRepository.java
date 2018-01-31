package com.roller.service;

import com.roller.domain.Party;
import com.roller.domain.Player;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PartyRepository extends PagingAndSortingRepository<Party, Long> {
}
