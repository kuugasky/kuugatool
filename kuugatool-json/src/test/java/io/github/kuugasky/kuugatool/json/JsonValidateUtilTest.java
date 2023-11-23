package io.github.kuugasky.kuugatool.json;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonValidateUtilTest {

    String json = "{\"name\":\"kuuga1\",\"sex\":1}";
    String jsonArray = "[{\"name\":\"kuuga1\",\"sex\":1},{\"name\":\"kuuga2\",\"sex\":2},{\"name\":\"kuuga3\",\"sex\":3}]";

    @Test
    void isValid() {
        assertTrue(JsonValidateUtil.isValid(json));
        assertTrue(JsonValidateUtil.isValid(jsonArray));
    }

    @Test
    void isValidBytes() {
        assertTrue(JsonValidateUtil.isValid(json.getBytes(StandardCharsets.UTF_8)));
        assertTrue(JsonValidateUtil.isValid(jsonArray.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void isValidArray() {
        assertFalse(JsonValidateUtil.isValidArray(json));
        assertTrue(JsonValidateUtil.isValidArray(jsonArray));
    }

    @Test
    void testIsValidArray() {
        assertFalse(JsonValidateUtil.isValidArray(json.getBytes(StandardCharsets.UTF_8)));
        assertTrue(JsonValidateUtil.isValidArray(jsonArray.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void isValidObject() {
        assertTrue(JsonValidateUtil.isValidObject(json));
        assertFalse(JsonValidateUtil.isValidObject(jsonArray));
    }

    @Test
    void testIsValidObject() {
        assertTrue(JsonValidateUtil.isValidObject(json.getBytes(StandardCharsets.UTF_8)));
        assertFalse(JsonValidateUtil.isValidObject(jsonArray.getBytes(StandardCharsets.UTF_8)));
    }

}