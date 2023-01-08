package com.developerkurt.repository;

import com.developerkurt.model.EntityMapping;
import com.developerkurt.model.EntityMappingItem;
import com.developerkurt.model.Platform;
import com.developerkurt.model.Product;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

/**
 * A mock EntityMapping repository
 */
public class EntityMappingRepository {


    public Optional<EntityMapping> findByPlatformAndEntityClassSimpleName(Platform platform, String entityName) {
        if(platform != null && entityName.equalsIgnoreCase(Product.class.getSimpleName())){
            return Optional.of(createMockEntityMapping(platform));
        }
        return Optional.empty();

    }

    /**
     * Creates a mock EntityMapping
     *
     * @param platform Platform of the EntityMapping and the prefix value of the mappings
     * @return Mock EntityMapping for the given platform
     */
    private EntityMapping createMockEntityMapping(Platform platform) {
        String prefix = platform.name().toLowerCase() + "_";

        return EntityMapping.builder()
                .entityClassSimpleName("Platform")
                .platform(platform)
                .items(new HashSet<>(Arrays.asList(
                        EntityMappingItem.builder()
                                .mappedFieldName(prefix + "id")
                                .originalFieldName("id")
                                .build(),
                        EntityMappingItem.builder()
                                .mappedFieldName(prefix + "name")
                                .originalFieldName("name")
                                .build(),
                        EntityMappingItem.builder()
                                .mappedFieldName(prefix + "price")
                                .originalFieldName("price")
                                .build(),
                        EntityMappingItem.builder()
                                .mappedFieldName(prefix + "category")
                                .originalFieldName("category")
                                .build()
                ))).build();

    }

}
