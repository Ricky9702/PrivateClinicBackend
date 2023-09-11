/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.service;

import com.clinic.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public interface UserService extends UserDetailsService {

    User getUserByName(String name);

    User getUserByUsername(String username);

    User getUserById(int id);

    User createUser(Map<String, String> params, MultipartFile avatar);
     boolean createDetailUser(User user);

    boolean authUser(String username, String password);

    List<User> getDoctorNurse(Map<String, String> params);

    boolean update(User user);

    boolean updateActiveField(User user);
}
