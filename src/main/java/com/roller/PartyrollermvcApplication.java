package com.roller;

import com.roller.domain.Character;
import com.roller.domain.Message;
import com.roller.domain.Party;
import com.roller.domain.Player;
import com.roller.service.MessageRepository;
import com.roller.service.PartyRepository;
import com.roller.service.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.roller.service.CharacterRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class PartyrollermvcApplication {

	private static final Logger log = LoggerFactory.getLogger(PartyrollermvcApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PartyrollermvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(PlayerRepository playerRepository, CharacterRepository characterRepository, PartyRepository partyRepository, MessageRepository messageRepository) {
		return (args) -> {
			Player admin = new Player("admin@partyroller.com","admin","p11111");
			admin.getRoles().add("ADMIN");
			playerRepository.save(admin);
			Player user = new Player("user@partyroller.com", "user", "p11111");
			playerRepository.save(user);
			Party party = new Party("Stoics", admin);
			partyRepository.save(party);
			Character plato = new Character("Plato", 1, 1, 1, user, party);
			characterRepository.save(plato);
			Character aristotle = new Character("Aristotle", 1, 1, 1, admin, party);
			characterRepository.save(aristotle);

			Message hello = new Message();
			hello.setText("hello");
			hello.setAuthor(admin);
			hello.setTimestamp(new Date());
			hello.setGame(party);
			messageRepository.save(hello);
			Message hi = new Message();
			hi.setText("hi");
			hi.setAuthor(admin);
			hi.setTimestamp(new Date());
			hi.setGame(party);
			messageRepository.save(hi);
		};
	}
}
