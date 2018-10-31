/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ol.exercise.exerciseol;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author israel
 */
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Item ID")
    private int itemId;
    @ApiModelProperty(notes = "Quantity of itmes")
    private int quantity;
    @ApiModelProperty(notes = "Item Name")
    private String itemName;
    @ApiModelProperty(notes = "Item Type")
    private String itemType;
    @ApiModelProperty(notes = "Item Description")
    private String itemDescription;
    @ApiModelProperty(notes = "Item Price")
    private double itemPrice;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return String.format(
                "ITEM[itemId=%d, itemName='%s', itemType='%s', quantity='%i', itemDescription='%s']",
                itemId, itemName, itemType, quantity, itemDescription);
    }
}
