package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.exception.ImageNotFoundException;
import com.win.itemsharingplatform.exception.InvalidFileException;
import com.win.itemsharingplatform.exception.ItemNotFoundException;
import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.repository.ItemRepository;
import com.win.itemsharingplatform.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final List<String> allowedFileExtensions = Arrays.asList("img", "jpeg", "jpg","image/jpeg","image/png","PNG", "png","application/octet-stream");;

    @Autowired
    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Item addItem(Item item){ return itemRepository.save(item);
    }
    public Item updateItem(Item item) {

        return itemRepository.save(item);
    }

    public void deleteItem(Long id)  { itemRepository.deleteById(id); }

    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }

    public List<Item> findItemsByOwnerId(Long ownerId){
        return itemRepository.findItemsByOwnerId(ownerId);
    }

    public Item findItemById(Long id){
        return itemRepository.findItemById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item by id (" + id + ") was not found"));
    }

    public List<Item> findItemsByHiddenIsFalseAndAvailableIsTrue(Long groupId){
        return itemRepository.findItemsByAvailableIsTrueAndGroupId(groupId);
    }

    public Item findItemByIdAndOwnerId(Long itemId, Long userId){
        return itemRepository.findItemByIdAndOwnerId(itemId, userId);
    }

    public void addAttachment(Long id, MultipartFile image) throws IOException, InvalidFileException {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("No such file"));
        byte[] bytes = image.getBytes();
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        if (image.getContentType() != null && !image.getContentType().trim().equals("")) {
            if (!allowedFileExtensions.contains(image.getContentType()))
                throw new InvalidFileException(image.getContentType());
        }
        BufferedImage originalImage = ImageIO.read(image.getInputStream());
        if(originalImage.getWidth()<250||originalImage.getHeight()<200){
            throw new InvalidFileException("Image resolution is too small");
        }

        if  (!allowedFileExtensions.contains(extension))
            throw new InvalidFileException(extension);

        item.setImage(bytes);
        item.setImageName(image.getOriginalFilename());
        itemRepository.save(item);
    }

    public byte[] getAttachment(Long id, String filename) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("No such item"));

        if (!item.getImageName().equals(filename)) {
            throw new ImageNotFoundException("No such image");
        }

        return item.getImage();
    }

    public void updateItemBookmarkCount(Long itemId, int value){
        itemRepository.updateItemBookmarkCount(itemId, value);
    }

    public List<Item> findItemsBookmarkedByUser(Long userId){
        return itemRepository.findItemsBookmarkedByUser(userId);
    }

    public void updateAvailableStatus(Long itemId, boolean available){
        itemRepository.updateAvailableStatus(itemId, available);
    }
}
