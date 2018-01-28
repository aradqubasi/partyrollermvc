package com.roller;

import com.roller.domain.Character;
import com.roller.domain.Player;
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

@SpringBootApplication
public class PartyrollermvcApplication {

	private static final Logger log = LoggerFactory.getLogger(PartyrollermvcApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PartyrollermvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(PlayerRepository playerRepository) {
		return (args) -> {
			Player admin = new Player("admin@partyroller.com","admin","p11111");
			admin.getRoles().add("ADMIN");
			playerRepository.save(admin);
			Player user = new Player("user@partyroller.com", "user", "p11111");
			playerRepository.save(user);
		};
	}
}
