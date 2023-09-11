/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.repository;

import com.clinic.pojo.DoctorShift;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface DoctorShiftRepository {
    List<DoctorShift> getDoctorShift(Map<String, String> object);
    List<Object[]> getDoctorAndShifts(Map<String, String> params);
    DoctorShift getDoctorShiftById(int id);
    Boolean createDoctorShift(DoctorShift nurse);
    Boolean updateDoctorShift(DoctorShift nurse);
    Boolean deleteDoctorShift(int id);
}
