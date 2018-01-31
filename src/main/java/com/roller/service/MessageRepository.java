package com.roller.service;

import com.roller.domain.Message;
import com.roller.domain.Party;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findMessagesByGame(Party game);

}
