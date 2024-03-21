package com.bytedance.mossey.demo.common.json;

import com.bytedance.mossey.demo.common.exception.JSONSyntaxRuntimeException;
import com.bytedance.mossey.demo.common.json.thrift.ThriftSerializer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JacksonUtil {

    private static final SimpleModule module = new SimpleModule()
        .addSerializer(TBase.class, new ThriftSerializer());

    public static ObjectMapper objectMapper = new ObjectMapper()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .setSerializationInclusion(Include.NON_NULL)
        .registerModule(module);

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            log.error("", e);
            throw new JSONSyntaxRuntimeException("readValue error ", e);
        }
    }

    public static <T> T readValue(String content, JavaType valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            log.error("", e);
            throw new JSONSyntaxRuntimeException("readValue error ", e);
        }
    }

    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            log.error("", e);
            throw new JSONSyntaxRuntimeException("readValue error ", e);
        }
    }

    public static <T> List<T> readList(String content, Class<T> valueType) {
        JavaType javaType = JacksonUtil.objectMapper
            .getTypeFactory().constructCollectionType(List.class, valueType);
        try {
            return JacksonUtil.objectMapper.readValue(content, javaType);
        } catch (IOException e) {
            log.error("", e);
            throw new JSONSyntaxRuntimeException("readList error ", e);
        }
    }

    public static <T> Map<String, T> readHashMap(String content, Class<T> valueType) {
        JavaType javaType = objectMapper.getTypeFactory()
            .constructParametricType(HashMap.class, String.class, valueType);
        try {
            return JacksonUtil.objectMapper.readValue(content, javaType);
        } catch (IOException e) {
            log.error("", e);
            throw new JSONSyntaxRuntimeException("readList error ", e);
        }
    }

    public static String writeValueAsString(Object obj) {

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.error("", e);
            throw new JSONSyntaxRuntimeException("writeString error ", e);
        }
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
