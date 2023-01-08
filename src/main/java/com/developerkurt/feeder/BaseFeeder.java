package com.developerkurt.feeder;

import com.developerkurt.model.EntityMappingItem;
import com.developerkurt.model.Platform;
import com.developerkurt.service.EntityMappingService;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


abstract class BaseFeeder {


    protected EntityMappingService entityMappingService;

    protected BaseFeeder(EntityMappingService entityMappingService) {
        this.entityMappingService = entityMappingService;
    }

    abstract public String getContent(Platform platform, Object... entities);

    /**
     * Creates a list of  HashMap with the Pairs of mappedFieldName and value so that it can be implemented easily for various
     * file types. It was needed because otherwise for every supported file type, we would have to do the same logic of getting the mapped
     * field name again and again.
     * <p>
     * Example:
     * <code>
     * original name of the field : field
     * platform name of the field : FIELD_1
     * Value: 123
     * <p>
     * Map entry value: <FIELD_1, 123>
     * </code>
     *
     * @return Every mapped field name for the platform, and their values
     * (if no mapping is found, it uses the default Java field names)
     */
    @SneakyThrows
    protected List<HashMap<String, Object>> createFieldValueMap(Platform platform, Object... entities) {

        val maps = new ArrayList<HashMap<String, Object>>();
        for (Object entity : entities) {
            HashMap<String, Object> map = new HashMap<>();
            if (entity != null) {

                val optionalMapping = entityMappingService.getEntityMapping(platform, entity.getClass().getSimpleName());
                if (optionalMapping.isPresent()) {
                    for (EntityMappingItem item : optionalMapping.get().getItems()) {

                        val getFieldMethod = entity.getClass().getDeclaredMethod("get" + StringUtils.capitalize(item.getOriginalFieldName()));
                        map.put(item.getMappedFieldName(), getFieldMethod.invoke(entity));
                    }
                } else {
                    //TODO currently it doesn't support the inherited fields, but it can be implemented if it is requested
                    for (val field : entity.getClass().getDeclaredFields()) {

                        val getFieldMethod = entity.getClass().getDeclaredMethod("get" + StringUtils.capitalize(field.getName()));
                        map.put(field.getName(), getFieldMethod.invoke(entity));
                    }
                }
                maps.add(map);
            }

        }
        return maps;
    }

    public void writeToFile(String path, Platform platform, String entitySimpleName) throws IOException {

        val file = new File(path);
        file.createNewFile();

        val fileOutputSteam = new FileOutputStream(file);
        try {
            fileOutputSteam.write(getContent(platform, entitySimpleName).getBytes());
        } catch (IOException e) {
            fileOutputSteam.close();
            throw e;
        }
    }


}
