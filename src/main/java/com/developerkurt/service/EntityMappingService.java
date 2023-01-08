package com.developerkurt.service;


import com.developerkurt.model.EntityMapping;
import com.developerkurt.model.Platform;

import java.util.Optional;

public interface EntityMappingService {

    Optional<EntityMapping> getEntityMapping(Platform platform, String entityName);

}
