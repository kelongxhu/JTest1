package com.es.util;

import com.es.model.IEsModel;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kelong
 * @date 10/28/15
 */
public class EsClient {

    private static final Logger log = LoggerFactory.getLogger(EsClient.class);

    private static EsClient esClient = null;

    private Client client = null;

    private EsClient() {
        if (client == null) {
            Settings settings = ImmutableSettings.settingsBuilder()
                .classLoader(Settings.class.getClassLoader())
                .put("cluster.name", "cluster").build();
            client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("172.26.32.18", 9300));
        }
    }

    /**
     * @return
     */
    public static EsClient getInstance() {
        if (esClient == null) {
            synchronized (EsClient.class) {
                if (esClient == null) {
                    esClient = new EsClient();
                }
            }
        }
        return esClient;
    }

    /**
     * 添加单条数据
     *
     * @param model
     * @throws java.io.IOException
     */
    public void addIndex(IEsModel model) throws Exception {
        IndexRequestBuilder indexReuqest = client
            .prepareIndex(model.getEsIndexName(), model.getEsTypeName())
            .setSource(model.getJson()).setId(model.getDocId());
        indexReuqest.execute().actionGet();
    }

    /**
     * 更新单条数据
     *
     * @param model
     * @throws Exception
     */
    public void updateIndex(IEsModel model) throws Exception {
        UpdateRequestBuilder indexReuqest = client.prepareUpdate(
            model.getEsIndexName(), model.getEsTypeName(),
            model.getDocId()).setDoc(model.getJson());

        indexReuqest.execute().actionGet();
    }

    /**
     * 根据ID批量删除
     *
     * @param model
     * @throws java.io.IOException
     */
    public void bulkDeleteIndex(String[] ids, IEsModel model) {

        if (null == ids || ids.length <= 0) {
            return;
        }
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

        for (String id : ids) {
            bulkRequestBuilder.add(client.prepareDelete(
                model.getEsIndexName(), model.getEsTypeName(), id));
        }
        BulkResponse deleteResponse = bulkRequestBuilder.execute().actionGet();

        if (deleteResponse.hasFailures()) {
            log.error(deleteResponse.buildFailureMessage());
        }
    }

    /**
     * 根据ID单条刪除
     *
     * @param model
     * @throws java.io.IOException
     */
    public void deleteIndex(String docId, IEsModel model) {
        DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete(
            model.getEsIndexName(), model.getEsTypeName(), docId);
        deleteRequestBuilder.execute().actionGet();
    }

    /**
     * 删除全部数据
     *
     * @param model
     * @throws java.io.IOException
     */
    public void deleteAllDoc(IEsModel model) {

        client.prepareDeleteByQuery(model.getEsIndexName()).setTypes(model.getEsTypeName())
            .setQuery(QueryBuilders.matchAllQuery())
            .execute().actionGet();
    }

}
