package com.floriantoenjes.item;

import com.floriantoenjes.recipe.Recipe;
import com.floriantoenjes.recipe.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public Item findById(Long id) {
        return itemRepository.findOne(id);
    }

    public List<Item> findAll() {
        return (List<Item>) itemRepository.findAll();
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

}
