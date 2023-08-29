/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.service.impl;

import com.clinic.pojo.MedicineUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clinic.repository.MedicineUnitRepository;
import com.clinic.service.MedicineUnitService;

/**
 *
 * @author admin
 */
@Service
public class MedicineUnitServiceImpl implements MedicineUnitService{
    @Autowired
    private MedicineUnitRepository medicineUnitRepository;

    @Override
    public List<MedicineUnit> getAllMedicineUnit() {
        return this.medicineUnitRepository.getAllMedicineUnit();
    }

    @Override
    public MedicineUnit getById(int id) {
        return this.medicineUnitRepository.getById(id);
    }
    
}
