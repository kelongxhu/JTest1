package com.dao.es;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryAction;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.elasticsearch.index.query.QueryBuilders.constantScoreQuery;


public class EsDaoSupport {

    private static final Logger logger = LoggerFactory.getLogger(EsDaoSupport.class);

    private Client esClient;

    protected SearchRequestBuilder newSearch(final String index, final String docType) {
        esClient = EsClientFactory.getEsTransportClient();
        return esClient.prepareSearch(index).setTypes(docType);
    }

    protected SearchResponse aggr(final String index, final String docType,
                                  final QueryBuilder query, final AbstractAggregationBuilder aggr) {

        esClient = EsClientFactory.getEsTransportClient();

        final SearchRequestBuilder request =
            newSearch(index, docType).setSize(0).setQuery(constantScoreQuery(query))
                .addAggregation(aggr);
        return request.execute().actionGet();
    }

    /**
     * 插入一条数据
     *
     * @param index
     * @param type
     * @param jsonSource 插入数据的json字符串
     * @return
     */
    protected boolean insertOne(String index, String type, String jsonSource) {
        esClient = EsClientFactory.getEsTransportClient();
        IndexResponse response =
            esClient.prepareIndex(index, type).setSource(jsonSource).execute().actionGet();
        if (StringUtils.isNotBlank(response.getId())) {
            return true;
        }
        return false;
    }

    /**
     * 插入一条自带ID的数据
     *
     * @param index
     * @param type
     * @param jsonSource
     * @param id
     * @return
     */
    public boolean insertOneById(String index, String type, String jsonSource, String id) {
        esClient = EsClientFactory.getEsTransportClient();
        IndexResponse response =
            esClient.prepareIndex(index, type).setSource(jsonSource).setId(id).execute().actionGet();
        if (StringUtils.isNotBlank(response.getId())) {
            return true;
        }
        return false;
    }

    /**
     * 更新文档
     *
     * @param index
     * @param type
     * @param jsonSource
     * @param id
     */
    public void updateOneById(String index, String type, String jsonSource, String id) {
        esClient = EsClientFactory.getEsTransportClient();
        UpdateRequestBuilder indexReuqest =
            esClient.prepareUpdate(index, type, id).setDoc(jsonSource);

        indexReuqest.execute().actionGet();
    }

    /**
     * 插入或更新文档
     *
     * @param index
     * @param type
     * @param upsertJson
     * @param id
     */
    public void upsert(String index, String type, String docJson, String upsertJson, String script,
                       String id) {
        if (StringUtils.isBlank(upsertJson)) {
            logger.error("upsert can't null; id： {}", id);
        }
        if (StringUtils.isNotBlank(docJson) && StringUtils.isNotBlank(script)) {
            logger.error("can't provide both script and doc; id： {}， upsert： {}", id, upsertJson);
        }
        esClient = EsClientFactory.getEsTransportClient();
        IndexRequest indexRequest = new IndexRequest(index, type, id).source(upsertJson);
        UpdateRequestBuilder updateReuqest =
            esClient.prepareUpdate(index, type, id).setUpsert(indexRequest);
        if (StringUtils.isNotBlank(docJson)) {
            updateReuqest.setDoc(docJson);
        }
        if (StringUtils.isNotBlank(script)) {
            updateReuqest.setScript(new Script(script));
        }
        updateReuqest.execute().actionGet();

    }

    /**
     * 插入或更新文档
     *
     * @param index
     * @param type
     * @param docJson
     * @param upsertJson
     * @param ttl
     * @param id
     */
    public void upsertTtl(String index, String type, String docJson, String upsertJson, TimeValue ttl,
                          String id) {
        if (StringUtils.isBlank(upsertJson)) {
            logger.error("upsert can't null; id： {}", id);
        }
        if (StringUtils.isBlank(docJson)) {
            logger.error("docJson can't null; id： {}", id);
        }
        esClient = EsClientFactory.getEsTransportClient();
        IndexRequest indexRequest = new IndexRequest(index, type, id).source(upsertJson).ttl(ttl);
        UpdateRequestBuilder updateReuqest =
            esClient.prepareUpdate(index, type, id).setUpsert(indexRequest).setDoc(docJson);
        updateReuqest.setTtl(ttl);
        updateReuqest.execute().actionGet();

    }

    /**
     * 删除文档
     *
     * @param index
     * @param type
     * @param id
     */
    public void deleteById(String index, String type, String id) {
        esClient = EsClientFactory.getEsTransportClient();
        DeleteRequestBuilder deleteRequestBuilder = esClient.prepareDelete(index, type, id);
        DeleteResponse response = deleteRequestBuilder.execute().actionGet();
        logger.debug("Delete:{}", response.getId());
    }

    /**
     * delete by query
     *
     * @param query
     */
    public long deleteByQuery(String index, String type, QueryBuilder query) {
        esClient = EsClientFactory.getEsTransportClient();
        DeleteByQueryRequestBuilder deleteBuilder =
            new DeleteByQueryRequestBuilder(esClient, DeleteByQueryAction.INSTANCE);
        DeleteByQueryResponse response = deleteBuilder
            .setIndices(index)
            .setTypes(type)
            .setQuery(query).execute().actionGet();
        long totalDeleted = response.getTotalDeleted();
        logger.debug("Delete:{}", totalDeleted);
        return totalDeleted;
    }

    public String getById(String index, String type, String id) {
        esClient = EsClientFactory.getEsTransportClient();
        GetResponse reponse = esClient.prepareGet(
            index, type, id).execute().actionGet();
        return reponse.getSourceAsString();
    }

    /**
     * 批量删除文档
     *
     * @param index
     * @param type
     * @param ids
     */
    public void batchDelete(String index, String type, String[] ids) {
        esClient = EsClientFactory.getEsTransportClient();
        BulkRequestBuilder bulkRequestBuilder = esClient.prepareBulk();
        for (String id : ids) {
            bulkRequestBuilder.add(esClient.prepareDelete(index, type, id));
        }
        BulkResponse deleteResponse = bulkRequestBuilder.execute().actionGet();
        if (deleteResponse.hasFailures()) {
            logger.error(deleteResponse.buildFailureMessage());
        }
    }

    protected long getCount(final Aggregation aggr) {
        return ((Terms) aggr).getBuckets().size() + 0L;
    }

    protected long getCount(final MultiBucketsAggregation.Bucket bucket) {
        if (null == bucket) {
            return 0L;
        }
        return bucket.getDocCount();
    }

    protected long getCount(final Sum sum) {
        return Double.valueOf(sum.getValue()).longValue();
    }

    /**
     * 根据ID索引数据
     *
     * @param index
     * @param docType
     * @param id
     * @return
     */
    public String findById(final String index, final String docType, final String id) {
        esClient = EsClientFactory.getEsTransportClient();
        GetResponse response = esClient.prepareGet(index, docType, id).execute().actionGet();
        return response.getSourceAsString();
    }
}
