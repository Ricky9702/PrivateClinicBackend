/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.service.impl;

import com.clinic.pojo.Doctor;
import com.clinic.pojo.DoctorShift;
import com.clinic.pojo.Nurse;
import com.clinic.pojo.NurseShift;
import com.clinic.pojo.Shift;
import com.clinic.pojo.User;
import com.clinic.repository.ShiftRepository;
import com.clinic.service.DoctorService;
import com.clinic.service.DoctorShiftService;
import com.clinic.service.NurseService;
import com.clinic.service.NurseShiftService;
import com.clinic.service.ShiftService;
import com.clinic.service.UserService;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftRepository repo;
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorShiftService doctorShiftService;
    @Autowired
    private NurseService nurseService;
    @Autowired
    private NurseShiftService nurseShiftService;
    @Autowired
    private SimpleDateFormat f;

    @Override
    public List<Shift> getShifts() {
        return this.repo.getShifts();
    }

    @Override
    public boolean createShift(Map<String, String[]> shift) {
        try {
            String userName = shift.get("userName")[0];
            String shiftId = shift.get("shiftId")[0];
            String date = shift.get("date")[0];

            User user = userService.getUserByName(userName);
            Shift s = this.getShift(Integer.parseInt(shiftId));

            if (user.getUserRole().equals("ROLE_DOCTOR")) {
                DoctorShift doctorShift = new DoctorShift();
                Doctor doctor = doctorService.getDoctorByUserId(user.getId());
                doctorShift.setDoctorId(doctor);
                doctorShift.setShiftId(s);
                doctorShift.setDate(f.parse(date));
                doctorShiftService.createDoctorShift(doctorShift);
            } else {
                NurseShift nurseShift = new NurseShift();
                Nurse nurse = nurseService.getNurseByUserId(user.getId());
                nurseShift.setNurseId(nurse);
                nurseShift.setShiftId(s);
                nurseShift.setDate(f.parse(date));
                nurseShiftService.createNurseShift(nurseShift);
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public Shift getShift(int id) {
        return this.repo.getShift(id);
    }

}
