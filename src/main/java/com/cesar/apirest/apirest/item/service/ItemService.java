package com.cesar.apirest.apirest.item.service;

import com.cesar.apirest.apirest.item.dto.ItemRequest;
import com.cesar.apirest.apirest.item.entity.ItemEntity;

import java.util.List;

public interface ItemService {
    List<ItemRequest> getAllItems(String sortType);
    List<String> getListOfItemNames();
    ItemRequest findItemById(Long id);
    ItemRequest createItem(ItemRequest item);
    ItemRequest updateItem(Long id, ItemRequest item);
    ItemEntity getItemByName(String name);
    void deleteItemById(Long id);
}
