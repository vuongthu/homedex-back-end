package com.homedex.households;

import com.homedex.dao.HouseholdDao;
import com.homedex.dao.entities.HouseholdEntity;
import com.homedex.households.models.Household;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class HouseholdsService {
    private final HouseholdDao householdDao;

    public HouseholdsService(HouseholdDao householdDao) {
        this.householdDao = householdDao;
    }

    public Household createHousehold(String name) {
        HouseholdEntity householdEntity = householdDao.save(HouseholdEntity.builder().name(name).build());
        return mapToUser(householdEntity);
    }

    public List<Household> getHouseholds() {
        return StreamSupport.stream(householdDao.findAll().spliterator(), true)
                .map(this::mapToUser).toList();
    }

    public Household getHouseholdById(UUID householdId) {
        HouseholdEntity entity = householdDao.findById(householdId).orElseThrow();
        return mapToUser(entity);
    }

    private Household mapToUser(HouseholdEntity entity) {
        return new Household(entity.getId(), entity.getName());
    }

    public void deleteHousehold(UUID householdId) {
        householdDao.deleteById(householdId);
    }
}
