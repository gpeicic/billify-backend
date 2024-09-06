package com.example.racunapp2.Service;

import com.example.racunapp2.Model.Kupac;
import com.example.racunapp2.Model.LoggedInUser;
import com.example.racunapp2.Repository.KupacRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final KupacRepository kupacRepository;

    public CustomUserDetailsService(KupacRepository kupacRepository) {
        this.kupacRepository = kupacRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Kupac user = kupacRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

       /* Set<GrantedAuthority> authorities = user.getRacuni().stream()
                .map(role -> new SimpleGrantedAuthority(role.getKupljeniProizvodi()))
                .collect(Collectors.toSet()); */

        return new LoggedInUser(user.getId(), user.getEmail(), user.getLozinka(), new ArrayList<>());
    }
}
