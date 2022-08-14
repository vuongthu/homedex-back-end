package com.homedex.households;

import com.homedex.categories.models.Item;
import com.homedex.households.models.Household;
import com.homedex.households.models.HouseholdRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/households")
public class HouseholdsController {
    private final HouseholdsService householdsService;

    public HouseholdsController(HouseholdsService householdsService) {
        this.householdsService = householdsService;
    }

    @PostMapping
    public ResponseEntity<Household> createHousehold(@RequestBody HouseholdRequest request, @RequestParam("user-id") UUID userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(householdsService.createHousehold(request.name(), request.image(), userId));
    }

    @GetMapping
    public ResponseEntity<List<Household>> getHouseholds() {
        return ResponseEntity.status(HttpStatus.OK).body(householdsService.getHouseholds());
    }

    @GetMapping("{household-id}")
    public ResponseEntity<Household> getHousehold(@PathVariable("household-id") UUID householdId) {
        return ResponseEntity.status(HttpStatus.OK).body(householdsService.getHouseholdById(householdId));
    }

    @DeleteMapping("{household-id}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable("household-id") UUID householdId) {
        householdsService.deleteHousehold(householdId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{household-id}")
    public ResponseEntity<Household> patchHousehold(@RequestBody HouseholdRequest request, @PathVariable("household-id") UUID householdId) {
        householdsService.updateHousehold(request.name(), request.image(), householdId);
        return ResponseEntity.status(HttpStatus.OK).body(householdsService.getHouseholdById(householdId));
    }

    @GetMapping("{household-id}/items")
    public ResponseEntity<List<Item>> getLikedItemsByHousehold(@PathVariable("household-id") UUID householdId, @RequestParam("action") String action) {
        List<Item> items;
        if ("like".equalsIgnoreCase(action)) {
            items = householdsService.getLikedItemsByHousehold(householdId);
        } else if ("purchase".equalsIgnoreCase(action)) {
            items = householdsService.getItemsToPurchaseByHousehold(householdId);
        } else {
            items = Collections.emptyList();
        }
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    // Household to User Mappings
    @GetMapping("/mappings")
    public ResponseEntity<List<Household>> getHouseholdsForUser(@RequestParam("user-id") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(householdsService.getHouseholdsForUser(userId));
    }
}

