/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.repository;

import com.clinic.pojo.Nurse;
import com.clinic.pojo.Patient;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hp
 */
public interface PatientRepository {
    Patient getById(int id);
    List<Patient> getAllPatients(Map<String, Object> params);
    Boolean create(Patient patient);
    Boolean update(Patient patient);
    Boolean delete(int id);
}
