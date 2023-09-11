/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.repository.impl;

import com.clinic.pojo.Shift;
import com.clinic.repository.ShiftRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hp
 */
@Transactional
@Repository
public class ShiftRepositoryImpl implements ShiftRepository{
    
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Shift> getShifts() {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder builder = s.getCriteriaBuilder();
            CriteriaQuery<Shift> query = builder.createQuery(Shift.class);
            Root<Shift> root = query.from(Shift.class);
            query.select(root);
            return s.createQuery(query).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Shift getShift(int id) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder builder = s.getCriteriaBuilder();
            CriteriaQuery<Shift> query = builder.createQuery(Shift.class);
            Root<Shift> root = query.from(Shift.class);
            query.select(root);
            query.where(builder.equal(root.get("id"), id));
            return s.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
