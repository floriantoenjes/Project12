package com.floriantoenjes.item;

import com.floriantoenjes.ingredient.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public void save(Item item) {
        itemRepository.save(item);
    }

    public Item findById(Long id) {
        return itemRepository.findOne(id);
    }

    public List<Item> findAll() {
        return (List<Item>) itemRepository.findAll();
    }
}
