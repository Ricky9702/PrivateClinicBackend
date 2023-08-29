/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.controllers;

import com.clinic.pojo.MedicineUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.clinic.service.MedicineUnitService;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
public class ApiMedicineUnitController {
    @Autowired
    private MedicineUnitService MedicineUnitService;
    @GetMapping("/unit-medicine")
    public ResponseEntity<List<MedicineUnit>> getAllMedicineUnit() {
        return new ResponseEntity<>(this.MedicineUnitService.getAllMedicineUnit(), HttpStatus.OK);
    }
}
