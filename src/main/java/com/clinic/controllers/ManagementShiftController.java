/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.controllers;

import com.clinic.pojo.User;
import com.clinic.service.ShiftService;
import com.clinic.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author hp
 */
@Controller
@RequestMapping("/management/shifts")
public class ManagementShiftController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private ShiftService shiftService;
    
    @GetMapping("/")
    public String shift() {
        return "shift";
    }

    @GetMapping("/create/")
    public String createShift(Model model, @ModelAttribute("shift") HashMap<String, String> shift) {
        model.addAttribute("userList", userService.getDoctorNurse(null));
        model.addAttribute("shiftList", shiftService.getShifts());
        return "createShift";
    }

    @PostMapping("/create/")
    public String create(Model model, HttpServletRequest req) {
        
        model.addAttribute("userList", userService.getDoctorNurse(null));
        model.addAttribute("shiftList", shiftService.getShifts());
        
        Map<String, String[]> shift = req.getParameterMap();
        User user = userService.getUserByName(shift.get("userName")[0]);
        if (user == null) {
            model.addAttribute("msg", "Nhân viên không tồn tại!!");
            return "createShift"; 
        } else {
            if (shiftService.createShift(shift)) {
                return "redirect:/management/shifts/"; 
            } else {
                model.addAttribute("msg", "Failed to create shift!!");
                return "createShift";
            }
        }
    }
}
