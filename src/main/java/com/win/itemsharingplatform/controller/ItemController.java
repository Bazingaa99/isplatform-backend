package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/isp/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) { this.itemService = itemService; }

    @GetMapping("/all/")
    public ResponseEntity<List<Item>> getAllItems () {
        List<Item> items = itemService.findAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/find//{id}")
    public ResponseEntity<Item> getItemById (@PathVariable("id") Long id) {
        Item item  = itemService.findItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/add/")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item newItem = itemService.addItem(item);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @GetMapping("/find/group/{group_id}")
    public ResponseEntity<List<Item>> getItemByGroupId (@PathVariable("group_id") Long groupId) {
        List<Item> item  = itemService.findItemsByGroupId(groupId);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
