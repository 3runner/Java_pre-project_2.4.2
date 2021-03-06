package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.entity.Role;
import web.entity.User;
import web.service.AdminService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ModelAttribute("roleSet")
    private Set<Role> roleSet() {
        return new HashSet<>(Arrays.asList(new Role("USER"), new Role("ADMIN")));
    }

    @GetMapping("/admin")
    public String getUsersPage(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
                return "pages/users";
    }

    @GetMapping("/user/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "pages/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "pages/new";

        adminService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", adminService.getUser(id));
        return "pages/edit";
    }

    @PatchMapping("/user/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "pages/edit";

        adminService.updateUser(id, user.getName(), user.getPassword(), user.getRoles());
        return "redirect:/admin";
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable("id") long id) {
        adminService.deleteUser(id);
        return "redirect:/admin";
    }
}