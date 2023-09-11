/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.service.impl;

import com.clinic.pojo.MedicineUnit;
import com.clinic.repository.MedicineUnitRepository;
import com.clinic.service.MedicineUnitService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class MedicineUnitServiceImpl implements MedicineUnitService{
    @Autowired
    private MedicineUnitRepository medicineUnitRepository;


    @Override
    public List<MedicineUnit> getAllMedicineUnit(java.util.Map<String, String> object) {
        return this.medicineUnitRepository.getAllMedicineUnit(object);    
    }

    @Override
    public List<Object[]> getMedicineUnitList(Map<String, String> object) {
        return this.medicineUnitRepository.getMedicineUnitList(object);
    }

    @Override
    public boolean createOrUpdateMedicineUnit(MedicineUnit medicineUnit) {
        return this.medicineUnitRepository.createOrUpdateMedicineUnit(medicineUnit);
    }

    @Override
    public MedicineUnit getById(int id) {
        return this.medicineUnitRepository.getById(id);
    }
    
    @Override
    public boolean update(MedicineUnit medicineUnit) {
        return this.medicineUnitRepository.update(medicineUnit);
    }
    
}
