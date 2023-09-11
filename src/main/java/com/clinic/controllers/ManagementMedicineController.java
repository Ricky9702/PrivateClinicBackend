/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinic.controllers;

import com.clinic.pojo.Category;
import com.clinic.pojo.Medicine;
import com.clinic.pojo.MedicineUnit;
import com.clinic.pojo.Unit;
import com.clinic.service.CategoryService;
import com.clinic.service.MedicineUnitService;
import com.clinic.service.UnitService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author hp
 */
@Controller
@RequestMapping("/management/medicines")
public class ManagementMedicineController {

    @Autowired
    private CategoryService categorySerivce;
    @Autowired
    private MedicineUnitService medicineUnitService;
    @Autowired
    private UnitService unitService;

    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("cateList", categorySerivce.getCategories());
        model.addAttribute("cateList", categorySerivce.getCategories());
        model.addAttribute("unitList", unitService.getUnits(null));
    }

    @GetMapping("/")
    public String medicines(Model model) {
        return "medicine";
    }

    @GetMapping("/create/")
    public String createPage(Model model) {
        model.addAttribute("medicineUnit", new MedicineUnit());
        return "createMedicine";
    }

    @PostMapping("/create/")
    public String createOrUpdate(
            @ModelAttribute(value = "medicineUnit") @Valid MedicineUnit m, BindingResult rsMedicineUnit) {
        if (!rsMedicineUnit.hasErrors()) {
            if (medicineUnitService.createOrUpdateMedicineUnit(m)) {
                return "redirect:/management/medicines/";
            }
        }
        return "createMedicine";
    }

    @GetMapping("/{id}")
    public String updatePage(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("medicineUnit", medicineUnitService.getById(id));
        return "createMedicine";
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") int id) {
        MedicineUnit m = medicineUnitService.getById(id);
        if (m != null) {
                m.setActive(0);
            if (medicineUnitService.createOrUpdateMedicineUnit(m)) {
                return "success";
            }
        }
        return "failed";
    }

}
