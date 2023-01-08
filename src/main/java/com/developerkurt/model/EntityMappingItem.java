package com.developerkurt.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a table that holds one of {@link EntityMapping}'s field and its mapping
 */
@Data
@Builder
public class EntityMappingItem {

    private String originalFieldName;

    private String mappedFieldName;

}
