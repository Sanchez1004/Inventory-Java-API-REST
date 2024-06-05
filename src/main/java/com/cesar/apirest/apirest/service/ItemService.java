package com.cesar.apirest.apirest.service;

import com.cesar.apirest.apirest.entity.Item;
import com.cesar.apirest.apirest.repository.ItemRepository;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(Long id);
    Item createItem(Item item);
    Item updateItem(Long id, Item item);
    String deleteItemById(Long id);
}
