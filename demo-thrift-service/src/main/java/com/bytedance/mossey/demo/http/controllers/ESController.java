package com.bytedance.mossey.demo.http.controllers;

import com.bytedance.mossey.demo.common.json.JacksonUtil;
import com.bytedance.mossey.demo.dal.es.ESClient;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * ESController
 *
 * @author xieao.mossey
 * @version 2024/03/25 19:28
 **/
@Slf4j
@RestController
@RequestMapping("/es")
public class ESController {
    private final ESClient esClient;

    public ESController(ESClient esClient) {
        this.esClient = esClient;
    }

    @GetMapping(value = "index/create")
    public String createIndex(@RequestParam("name") String name) throws IOException {
        return esClient.createIndex(name);
    }

    @GetMapping(value = "index/get")
    public Set<String> getIndex(@RequestParam("name") String name) throws IOException {
        return esClient.getIndex(name);
    }

    @GetMapping(value = "doc/create")
    public int createDoc(@RequestParam("index") String index, @RequestParam("id") String id) throws IOException {
        Map<String, String> docMap = Maps.newHashMap();
        docMap.put("source", "aeolus");
        docMap.put("type", "hive");
        return esClient.insertDoc(index, id, JacksonUtil.writeValueAsString(docMap));
    }

    @GetMapping(value = "doc/update")
    public int updateDoc(@RequestParam("index") String index, @RequestParam("id") String id) throws IOException {
        Map<String, Object> docMap = Maps.newHashMap();
        docMap.put("source", "dorado");
        docMap.put("type", "clickhouse");
        return esClient.updateDoc(index, id, docMap);
    }
}
