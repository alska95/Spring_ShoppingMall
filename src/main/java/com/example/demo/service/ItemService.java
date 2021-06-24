package com.example.demo.service;


import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, int price  , int stock , String name){
        Item findItem = itemRepository.findById(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stock);

    }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findById(itemId);
    }
}
