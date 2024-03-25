package com.bytedance.mossey.demo.dal.es;

import com.bytedance.mossey.demo.common.json.JacksonUtil;
import com.bytedance.mossey.demo.dal.config.ESProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * ESClient ES 客户端
 *
 * @author xieao.mossey
 * @version 2024/03/25 16:16
 **/
@Slf4j
@Service
@EnableConfigurationProperties(ESProperties.class)
public class ESClient {
    final ESProperties esProperties;

    private RestHighLevelClient client;

    public ESClient(ESProperties esProperties) {
        this.esProperties = esProperties;
    }

    @PostConstruct
    public void init() {
        RestClientBuilder builder =
            RestClient.builder(
                    new HttpHost(esProperties.getHost(), esProperties.getPort(), HttpHost.DEFAULT_SCHEME_NAME))
                //            RestClient.builder(psm, NodeTypeEnum.DATA)
                .setRequestConfigCallback(
                    requestConfigBuilder -> requestConfigBuilder.setConnectionRequestTimeout(60000)
                        .setSocketTimeout(300000)
                        .setConnectTimeout(10000));

        client = new RestHighLevelClient(builder);
    }

    @PreDestroy
    public void destroy() {
        if (client != null) {
            try {
                client.close();
                log.info("ESClient successfully exited");
            } catch (IOException e) {
                log.error("ESClient exit failed", e);
            }
        }
    }

    /**
     * 创建 Index
     *
     * @param index
     * @return
     * @throws IOException
     */
    public String createIndex(String index) throws IOException {
        CreateIndexRequest createRequest = new CreateIndexRequest(index);
        CreateIndexResponse createResponse = client.indices().create(createRequest, RequestOptions.DEFAULT);
        if (createResponse.isAcknowledged()) {
            log.info("ESClient create index {} success", index);
        } else {
            log.error("ESClient create index {} failed", index);
        }

        return index;
    }

    public Set<String> getIndex(String index) throws IOException {
        GetIndexRequest getRequest = new GetIndexRequest(index);
        GetIndexResponse getResponse = client.indices().get(getRequest, DEFAULT);
        return getResponse.getSettings().keySet();
    }

    public boolean existIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return client.indices().exists(request, DEFAULT);
    }

    public String deleteIndex(String index) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, DEFAULT);
        if (response.isAcknowledged()) {
            log.info("delete index {} success", index);
        } else {
            log.error("delete index {} error", index);
        }
        return index;
    }

    public int insertDoc(String indexName, String id, String doc) throws IOException {
        int cnt = 0;
        IndexRequest indexRequest =
            new IndexRequest()
                .index(indexName)
                .id(id)
                .source(doc, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, DEFAULT);
        if (response.getResult() == DocWriteResponse.Result.CREATED) {
            cnt += 1;
            log.info("insert doc {} success, response={}", doc, JacksonUtil.writeValueAsString(response));
        } else {
            log.warn("insert doc failed {}", JacksonUtil.writeValueAsString(response));
        }
        return cnt;
    }

    public int updateDoc(String indexName, String id, Map<String, Object> updateObjs) throws IOException {
        UpdateRequest updateRequest =
            new UpdateRequest()
                .index(indexName)
                .id(id)
                .doc(updateObjs, XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, DEFAULT);
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            log.info("update doc {} success, response={}", updateObjs, JacksonUtil.writeValueAsString(updateResponse));
        } else {
            log.warn("update doc failed {}", JacksonUtil.writeValueAsString(updateResponse));
        }
        return 1;
    }

    public boolean deleteDoc(String index, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        DeleteResponse deleteResponse = client.delete(deleteRequest, DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            log.info("delete doc success, index={}, id={}", index, id);
            return true;
        } else {
            log.info("delete doc failed, index={}, id={}", index, id);
            return false;
        }
    }
}
