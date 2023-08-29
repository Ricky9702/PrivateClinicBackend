/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.repository.impl;

import com.clinic.pojo.Nurse;
import com.clinic.pojo.Patient;
import com.clinic.repository.PatientRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hp
 */
@Repository
@Transactional
public class PatientRepositoryImpl implements PatientRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Patient getById(int id) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Patient> query = builder.createQuery(Patient.class);
        Root<Patient> root = query.from(Patient.class);
        query.select(root);
        try {
            Predicate predicate = builder.equal(root.get("id"), id);
            query.where(predicate);
        } catch (NumberFormatException e) {
        }

        Query<Patient> q = session.createQuery(query);
        return q.getSingleResult();

    }

    @Override
    public List<Patient> getAllPatients(Map<String, Object> params) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Patient> query = builder.createQuery(Patient.class);
        Root<Patient> root = query.from(Patient.class);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String patientName = params.getOrDefault("patientName", "").toString().trim();
            if (!patientName.isEmpty()) {
              predicates.add(builder.like(root.get("userId").get("name"), String.format("%%%s%%", patientName)));
            }
            
             String patientId = params.getOrDefault("id", "").toString().trim();
            if (!patientId.isEmpty()) {
              predicates.add(builder.equal(root.get("id"), patientId));
            }
            
            query.where(predicates.toArray(new Predicate[]{}));
        }
        query.select(root);
        Query<Patient> q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public Boolean create(Patient patient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean update(Patient patient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
