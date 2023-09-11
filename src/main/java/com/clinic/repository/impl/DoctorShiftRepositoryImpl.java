/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.repository.impl;

import com.clinic.pojo.Doctor;
import com.clinic.pojo.DoctorShift;
import com.clinic.pojo.Nurse;
import com.clinic.pojo.NurseShift;
import com.clinic.repository.DoctorShiftRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class DoctorShiftRepositoryImpl implements DoctorShiftRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private SimpleDateFormat f;
    
    
    @Override
    public List<DoctorShift> getDoctorShift(Map<String, String> object) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<DoctorShift> q = b.createQuery(DoctorShift.class);
        Root<DoctorShift> root = q.from(DoctorShift.class);
        q.select(root);
        
        if (object != null) {
            List<Predicate> predicates = new ArrayList<>();
            
            String doctorId = object.get("doctorId");
            if (doctorId != null && !doctorId.isEmpty())
                predicates.add(b.equal(root.get("doctorId").get("id"), Integer.valueOf(doctorId)));
            
            String fromDate = object.get("fromDate"); // yyyy-MM-dd;
            if (fromDate != null && !fromDate.isEmpty())
                try {
                    predicates.add(b.greaterThanOrEqualTo(root.get("date"), f.parse(fromDate)));
            } catch (ParseException ex) {
                Logger.getLogger(DoctorShiftRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String toDate = object.get("toDate"); // yyyy-MM-dd;
                if (toDate != null && !toDate.isEmpty())
            try {
                predicates.add(b.lessThanOrEqualTo(root.get("date"), f.parse(toDate)));
            } catch (ParseException ex) {
                Logger.getLogger(DoctorShiftRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            q.where(predicates.toArray(Predicate[]:: new));
        }
        
        Query<DoctorShift> query = session.createQuery(q);
        return query.getResultList();  
    }

    @Override
    public Boolean createDoctorShift(DoctorShift doctorShift) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(doctorShift);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }    
    }

    @Override
    public Boolean updateDoctorShift(DoctorShift doctorShift) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(doctorShift);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        } 
    }

    @Override
    public Boolean deleteDoctorShift(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        DoctorShift doctorShift = this.getDoctorShiftById(id);
        try {
            s.update(doctorShift);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }    }

    @Override
    public DoctorShift getDoctorShiftById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<DoctorShift> q = b.createQuery(DoctorShift.class);
        Root<DoctorShift> root = q.from(DoctorShift.class);
        q.select(root);

        try {
            Predicate predicate = b.equal(root.get("id"), id);
            q.where(predicate);
        } catch (NumberFormatException e) {
        }
        Query<DoctorShift> query = session.createQuery(q);
        return query.getSingleResult();     
    }

    @Override
    public List<Object[]> getDoctorAndShifts(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        Root<DoctorShift> doctorShiftRoot = query.from(DoctorShift.class);

        Join<DoctorShift, Doctor> doctorJoin = doctorShiftRoot.join("doctorId");
        
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String fromDate = params.get("fromDate"); // yyyy-MM-dd;
            if (fromDate != null && !fromDate.isEmpty()) {
                try {
                    // Adjust the attribute name accordingly based on the selected table
                        predicates.add(builder.greaterThanOrEqualTo(doctorShiftRoot.get("date"), f.parse(fromDate)));
                } catch (ParseException ex) {
                    Logger.getLogger(DoctorShiftRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String toDate = params.get("toDate"); // yyyy-MM-dd;
            if (toDate != null && !toDate.isEmpty()) {
                try {
                        predicates.add(builder.lessThanOrEqualTo(doctorShiftRoot.get("date"), f.parse(toDate)));
                } catch (ParseException ex) {
                    Logger.getLogger(DoctorShiftRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
           query.multiselect(
                    doctorJoin.get("userId").get("id"),
                    doctorJoin.get("userId").get("name"),
                    doctorJoin.get("userId").get("userRole"),
                    doctorShiftRoot.get("shiftId"),
                    doctorShiftRoot.get("date"));
            query.where(predicates.toArray(Predicate[]::new));
            
            query.orderBy(builder.desc(doctorJoin.get("id")));
        }
        return s.createQuery(query).getResultList();    
    }
    
}
