/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.service.impl;

import com.clinic.pojo.NurseShift;
import com.clinic.repository.NurseRepository;
import com.clinic.repository.NurseShiftRepository;
import com.clinic.service.NurseShiftService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class NurseShiftServiceImpl implements NurseShiftService{
    
    @Autowired
    private NurseShiftRepository nurseRepo;

    @Override
    public List<NurseShift> getNursesShift() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public NurseShift getNurseShiftById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Object[]> getNurseAndShifts(Map<String, String> params) {
        return this.nurseRepo.getNurseAndShifts(params);
    }

    @Override
    public Boolean createNurseShift(NurseShift nurseShift) {
        return this.nurseRepo.createNurseShift(nurseShift);
    }

    @Override
    public Boolean updateNurseShift(NurseShift nurseShift) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean deleteNurseShift(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   
    
}
