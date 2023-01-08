package com.developerkurt.feeder;

import com.developerkurt.model.Platform;
import com.developerkurt.service.EntityMappingServiceImpl;
import lombok.SneakyThrows;
import lombok.val;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class XMLFeeder extends BaseFeeder {

    public XMLFeeder() {
        super(new EntityMappingServiceImpl());
    }

    @SneakyThrows
    @Override
    public String getContent(Platform platform, Object... entities) {
        val mappedFieldValueMap = createFieldValueMap(platform, entities);

        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        Element root = doc.createElement("root");
        doc.appendChild(root);

        for (HashMap<String, Object> map : mappedFieldValueMap) {
            Element element = doc.createElement("product");
            root.appendChild(element);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Element child = doc.createElement(entry.getKey());
                child.appendChild(doc.createTextNode(entry.getValue().toString()));
                element.appendChild(child);
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        StringWriter sw = new StringWriter();

        StreamResult result = new StreamResult(sw);
        transformer.transform(new DOMSource(doc), result);

        return sw.toString();
    }
}
