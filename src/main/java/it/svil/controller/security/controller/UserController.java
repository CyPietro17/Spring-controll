package it.svil.controller.security.controller;

import it.svil.controller.security.model.MyUser;
import it.svil.controller.security.model.Role;
import it.svil.controller.security.repository.RoleRepository;
import it.svil.controller.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
//    TODO controlla questa sezione!!!
//    private final MyUser firstUser;
//    private Role adminRole = new Role("ROLE_ADMIN");
//    private Role userRole = new Role("ROLE_USER");
//    public MyUser userBuilder(){
//        roleRepository.saveAll(Arrays.asList(adminRole, userRole));
//        MyUser admin = new MyUser();
//        admin.setUsername("admin");
//        admin.setPassword("4ume1a");
//        admin.setRoles(Arrays.asList(adminRole));
//        admin.setEmail("admin@email.com");
//        userService.register(admin);
//        adminRole.setUsers(Arrays.asList(admin));
//
//        return admin;
//    }

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
//        this.userService.register(this.firstUser);
        this.roleRepository = roleRepository;
//        this.firstUser = userBuilder();
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping("/login")
    public String login(){
        return "new-login";
    }

    @GetMapping("/register")
    public String register(Model model, MyUser user){
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/register")
    public String doRegister(MyUser user){
        Role role = roleRepository.getReferenceById("ROLE_USER");
        user.setRoles(Arrays.asList(role));
        if(role.getUsers() == null)
            role.setUsers(Arrays.asList(user));
        else role.getUsers().add(user);
        userService.register(user);
        return "redirect:/new-login";
    }
}
