package com.example.racunapp2.Config;

import com.example.racunapp2.Client.Client;
import com.example.racunapp2.Client.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;

    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client user = clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

       /* Set<GrantedAuthority> authorities = user.getRacuni().stream()
                .map(role -> new SimpleGrantedAuthority(role.getKupljeniProizvodi()))
                .collect(Collectors.toSet()); */

        return new LoggedInUser(user.getId(), user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
