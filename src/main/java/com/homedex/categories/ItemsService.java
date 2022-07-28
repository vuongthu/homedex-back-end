package com.homedex.categories;

import com.homedex.categories.models.Item;
import com.homedex.dao.CategoryDao;
import com.homedex.dao.ItemDao;
import com.homedex.dao.entities.CategoryEntity;
import com.homedex.dao.entities.ItemEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    public Item createItem(String name, String measurement, String brand, String addInfo, LocalDateTime expiration, UUID categoryId) {
        CategoryEntity entity = categoryDao.findById(categoryId).orElseThrow();
        ItemEntity itemEntity = ItemEntity.builder().name(name).measurement(measurement).brand(brand).addInfo(addInfo).expiration(expiration).categoryEntity(entity).build();
        return mapToItem(itemDao.save(itemEntity));
    }

    private Item mapToItem(ItemEntity entity) {
        return new Item(entity.getId(), entity.getName(), entity.getMeasurement(), entity.getBrand(), entity.getAddInfo(), entity.getExpiration());
    }

}
