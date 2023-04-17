package it.svil.controller.security.service;

import it.svil.controller.security.model.MyUser;
import it.svil.controller.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findByUsername(username);
        if(user != null){
            return user;
        } else {throw new UsernameNotFoundException("Utente " + username + " non trovato");}
    }

    public Iterable<MyUser> getUsers(){
        return userRepository.findAll();
    }

    public Iterable<MyUser> delete(Long id) {
        userRepository.deleteById(id);
        return this.getUsers();
    }

    public Optional<MyUser> findUser(Long id){
        return userRepository.findById(id);
    }

    public MyUser putUser(Long id, MyUser user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public void register(MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
