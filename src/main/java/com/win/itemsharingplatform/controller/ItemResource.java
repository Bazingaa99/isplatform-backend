package com.win.itemsharingplatform.controller;

<<<<<<< HEAD:src/main/java/com/win/itemsharingplatform/resources/ItemResource.java
package com.win.itemsharingplatform.resources;
=======
package com.win.itemsharingplatform.controller;
>>>>>>> ecf4826b6ea7b40db39a4ce38372aa5f66434052:src/main/java/com/win/itemsharingplatform/controller/ItemSharingPlatformController.java

import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/isp")
public class ItemResource {
    private final ItemService itemService;

    public ItemResource(ItemService itemService) { this.itemService = itemService; }

    @GetMapping("/all/items")
    public ResponseEntity<List<Item>> getAllItems () {
        List<Item> items = itemService.findAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/find/item/{id}")
    public ResponseEntity<Item> getItemById (@PathVariable("id") Long id) {
        Item item  = itemService.findItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/add/item")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        //User owner = userService.findUserById(
        Item newItem = itemService.addItem(item);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @GetMapping("/find/group/items/{group_id}")
    public ResponseEntity<List<Item>> getItemByGroupId (@PathVariable("group_id") Long groupId) {
        List<Item> item  = itemService.findItemsByGroupId(groupId);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
