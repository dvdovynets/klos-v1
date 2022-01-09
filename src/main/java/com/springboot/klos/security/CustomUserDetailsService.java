package com.springboot.klos.security;

import com.springboot.klos.dao.ParticipantDao;
import com.springboot.klos.model.Participant;
import com.springboot.klos.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ParticipantDao participantDao;

    public CustomUserDetailsService(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Participant participant = participantDao.findByEmailAndIsDeleted(email, false).orElseThrow(
                () -> new UsernameNotFoundException("Participant not found with email: " + email));
        return new User(participant.getEmail(),
                participant.getPassword(),
                mapRolesToAuthorities(participant.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }
}
