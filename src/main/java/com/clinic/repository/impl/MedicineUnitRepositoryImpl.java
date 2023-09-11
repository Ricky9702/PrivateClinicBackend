/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.repository.impl;

import com.clinic.pojo.Category;
import com.clinic.pojo.Medicine;
import com.clinic.pojo.MedicineUnit;
import com.clinic.pojo.Unit;
import com.clinic.repository.CategoryRepository;
import com.clinic.repository.MedicineRepository;
import com.clinic.repository.MedicineUnitRepository;
import com.clinic.repository.UnitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
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
 * @author admin
 */
@Repository
@Transactional
public class MedicineUnitRepositoryImpl implements MedicineUnitRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private CategoryRepository cateRepository;

    @Override
    public List<MedicineUnit> getAllMedicineUnit(Map<String, String> object) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<MedicineUnit> q = b.createQuery(MedicineUnit.class);
        Root root = q.from(MedicineUnit.class);
        q.select(root);
        if (object != null) {
            List<Predicate> predicates = new ArrayList<>();
            String medicineId = object.get("medicineId");

            if (medicineId != null && !medicineId.isEmpty()) {
                predicates.add(b.equal(root.get("medicineId").get("id"), medicineId));
            }
            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = session.createQuery(q);
        return query.getResultList();
    }

    @Override
    public MedicineUnit getOrCreateByMedicineUnit(int medicineId, int unitId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<MedicineUnit> query = builder.createQuery(MedicineUnit.class);
        Root<MedicineUnit> root = query.from(MedicineUnit.class);
        query.select(root)
                .where(builder.equal(root.get("medicineId").get("id"), medicineId),
                        builder.equal(root.get("unitId").get("id"), unitId));
        Query<MedicineUnit> q = session.createQuery(query);

        List<MedicineUnit> medicineUnits = q.getResultList();
        if (!medicineUnits.isEmpty()) {
            return medicineUnits.get(0);
        } else {
            Medicine medicine = medicineRepository.getMedicineById(medicineId);
            Unit unit = unitRepository.getUnitById(unitId);

            MedicineUnit newMedicineUnit = new MedicineUnit();
            newMedicineUnit.setMedicineId(medicine);
            newMedicineUnit.setUnitId(unit);
            newMedicineUnit.setUnitPrice(0);
            newMedicineUnit.setQuantity(0);

            session.save(newMedicineUnit);
            return newMedicineUnit;
        }
    }

    @Override
    public boolean update(MedicineUnit medicineUnit) {
        try {
            Session session = factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<MedicineUnit> updateCriteria = builder.createCriteriaUpdate(MedicineUnit.class);
            Root<MedicineUnit> root = updateCriteria.from(MedicineUnit.class);
            updateCriteria.set(root.get("quantity"), medicineUnit.getQuantity());
            updateCriteria.where(builder.equal(root.get("id"), medicineUnit.getId()));
            int updatedCount = session.createQuery(updateCriteria).executeUpdate();
            return updatedCount > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Object[]> getMedicineUnitList(Map<String, String> object) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
       CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root root = q.from(MedicineUnit.class);
        q.multiselect(
                root.get("medicineId").get("categoryId").get("name"),
                root.get("medicineId").get("name"),
                                root.get("id"),
                    root.get("unitId").get("name"),
                       root.get("unitPrice"),
                       root.get("quantity"),
                       root.get("active")
        );
        
        if (object != null) {
            List<Predicate> predicates = new ArrayList<>();
            
            String kw = object.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("medicineId").get("name"), String.format("%%%s%%", kw)));
            }        
            
            String cateName = object.get("cateName");
             if (cateName != null && !cateName.isEmpty()) {
                predicates.add(b.like(root.get("medicineId").get("categoryId").get("name"), String.format("%%%s%%", cateName)));
            }
             
            q.where(predicates.toArray(Predicate[]::new));
        }
        
        q.orderBy(
            b.asc(root.get("medicineId").get("categoryId").get("id")),
            b.asc(root.get("medicineId").get("id")),
            b.desc(root.get("active"))
        );
        Query query = session.createQuery(q);
        return query.getResultList();
    }

    @Override
    public boolean createOrUpdateMedicineUnit(MedicineUnit medicineUnit) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            
            Category category = cateRepository.getCategoryByName(medicineUnit.getMedicineId().getCategoryId().getName());
            if (category == null) {
                category = new Category();
                category.setName(medicineUnit.getMedicineId().getCategoryId().getName());
                session.save(category);
            }
            
            Medicine medicine = medicineRepository.getMedicineByName(medicineUnit.getMedicineId().getName());
            if (medicine == null) {
                medicine = new Medicine();
                medicine.setName(medicineUnit.getMedicineId().getName());
                medicine.setCategoryId(category);
                session.save(medicine);
            }
           
            Unit unit = unitRepository.getUnitByName(medicineUnit.getUnitId().getName());
            if (unit == null) {
                unit = new Unit();
                unit.setName(medicineUnit.getUnitId().getName());
                session.save(unit);
            }
            
            medicineUnit.setMedicineId(medicine);
            medicineUnit.setUnitId(unit);
            
            if (medicineUnit.getId() == null) 
                session.save(medicineUnit);
            else
                session.update(medicineUnit);
            
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public MedicineUnit getById(int id) {
 Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<MedicineUnit> query = builder.createQuery(MedicineUnit.class);
        Root<MedicineUnit> root = query.from(MedicineUnit.class);
        query.select(root)
                .where(builder.equal(root.get("id"), id));
        Query<MedicineUnit> q = session.createQuery(query);
        List<MedicineUnit> medicineUnits = q.getResultList();
        return medicineUnits.get(0);
    }
}
