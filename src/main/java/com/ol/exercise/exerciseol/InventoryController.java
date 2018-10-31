/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ol.exercise.exerciseol;

import com.ol.exercise.exerciseol.Inventory;
import com.ol.exercise.exerciseol.config.AppStartupRunner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author israel
 */
@RestController
@Api(value = "/inventory-controller", description = "Items Controller")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    ItemRepository repository;

    @RequestMapping(path = "/getItems",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "List all Items",
            notes = "List all Items in inventory",
            response = Inventory.class,
            responseContainer = "List")
    public ResponseEntity<List<Inventory>> getItems() {
        List<Inventory> listItem = repository.findItems();
        return new ResponseEntity<List<Inventory>>(listItem, HttpStatus.OK);
    }

    @RequestMapping(path = "/getItem",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Item",
            notes = "Item in inventory",
            response = Inventory.class)
    @ApiResponses({
        @ApiResponse(code = 404, message = "Item with such itemID doesn't exists")
    })
    public ResponseEntity<Inventory> getItem(@ApiParam(value = "itemId to lookup for", required = true)
            @RequestParam("itemId") String itemId) {
        Inventory item = null;
        ResponseEntity response = null;
        if (null != itemId && !itemId.equals("")) {
            try {
                item = repository.findByItemId(Integer.parseInt(itemId));
                response = new ResponseEntity<Inventory>(item, HttpStatus.OK);
            } catch (NumberFormatException excepcion) {
                response = new ResponseEntity<Inventory>(item, HttpStatus.BAD_REQUEST);
            }
        } else {
            response = new ResponseEntity<Inventory>(item, HttpStatus.BAD_REQUEST);
        }
        if (null == item) {
            response = new ResponseEntity<Inventory>(item, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @RequestMapping(path = "/addItem",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "new Item",
            notes = "new Item in inventory",
            response = Inventory.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Data Error")
    })
    public ResponseEntity<Inventory> addItem(
            @ApiParam(value = "Name of Item", required = true)
            @RequestParam("itemName") String itemName,
            @ApiParam(value = "Quantity of Item", required = true)
            @RequestParam("itemQuantity") String itemQuantity,
            @ApiParam(value = "Type of Item", required = true)
            @RequestParam("itemType") String itemType,
            @ApiParam(value = "Price of Item", required = true)
            @RequestParam("itemPrice") String itemPrice,
            @ApiParam(value = "Description of Item", required = false)
            @RequestParam("itemDescription") String itemDescription) {
        Inventory item1 = new Inventory();
        item1.setItemDescription(itemDescription);
        item1.setItemName(itemName);
        item1.setItemType(itemType);

        if (null != itemQuantity && !itemQuantity.equals("")) {
            try {
                item1.setQuantity(Integer.parseInt(itemQuantity));
            } catch (NumberFormatException excepcion) {
                logger.error("Se presento un error con la cantidad" + excepcion.getMessage());
                return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
            }
        }
        if (null != itemPrice && !itemPrice.equals("")) {
            try {
                item1.setItemPrice(Double.parseDouble(itemPrice));
            } catch (NumberFormatException excepcion) {
                logger.error("Se presento un error con el precio" + excepcion.getMessage());
                return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
            }
        }

        repository.save(item1);
        return new ResponseEntity<Inventory>(item1, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/deleteItem",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "delete Item",
            notes = "delete Item",
            response = Inventory.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Data Error")
        ,
        @ApiResponse(code = 404, message = "Item not found")
    })
    public ResponseEntity<Inventory> deleteItem(
            @ApiParam(value = "id of Item", required = true)
            @RequestParam("itemId") String itemId) {
        Inventory item1 = null;

        if (null != itemId && !itemId.equals("")) {
            try {
                item1 = repository.findByItemId(Integer.parseInt(itemId));
            } catch (NumberFormatException excepcion) {
                logger.error("Se presento un error con el id" + excepcion.getMessage());
                return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
            }
        }
        if (null == item1) {
            return new ResponseEntity<Inventory>(item1, HttpStatus.NOT_FOUND);
        }
        repository.delete(item1);
        return new ResponseEntity<Inventory>(item1, HttpStatus.OK);
    }

    @RequestMapping(path = "/withdrawal",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "withdrawal Quantity",
            notes = "withdrawal Quantity",
            response = Inventory.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Data Error")
        ,
        @ApiResponse(code = 404, message = "Item not found")
        ,
        @ApiResponse(code = 409, message = "Insufficient quantity of items")
    })
    public ResponseEntity<Inventory> withdrawal(
            @ApiParam(value = "id of Item", required = true)
            @RequestParam("itemId") String itemId,
            @ApiParam(value = "Quantity of Item", required = true)
            @RequestParam("itemQuantity") String itemQuantity) {
        Inventory item1 = null;

        if (null != itemId && !itemId.equals("")) {
            try {
                item1 = repository.findByItemId(Integer.parseInt(itemId));
            } catch (NumberFormatException excepcion) {
                logger.error("Se presento un error con el id" + excepcion.getMessage());
                return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
        }
        if (null == item1) {
            return new ResponseEntity<Inventory>(item1, HttpStatus.NOT_FOUND);
        } else {
            if (null != itemQuantity && !itemQuantity.equals("")) {
                try {
                    if (item1.getQuantity() < Integer.parseInt(itemQuantity)) {
                        return new ResponseEntity<Inventory>(item1, HttpStatus.CONFLICT);
                    }
                    int newQuantity = item1.getQuantity() - Integer.parseInt(itemQuantity);
                    item1.setQuantity(newQuantity);
                } catch (NumberFormatException excepcion) {
                    logger.error("Se presento un error con la cantidad" + excepcion.getMessage());
                    return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
            }
        }

        repository.save(item1);
        return new ResponseEntity<Inventory>(item1, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/deposit",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "deposit Quantity",
            notes = "deposit Quantity",
            response = Inventory.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Data Error")
        ,
        @ApiResponse(code = 404, message = "Item not found")
        ,
        @ApiResponse(code = 409, message = "Insufficient quantity of items")
    })
    public ResponseEntity<Inventory> deposit(
            @ApiParam(value = "id of Item", required = true)
            @RequestParam("itemId") String itemId,
            @ApiParam(value = "Quantity of Item", required = true)
            @RequestParam("itemQuantity") String itemQuantity) {
        Inventory item1 = null;

        if (null != itemId && !itemId.equals("")) {
            try {
                item1 = repository.findByItemId(Integer.parseInt(itemId));
            } catch (NumberFormatException excepcion) {
                logger.error("Se presento un error con el id" + excepcion.getMessage());
                return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
        }
        if (null == item1) {
            return new ResponseEntity<Inventory>(item1, HttpStatus.NOT_FOUND);
        } else {
            if (null != itemQuantity && !itemQuantity.equals("")) {
                try {
                    if (Integer.parseInt(itemQuantity) < 0) {
                        return new ResponseEntity<Inventory>(item1, HttpStatus.CONFLICT);
                    }
                    int newQuantity = item1.getQuantity() + Integer.parseInt(itemQuantity);
                    item1.setQuantity(newQuantity);
                } catch (NumberFormatException excepcion) {
                    logger.error("Se presento un error con la cantidad" + excepcion.getMessage());
                    return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<Inventory>(item1, HttpStatus.BAD_REQUEST);
            }
        }

        repository.save(item1);
        return new ResponseEntity<Inventory>(item1, HttpStatus.CREATED);
    }
}
