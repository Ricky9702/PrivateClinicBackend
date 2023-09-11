/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.controllers;

import com.clinic.pojo.User;
import com.clinic.service.ShiftService;
import com.clinic.service.UserService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hp
 */
@Controller
@RequestMapping("/management/users")
public class ManagementUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String list(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("userList", userService.getDoctorNurse(params));
        return "users";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable(value = "id") int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "detailuser";
        }
        return "users";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute(value = "user") @Valid User u, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (userService.update(u)) {
                return "redirect:/management/users/";
            }
        }
        return "detailuser";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            if (userService.updateActiveField(user)) {
                return "success";
            }
        }
        return "failed";
    }

    @GetMapping("/create/")
    public String createPage(Model model) {
        model.addAttribute("user", new User());
        return "detailuser";

    }

    @PostMapping("/create/")
    public String createUser(@ModelAttribute(value = "user") @Valid User u, BindingResult rs) {

        if (!rs.hasErrors()) {
            if (userService.createDetailUser(u)) {
                return "redirect:/management/users/";
            }
        }
        return "detailuser";
    }
}
