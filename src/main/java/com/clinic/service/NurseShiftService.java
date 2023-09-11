/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.service;

import com.clinic.pojo.NurseShift;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface NurseShiftService {
    List<NurseShift> getNursesShift();
    NurseShift getNurseShiftById(int id);
    List<Object[]> getNurseAndShifts(Map<String, String> params);
    Boolean createNurseShift(NurseShift nurseShift);
    Boolean updateNurseShift(NurseShift nurseShift);
    Boolean deleteNurseShift(int id);
}
