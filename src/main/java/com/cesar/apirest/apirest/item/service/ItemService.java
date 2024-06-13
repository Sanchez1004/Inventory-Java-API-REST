package com.cesar.apirest.apirest.item.service;

import com.cesar.apirest.apirest.item.entity.ItemEntity;
import java.util.List;

public interface ItemService {
    List<ItemEntity> getAllItems(String sortType);
    ItemEntity getItemById(Long id);
    ItemEntity createItem(ItemEntity item);
    ItemEntity updateItem(Long id, ItemEntity item);
    void deleteItemById(Long id);
}
