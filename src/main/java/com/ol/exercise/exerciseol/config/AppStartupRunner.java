/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ol.exercise.exerciseol.config;

import com.ol.exercise.exerciseol.Inventory;
import com.ol.exercise.exerciseol.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author israel
 */
@Component
public class AppStartupRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

    @Autowired
    ItemRepository repository;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("***********************************************************");
        logger.info("*****************Init Inventory API REST OL****************");        
        logger.info("http://localhost:8088/swagger-ui.html#!/inventory-controller");
        logger.info("***********************************************************");
        Inventory item1 = new Inventory();
        item1.setItemDescription("Chisel Expert De Hombre 29´´");
        item1.setItemName("Chisel Expert");
        item1.setItemType("MTB");
        item1.setQuantity(10);
        item1.setItemPrice(42100.00);
        repository.save(item1);
        Inventory item2 = new Inventory();
        item2.setItemDescription("Epic Hardtail Comp De Hombre 29´´");
        item2.setItemName("Epic Hardtail Comp");
        item2.setItemType("MTB");
        item2.setQuantity(5);
        item2.setItemPrice(56900.00);
        repository.save(item2);
        logger.info("*****************Save H2 Inventory****************");

    }

}