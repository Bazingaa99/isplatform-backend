package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.exception.UserNotFoundException;
import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item addItem(Item item){
        item.setName("Petras");
        return itemRepository.save(item);
    }

    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }

    public Item updateItem(Item item){
        return itemRepository.save(item);
    }

    public Item findItemById(Long id){
        return itemRepository.findItemById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteItem(Long id){
        itemRepository.deleteItemById(id);
    }
}
