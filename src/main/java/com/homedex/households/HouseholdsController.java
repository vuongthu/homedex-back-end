package com.homedex.households;

import com.homedex.households.models.Household;
import com.homedex.households.models.HouseholdRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Household> createHousehold(@RequestBody HouseholdRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(householdsService.createHousehold(request.name()));
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
    public ResponseEntity<Void> patchHouseholdName(@RequestBody HouseholdRequest request, @PathVariable("household-id") UUID householdId) {
        householdsService.updateHouseholdName(request.name(), householdId);
        return ResponseEntity.noContent().build();
    }
}

