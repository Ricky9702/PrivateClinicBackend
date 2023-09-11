/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.service;

import com.clinic.pojo.Shift;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hp
 */
public interface ShiftService {
    List<Shift> getShifts();
    Shift getShift(int id);
    boolean createShift(Map<String, String[]> shift);
}
