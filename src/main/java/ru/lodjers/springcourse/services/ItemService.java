package ru.lodjers.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lodjers.springcourse.models.Item;
import ru.lodjers.springcourse.models.Person;
import ru.lodjers.springcourse.repositories.ItemsRepository;

import java.util.List;

@Service
@Transactional
public class ItemService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }
    public List<Item> findByItemName(String itemName) {
        return itemsRepository.findByItemName(itemName);
    }
    public List<Item> findByOwner(Person owner) {
        return itemsRepository.findByOwner(owner);
    }

}
