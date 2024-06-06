package com.cesar.apirest.apirest.service;

import com.cesar.apirest.apirest.entity.ItemEntity;
import java.util.List;

public interface ItemService {
    List<ItemEntity> getAllItems();
    ItemEntity getItemById(Long id);
    ItemEntity createItem(ItemEntity item);
    ItemEntity updateItem(Long id, ItemEntity item);
    boolean deleteItemById(Long id);
}
