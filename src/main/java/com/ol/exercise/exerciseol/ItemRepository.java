/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ol.exercise.exerciseol;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author israel
 */
public interface ItemRepository extends CrudRepository<Inventory, Long> {

    List<Inventory> findByItemName(@Param("itemName") String name);

    @Query("select i from Inventory i")
    List<Inventory> findItems();

    Inventory findByItemId(@Param("itemId") int itemId);

    @Query("update Inventory set quantity =:quantity where itemId =:itemId")
    void withdrawal(@Param("itemId") int itemId, @Param("quantity") int quantity);

}
