/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.service.impl;

import com.clinic.pojo.Doctor;
import com.clinic.pojo.DoctorShift;
import com.clinic.repository.DoctorShiftRepository;
import com.clinic.service.DoctorService;
import com.clinic.service.DoctorShiftService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class DoctorShiftServiceImpl implements DoctorShiftService{
    @Autowired
     private DoctorShiftRepository repo;

    @Override
    public List<DoctorShift> getDoctorShift(Map<String, String> object) {
        return this.repo.getDoctorShift(object);
    }

    @Override
    public DoctorShift getDoctorShiftById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean createDoctorShift(DoctorShift nurse) {
        return this.repo.createDoctorShift(nurse);
    }

    @Override
    public Boolean updateDoctorShift(DoctorShift nurse) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean deleteDoctorShift(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Object[]> getDoctorAndShifts(Map<String, String> params) {
        return this.repo.getDoctorAndShifts(params);
    }
    
  
    
}
