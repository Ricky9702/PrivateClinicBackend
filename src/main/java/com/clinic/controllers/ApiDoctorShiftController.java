/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.controllers;

import com.clinic.pojo.Doctor;
import com.clinic.pojo.DoctorShift;
import com.clinic.service.DoctorService;
import com.clinic.service.DoctorShiftService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api")
public class ApiDoctorShiftController {
    @Autowired
    private DoctorShiftService doctorService;
    
    @GetMapping("/doctors-shift/")
    public ResponseEntity<List<DoctorShift>> list(
                @RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.doctorService.getDoctorShift(params), 
                HttpStatus.OK);
    }
    
    @GetMapping("/doctors-shift-stats/")
    public ResponseEntity<List<Object[]>> getDoctorAndShifts(
                @RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.doctorService.getDoctorAndShifts(params), 
                HttpStatus.OK);
    }
}