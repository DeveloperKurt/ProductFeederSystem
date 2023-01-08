package com.developerkurt.feeder;

import com.developerkurt.model.Platform;
import com.developerkurt.service.EntityMappingServiceImpl;
import lombok.val;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSONFeeder extends BaseFeeder {

    public JSONFeeder() {
        super(new EntityMappingServiceImpl());
    }

    @Override
    public String getContent(Platform platform, Object... entities) {
        val mappedFieldValueMap = createFieldValueMap(platform, entities);

        JSONArray jsonArray = new JSONArray();

        for (HashMap<String, Object> entityFieldNameValueMap: mappedFieldValueMap) {
            val jsonObject = new JSONObject();

            for (Map.Entry<String, Object> entry : entityFieldNameValueMap.entrySet()) {
                if (entry.getValue().getClass().isEnum()) {
                    jsonObject.put(entry.getKey(), entry.getValue().toString());
                } else {
                    jsonObject.put(entry.getKey(), entry.getValue());
                }
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString(4);
    }
}
