/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.controllers;

import com.clinic.pojo.Medicine;
import com.clinic.service.MedicineService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
public class ApiMedicineController {
    @Autowired
    private MedicineService medicineService;
    
    @CrossOrigin
    @GetMapping("/medicines/")
    public ResponseEntity<List<Medicine>> getAllMedicine(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.medicineService.getAllMedicine(params), HttpStatus.OK);
    }
}
