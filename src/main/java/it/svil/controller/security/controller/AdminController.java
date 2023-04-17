package it.svil.controller.security.controller;

import it.svil.controller.security.model.MyUser;
import it.svil.controller.security.repository.RoleRepository;
import it.svil.controller.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String AllUsers(Model model, Authentication authentication) {
        model.addAttribute("users", userService.getUsers());
        if(authentication != null)
            model.addAttribute("user", (MyUser) authentication.getPrincipal());
        return "users";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        userService.findUser(id).orElseThrow(
                () -> new IllegalArgumentException("Lo User " + id + " non esiste")
        );
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/getAdmin/{id}")
    public String putStudent(@PathVariable Long id) {
        MyUser user = userService.findUser(id).orElseThrow(
                () -> new IllegalArgumentException("Lo User " + id + " non esiste")
        );
        user.getRoles().add(roleRepository.findById("ROLE_ADMIN").orElse(null));
        userService.putUser(id, user);
        return "redirect:/admin";
    }
}
