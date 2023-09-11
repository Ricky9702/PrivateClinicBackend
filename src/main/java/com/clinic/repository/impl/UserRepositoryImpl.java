/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.repository.impl;

import com.clinic.pojo.User;
import com.clinic.pojo.Doctor;
import com.clinic.pojo.DoctorShift;
import com.clinic.pojo.MedicalReport;
import com.clinic.pojo.Nurse;
import com.clinic.pojo.NurseShift;
import com.clinic.pojo.Patient;
import com.clinic.pojo.ReportDetail;
import com.clinic.repository.DepartmentRepository;
import com.clinic.repository.DoctorRepository;
import com.clinic.repository.DoctorShiftRepository;
import com.clinic.repository.NurseRepository;
import javax.persistence.NoResultException;
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
import com.clinic.repository.UserRepository;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Selection;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private NurseRepository NurseRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private SimpleDateFormat f;

    @Override
    public User getUserByName(String name) {
        Session s = this.factory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.select(root);

        try {
            Predicate predicate = b.equal(root.get("name"), name.trim());
            q.where(predicate);

            Query<User> query = s.createQuery(q);
            return query.getResultList().isEmpty() ? null : query.getResultList().get(0);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.select(root);

        try {
            Predicate predicate = b.equal(root.get("username"), username.trim());
            q.where(predicate);

            Query<User> query = s.createQuery(q);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.select(root);

        try {
            Predicate predicate = b.equal(root.get("id"), id);
            q.where(predicate);

            Query<User> query = s.createQuery(q);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User createUser(User user) {
        Session session = this.factory.getObject().getCurrentSession();
        Patient patient = new Patient();
        try {
            patient.setUserId(user);
            session.save(user);
            session.save(patient);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean authUser(String username, String password) {
        User u = this.getUserByUsername(username);

        return this.passEncoder.matches(password, u.getPassword());

    }

    @Override
    public List<User> getDoctorNurse(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }
        }
        predicates.add(root.get("userRole").in("ROLE_DOCTOR", "ROLE_NURSE"));
        q.where(predicates.toArray(Predicate[]::new));
        q.orderBy(
                b.desc(root.get("active")),
                b.desc(root.get("id"))
        );
        return s.createQuery(q).getResultList();

    }

    @Override
    public boolean update(User user) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder builder = s.getCriteriaBuilder();
            CriteriaUpdate<User> updateQuery = builder.createCriteriaUpdate(User.class);
            Root<User> root = updateQuery.from(User.class);

            updateQuery.set(root.get("name"), user.getName());
            updateQuery.set(root.get("username"), user.getUsername());
            updateQuery.set(root.get("email"), user.getEmail());
            updateQuery.set(root.get("birthday"), user.getBirthday());
            updateQuery.set(root.get("address"), user.getAddress());
            updateQuery.set(root.get("gender"), user.getGender());
            updateQuery.set(root.get("active"), user.getActive());
            updateQuery.set(root.get("userRole"), user.getUserRole());

            User existingUser = this.getUserById(user.getId());

            if (!existingUser.getPassword().equals(user.getPassword())) {
                updateQuery.set(root.get("password"), passEncoder.encode(user.getUserRole()));
            }

            if (user.getImage() != null && !user.getImage().isEmpty()) {
                updateQuery.set(root.get("image"), user.getImage());
            }

            switch (user.getUserRole()) {
                case "ROLE_DOCTOR": {
                    Doctor doctor = doctorRepository.getDoctorByUserId(user.getId());
                    if (doctor != null) {
                        doctor.setDepartmentId(departmentRepository.getDepartmentById(Integer.valueOf(user.getRoleDetail())));
                        s.update(doctor);
                    } else {
                        doctor = new Doctor();
                        doctor.setUserId(user);
                        doctor.setDepartmentId(departmentRepository.getDepartmentById(Integer.valueOf(user.getRoleDetail())));
                        s.save(doctor);
                    }
                    break;
                }

                case "ROLE_NURSE": {
                    Nurse nurse = NurseRepository.getNurseByUserId(user.getId());
                    if (nurse == null) {
                        nurse = new Nurse();
                        nurse.setUserId(user);
                        s.save(nurse);
                    }
                }
                break;
                default:
                    break;
            }

            updateQuery.where(builder.equal(root.get("id"), user.getId()));
            return s.createQuery(updateQuery).executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateActiveField(User user) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder builder = s.getCriteriaBuilder();
            CriteriaUpdate<User> updateQuery = builder.createCriteriaUpdate(User.class);
            Root<User> root = updateQuery.from(User.class);
            updateQuery.set(root.get("active"), 0);
            User existingUser = this.getUserById(user.getId());
            updateQuery.where(builder.equal(root.get("id"), user.getId()));
            return s.createQuery(updateQuery).executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createDetailUser(User user) {
        Session session = this.factory.getObject().getCurrentSession();

        try {
            session.save(user);

            switch (user.getUserRole()) {
                case "ROLE_NURSE": {
                    Nurse nurse = new Nurse();
                    nurse.setUserId(user);
                    session.save(nurse);
                    break;
                }

                case "ROLE_DOCTOR": {
                    Doctor doctor = new Doctor();
                    doctor.setUserId(user);
                    doctor.setDepartmentId(departmentRepository.getDepartmentById(Integer.valueOf(user.getRoleDetail())));
                    session.save(doctor);
                    break;
                }
                default:
                    break;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
