//package com.techupdating.techupdating.configurations;
//
//import com.techupdating.techupdating.models.User;
//import com.techupdating.techupdating.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final Logger logger = Logger.getLogger(this.getClass().getName());
//    private UserRepository userRepository;
//
//    @Autowired
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//
//        logger.info("This is my log:  " + (username == ""));
//
//        User user = userRepository.findByAccount(username).orElseThrow(
//                () -> new RuntimeException("User does not exist")
//        );
//
//        Collection<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
//                .collect(Collectors.toList());
//
//
//        if(user != null) {
//            org.springframework.security.core.userdetails.User authUser = new org.springframework.security.core.userdetails.User(
//                    user.getAccount(),
//                    user.getPassword(),
//                    authorities
//            );
//
//            System.out.println(user.getAccount());
//            System.out.println(user.getPassword());
//            System.out.println( user.getRoles().stream().map((role) ->
//                    new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase())).collect(Collectors.toList()));
//            return authUser;
//        } else {
//            throw new UsernameNotFoundException("Invalid user or password");
//        }
//    }
//}
