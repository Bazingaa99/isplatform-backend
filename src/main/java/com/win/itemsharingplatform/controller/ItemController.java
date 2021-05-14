package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.exception.DoesNotExistException;
import com.win.itemsharingplatform.exception.DuplicateBookmarkException;
import com.win.itemsharingplatform.exception.InvalidFileException;
import com.win.itemsharingplatform.exception.UserIsOwnerException;
import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UserHasBookmarks;
import com.win.itemsharingplatform.model.request.ItemBookmarkRequest;
import com.win.itemsharingplatform.model.request.ItemRequest;
import com.win.itemsharingplatform.service.ItemService;
import com.win.itemsharingplatform.service.RequestService;
import com.win.itemsharingplatform.service.UserHasBookmarksService;
import com.win.itemsharingplatform.service.UserService;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/isp/items")
@Data
public class ItemController {
    private final ItemService itemService;
    private final UserService userService;
    private final UserHasBookmarksService userHasBookmarksService;
    private final RequestService requestService;

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
    @PostMapping("/{id}/image")
    public ResponseEntity<HttpStatus> addImageToItem(@PathVariable("id") Long id, @RequestParam("file") MultipartFile image) throws IOException, InvalidFileException {
        itemService.addAttachment(id, image);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/{id}/image/{filename}")
    public ResponseEntity fetchImage(@PathVariable("id") Long id, @PathVariable("filename") String filename){
        byte[] data = itemService.getAttachment(id, filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", filename)).body(data);
    }

    @PutMapping("/update/")
    public void updateItem(@Valid @RequestBody ItemRequest itemRequest) {
        // checking if the item owner is the same person as the one that tries to update it
        Item oldItem = itemService.findItemById(itemRequest.getItem().getId());
        if(oldItem.getOwner() == userService.getUserByEmail(itemRequest.getEmail())) {
            itemRequest.getItem().setOwner(itemService.findItemById(itemRequest.getItem().getId()).getOwner());
            itemRequest.getItem().setImage(oldItem.getImage());
            itemRequest.getItem().setImageName(oldItem.getImageName());
            itemService.updateItem(itemRequest.getItem());
        }
    }

    @DeleteMapping("/delete/{itemId}&{email}")
    public void deleteItem(@PathVariable("itemId") Long itemId, @PathVariable("email") String userEmail) {
        // checking if the item owner is the same person as the one that tries to update it
        if(itemService.findItemById(itemId).getOwner()== userService.getUserByEmail(userEmail)) {
            itemService.deleteItem(itemId);
            userHasBookmarksService.deleteUserHasBookmarksByItemId(itemId);
            requestService.deleteRequestsByItemId(itemId);
        }
    }

    @GetMapping("/find/group/{group_id}")
    public ResponseEntity<List<Item>> getItemByGroupId (@PathVariable("group_id") Long groupId) {
        List<Item> item  = itemService.findItemsByHiddenIsFalseAndAvailableIsTrue(groupId);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/bookmark")
    public ResponseEntity<UserHasBookmarks> addUserHasBookmarks(@Valid @RequestBody ItemBookmarkRequest itemBookmarkRequest) throws UserIsOwnerException {
        Item item = itemService.findItemById(itemBookmarkRequest.getItemId());
        Long userId = userService.getUserByEmail(itemBookmarkRequest.getEmail()).getId();
        UserHasBookmarks userHasBookmarks  = userHasBookmarksService.findUserHasBookmarksByUserIdAndItemId(userId, itemBookmarkRequest.getItemId());
        if(userHasBookmarks == null){
            if(!item.getOwner().getId().equals(userId)){
                itemService.updateItemBookmarkCount(itemBookmarkRequest.getItemId(), 1);
                UserHasBookmarks uhb = userHasBookmarksService.addUserHasBookmarks(new UserHasBookmarks(new User(userId), new Item(itemBookmarkRequest.getItemId())));
                return new ResponseEntity<>(uhb, HttpStatus.CREATED);
            }else{
                throw new UserIsOwnerException("You can't bookmark your own item.");
            }
        }else{
            throw new DuplicateBookmarkException("You have already bookmarked this item.");
        }
    }

    @DeleteMapping("/delete/bookmark/{email}/{item_id}")
    public void deleteUserHasBookmarks(@PathVariable("email") String email, @PathVariable("item_id") Long itemId){
        Long userId = userService.getUserByEmail(email).getId();
        UserHasBookmarks userHasBookmarks = userHasBookmarksService.findUserHasBookmarksByUserIdAndItemId(userId, itemId);
        if(userHasBookmarks != null){
            itemService.updateItemBookmarkCount(userHasBookmarks.getItem().getId(), -1);
            userHasBookmarksService.deleteUserHasBookmarks(userHasBookmarks);
        }else{
            throw new DoesNotExistException("You haven't bookmarked this item.");
        }

    }

    @GetMapping("/find/bookmarks/user/{email}")
    public ResponseEntity<List<Item>> getUserHasBookmarksByUserId(@PathVariable("email") String email){
        Long userId = userService.getUserByEmail(email).getId();
        List<Item> items = itemService.findItemsBookmarkedByUser(userId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("check/bookmark/{email}/{itemId}")
    public ResponseEntity<Boolean> existsUserHasBookmarksByUserIdAndItemId(@PathVariable("email") String email, @PathVariable("itemId") Long itemId){
        Long userId = userService.getUserByEmail(email).getId();
        Boolean bookmarkExists = userHasBookmarksService.existsUserHasBookmarksByUserIdAndItemId(userId, itemId);
        return new ResponseEntity<>(bookmarkExists, HttpStatus.OK);
    }

    @GetMapping("/find/user/items/{email}&{userId}")
    public ResponseEntity<List<Item>> checkUserIsGroupOwner(@PathVariable("email") String email, @PathVariable("userId") String userId){
        Boolean userIsItemsOwner = (userService.getUserByEmail(email).getId().toString().equals(userId));
        List<Item> userItems = itemService.findUserItems(parseLong(userId), userIsItemsOwner);
        return new ResponseEntity<>(userItems, HttpStatus.OK);
    }
}
