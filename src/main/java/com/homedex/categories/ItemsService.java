package com.homedex.categories;

import com.homedex.categories.models.Item;
import com.homedex.dao.CategoryDao;
import com.homedex.dao.ItemDao;
import com.homedex.dao.entities.CategoryEntity;
import com.homedex.dao.entities.ItemEntity;
import com.homedex.dao.entities.Measurement;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ItemsService {
    private final CategoryDao categoryDao;
    private final ItemDao itemDao;

    public ItemsService(CategoryDao categoryDao, ItemDao itemDao) {
        this.categoryDao = categoryDao;
        this.itemDao = itemDao;
    }

    public Item createItem(String name, String measurement, String brand, String addInfo, LocalDateTime expiration, Long unit, UUID categoryId) {
        CategoryEntity entity = categoryDao.findById(categoryId).orElseThrow();
        ItemEntity itemEntity = ItemEntity.builder().name(name).measurement(Measurement.valueOf(measurement.trim().toUpperCase())).brand(brand).addInfo(addInfo).expiration(expiration).categoryEntity(entity).unit(unit).build();
        return mapToItem(itemDao.save(itemEntity));
    }

    public Item updateItem(String name, String measurement, String brand, String addInfo, LocalDateTime expiration, Long unit, UUID itemId) {
        ItemEntity itemEntity = itemDao.findById(itemId).orElseThrow();
        itemEntity.setName(name);
        itemEntity.setMeasurement(Measurement.valueOf(measurement.trim().toUpperCase()));
        itemEntity.setBrand(brand);
        itemEntity.setAddInfo(addInfo);
        itemEntity.setExpiration(expiration);
        itemEntity.setUnit(unit);
        return mapToItem(itemDao.save(itemEntity));
    }

    private Item mapToItem(ItemEntity entity) {
        return new Item(entity.getId(), entity.getName(), entity.getMeasurement().toString(), entity.getBrand(), entity.getAddInfo(), entity.getExpiration(), entity.getUnit());
    }

    public List<Item> getItems(UUID categoryId) {
        return itemDao.findAllByCategoryEntityId(categoryId).stream()
                .map(this::mapToItem)
                .toList();
    }

    public void deleteItem(UUID itemId) {
        itemDao.deleteById(itemId);
    }
}
