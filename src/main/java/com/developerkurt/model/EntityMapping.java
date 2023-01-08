package com.developerkurt.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * It keeps the original name of an entity, and its fields' supported names for different platforms
 */
@Data
@Builder
public class EntityMapping {

    private String entityClassSimpleName;

    private Platform platform;

    //@OneToMany
    private Set<EntityMappingItem> items = new HashSet<>();

}
