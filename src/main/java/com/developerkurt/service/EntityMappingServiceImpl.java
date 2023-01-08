package com.developerkurt.service;

import com.developerkurt.model.EntityMapping;
import com.developerkurt.model.Platform;
import com.developerkurt.repository.EntityMappingRepository;

import java.util.Optional;

/**
 * When an entity is going to be extracted into a specific format for the feeder system, the external systems'
 * format for the fields' name might vary; thus, this service helps managing field name mappings for different platforms.
 */
public class EntityMappingServiceImpl implements EntityMappingService{


    private EntityMappingRepository entityMappingRepository = new EntityMappingRepository();

    @Override
    public Optional<EntityMapping> getEntityMapping(Platform platform, String entityName){
        return entityMappingRepository.findByPlatformAndEntityClassSimpleName(platform, entityName);
    }

}
