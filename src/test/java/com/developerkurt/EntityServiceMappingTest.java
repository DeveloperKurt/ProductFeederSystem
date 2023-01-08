package com.developerkurt;

import com.developerkurt.feeder.JSONFeeder;
import com.developerkurt.feeder.XMLFeeder;
import com.developerkurt.model.Platform;
import com.developerkurt.model.Product;
import lombok.SneakyThrows;
import lombok.val;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;

public class EntityServiceMappingTest {

    private static final String REDDIT_PREFIX = "reddit_";
    private static final String TWITTER_PREFIX = "twitter_";
    private static final String FACEBOOK_PREFIX = "facebook_";


    @ParameterizedTest
    @EnumSource(value = Platform.class, names = {"REDDIT", "FACEBOOK", "TWITTER"}, mode = EnumSource.Mode.MATCH_ANY)
    public void GivenDifferentPlatforms_WhenJsonFeederIsCalled_ThenResultsMatchWithPlatformMappings(Platform platform) {
        //Given
        JSONFeeder jsonFeeder = new JSONFeeder();
        val products = new Product[]{Product.createMockProduct(), Product.createMockProduct()};

        //When
        val res = jsonFeeder.getContent(platform, (Object[]) products);
        System.out.println("Result:\n" + res);

        //Then
        val resJson = new JSONArray(res);
        performJsonContainsKeyAssertions(getPrefixForPlatform(platform), resJson.getJSONObject(0));
    }


    @SneakyThrows
    @ParameterizedTest
    @EnumSource(value = Platform.class, names = {"REDDIT", "FACEBOOK", "TWITTER"}, mode = EnumSource.Mode.MATCH_ANY)
    public void GivenDifferentPlatforms_WhenXMLFeederIsCalled_ThenResultsMatchWithPlatformMappings(Platform platform) {
        //Given
        XMLFeeder xmlFeeder = new XMLFeeder();
        val products = new Product[]{Product.createMockProduct(), Product.createMockProduct()};

        //When
        val res = xmlFeeder.getContent(platform, (Object[]) products);
        System.out.println("Result:\n" + res);

        //Then
        val resXml = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(res)));

        performXmlContainsKeyAssertions(getPrefixForPlatform(platform),
                resXml.getDocumentElement()
                        .getFirstChild()
                        .getChildNodes());

    }


    private String getPrefixForPlatform(Platform platform) {
        switch (platform) {
            case REDDIT:
                return REDDIT_PREFIX;
            case TWITTER:
                return TWITTER_PREFIX;
            case FACEBOOK:
                return FACEBOOK_PREFIX;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void performXmlContainsKeyAssertions(String prefix, NodeList nodeList) {

        val expectedTagNames = new HashSet<>(Arrays.asList(prefix + "price", prefix + "category", prefix + "name", prefix + "id"));

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element xmlElement = (Element) nodeList.item(i);
            if (expectedTagNames.contains(xmlElement.getTagName())) {
                expectedTagNames.remove(xmlElement.getTagName());
            }
        }
        Assertions.assertTrue(expectedTagNames.isEmpty());

    }

    private void performJsonContainsKeyAssertions(String prefix, JSONObject jsonObject) {
        Assertions.assertTrue(jsonObject.has(prefix + "price"));
        Assertions.assertTrue(jsonObject.has(prefix + "category"));
        Assertions.assertTrue(jsonObject.has(prefix + "name"));
        Assertions.assertTrue(jsonObject.has(prefix + "id"));
    }
}
