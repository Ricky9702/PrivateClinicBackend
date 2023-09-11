package com.clinic.controllers;

import com.clinic.pojo.DoctorShift;
import com.clinic.service.DoctorShiftService;
import com.clinic.service.NurseShiftService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api")
public class ApiNurseShiftController {

    @Autowired
    private NurseShiftService nurseService;

    @GetMapping("/nurse-shift-stats/")
    public ResponseEntity<List<Object[]>> getNurseAndShifts(
            @RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.nurseService.getNurseAndShifts(params),
                HttpStatus.OK);
    }
}
