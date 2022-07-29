package com.homedex.categories;

import com.homedex.categories.models.Category;
import com.homedex.categories.models.CategoryRequest;
import com.homedex.categories.models.Item;
import com.homedex.categories.models.ItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private static final String HOUSEHOLD_ID_PARAM = "household-id";
    private final CategoriesService categoriesService;
    private final ItemsService itemsService;

    public CategoriesController(CategoriesService categoriesService, ItemsService itemsService) {
        this.categoriesService = categoriesService;
        this.itemsService = itemsService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody CategoryRequest request,
            @RequestParam(HOUSEHOLD_ID_PARAM) UUID householdId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriesService.createCategory(request.name(), householdId));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(@RequestParam(HOUSEHOLD_ID_PARAM) UUID householdId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriesService.getCategories(householdId));
    }

    @GetMapping("{category-id}")
    public ResponseEntity<Category> getCategory(@PathVariable("category-id") UUID categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriesService.getCategory(categoryId));
    }

    @DeleteMapping("{category-id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("category-id") UUID categoryId) {
        categoriesService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{category-id}")
    public ResponseEntity<Void> updateCategoryName(@RequestBody CategoryRequest request, @PathVariable("category-id") UUID categoryId) {
        categoriesService.updateCategoryName(request.name(), categoryId);
        return ResponseEntity.noContent().build();
    }

    // Items

    @PostMapping("{category-id}/items")
    public ResponseEntity<Item> createItem(@RequestBody ItemRequest request, @PathVariable("category-id") UUID categoryId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemsService.createItem(request.name(), request.measurement(), request.brand(), request.addInfo(), request.expiration(), categoryId));
    }

    @GetMapping("{category-id}/items")
    public ResponseEntity<List<Item>> getItems(@PathVariable("category-id") UUID categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemsService.getItems(categoryId));
    }
}
