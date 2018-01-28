package com.roller.service;
import com.roller.domain.Player;
import com.roller.domain.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InternalUserDetailService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @Autowired
    public InternalUserDetailService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        Player user = playerRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown User");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNotExpired = true;
        boolean accountNonLocked = true;
        UserPrincipal principal = new UserPrincipal(
                user,
                enabled, accountNonExpired, credentialsNotExpired, accountNonLocked,
                getAuthorities(user.getRoles())
        );
        return principal;
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r))
                .collect(Collectors.toList());
    }
}
