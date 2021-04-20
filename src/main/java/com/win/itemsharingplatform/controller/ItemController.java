package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.request.ItemRequest;
import com.win.itemsharingplatform.service.ItemService;
import com.win.itemsharingplatform.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/isp/items")
public class ItemController {
    private final ItemService itemService;
    private final UserService userService;

    public ItemController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping("/all/")
    public ResponseEntity<List<Item>> getAllItems () {
        List<Item> items = itemService.findAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Item> getItemById (@PathVariable("id") Long id) {
        Item item  = itemService.findItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/add/")
    public ResponseEntity<Item> addItem(@Valid @RequestBody ItemRequest itemRequest) {
        Long userId = userService.getUserByEmail(itemRequest.getEmail()).getId();
        itemRequest.getItem().setOwner(new User(userId));
        Item newItem = itemService.addItem(itemRequest.getItem());
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @PutMapping("/update/")
    public void updateItem(@Valid @RequestBody ItemRequest itemRequest) {
        // checking if the item owner is the same person as the one that tries to update it
        if(itemService.findItemById(itemRequest.getItem().getId()).getOwner() == userService.getUserByEmail(itemRequest.getEmail())) {
            itemRequest.getItem().setOwner(itemService.findItemById(itemRequest.getItem().getId()).getOwner());
            itemService.updateItem(itemRequest.getItem());
        }
    }

    @DeleteMapping("/delete/{itemId}&{email}")
    public void deleteItem(@PathVariable("itemId") Long itemId, @PathVariable("email") String userEmail) {
        // checking if the item owner is the same person as the one that tries to update it
        if(itemService.findItemById(itemId).getOwner()== userService.getUserByEmail(userEmail)) {
            itemService.deleteItem(itemId);
        }
    }

    @GetMapping("/find/group/{group_id}")
    public ResponseEntity<List<Item>> getItemByGroupId (@PathVariable("group_id") Long groupId) {
        List<Item> item  = itemService.findItemsByGroupIdAndNotRespondedOrDeclined(groupId);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
