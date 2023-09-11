/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.repository.impl;

import com.clinic.pojo.Doctor;
import com.clinic.pojo.Nurse;
import com.clinic.pojo.NurseShift;
import com.clinic.repository.NurseShiftRepository;
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
public class NurseShiftRepositoryImpl implements NurseShiftRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private SimpleDateFormat f;
    
    @Override
    public List<NurseShift> getNursesShift() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<NurseShift> q = b.createQuery(NurseShift.class);
        Root<NurseShift> root = q.from(NurseShift.class);
        q.select(root);

       
        Query<NurseShift> query = session.createQuery(q);
        return query.getResultList();    
    }

    @Override
    public Boolean createNurseShift(NurseShift nurseShift) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(nurseShift);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }    
    }

    @Override
    public Boolean updateNurseShift(NurseShift nurseShift) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(nurseShift);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }    }

    @Override
    public Boolean deleteNurseShift(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        NurseShift nurseShift = this.getNurseShiftById(id);
        try {
            s.update(nurseShift);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public NurseShift getNurseShiftById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<NurseShift> q = b.createQuery(NurseShift.class);
        Root<NurseShift> root = q.from(NurseShift.class);
        q.select(root);

        try {
            Predicate predicate = b.equal(root.get("id"), id);
            q.where(predicate);
        } catch (NumberFormatException e) {
        }
        Query<NurseShift> query = session.createQuery(q);
        return query.getSingleResult();    
    }
    
    @Override
    public List<Object[]> getNurseAndShifts(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        Root<NurseShift> nurseShiftRoot = query.from(NurseShift.class);

        Join<NurseShift, Nurse> nurseJoin = nurseShiftRoot.join("nurseId");
        
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String fromDate = params.get("fromDate"); // yyyy-MM-dd;
            if (fromDate != null && !fromDate.isEmpty()) {
                try {
                    // Adjust the attribute name accordingly based on the selected table
                        predicates.add(builder.greaterThanOrEqualTo(nurseShiftRoot.get("date"), f.parse(fromDate)));
                } catch (ParseException ex) {
                    Logger.getLogger(DoctorShiftRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String toDate = params.get("toDate"); // yyyy-MM-dd;
            if (toDate != null && !toDate.isEmpty()) {
                try {
                        predicates.add(builder.lessThanOrEqualTo(nurseShiftRoot.get("date"), f.parse(toDate)));
                } catch (ParseException ex) {
                    Logger.getLogger(DoctorShiftRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
           query.multiselect(
                    nurseJoin.get("userId").get("id"),
                    nurseJoin.get("userId").get("name"),
                    nurseJoin.get("userId").get("userRole"),
                    nurseShiftRoot.get("shiftId"),
                    nurseShiftRoot.get("date"));
            query.where(predicates.toArray(Predicate[]::new));
            
            query.orderBy(builder.desc(nurseJoin.get("id")));
        }
        return s.createQuery(query).getResultList();        }
}
