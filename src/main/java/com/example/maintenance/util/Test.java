package com.example.maintenance.util;

import com.example.maintenance.entity.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.maintenance.controller.AdministratorController;

@Component
public class Test {
    private final AdministratorController administratorController;

    @Autowired
    public Test(AdministratorController administratorController) {
        this.administratorController = administratorController;
    }

    public void test() {
        Administrator a = new Administrator();
        administratorController.save(a);
        administratorController.deleteById(a.getId());
    }
}
