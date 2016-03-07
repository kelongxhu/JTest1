package com.es;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kelong
 * @date 10/27/15
 */
public class EsClientTest {

    private Client client = null;

    private Client getClient() {
        // set Settings class loader to fixed jmx call it
        if (client == null) {
            Settings settings = ImmutableSettings.settingsBuilder()
                .classLoader(Settings.class.getClassLoader())
                .put("cluster.name", "cluster").build();

//            Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "fstore_cluster").build();

            client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("172.26.32.18",
                    9300));
        }

        return client;
    }

    public void createIndexs() {
        Doc doc = new Doc();
        doc.setId(1l);
        doc.setTitle("张三");
        doc.setPost("hello ,world.");
        Doc doc2 = new Doc();
        doc2.setId(2l);
        doc2.setTitle("李四");
        doc2.setPost("i am mac . hello ,how do you do.张三");
        Doc doc3 = new Doc();
        doc3.setId(3l);
        doc3.setTitle("王五");
        doc3.setPost("what are you doing");
        Doc doc4 = new Doc();
        doc4.setId(4l);
        doc4.setTitle("张四");
        doc4.setPost("my god,how are you");
        List<Doc> docs=new ArrayList<Doc>();
        docs.add(doc);
        docs.add(doc2);
        docs.add(doc3);
        docs.add(doc4);
        for (Doc docObj:docs){
            getClient().prepareIndex("forum", "post").setId(docObj.getId() + "")
                .setSource(JSON.toJSONString(docObj)).execute().actionGet();
        }
    }

    public void createIndex(){
        Doc doc = new Doc();
        doc.setId(1l);
        doc.setTitle("张三");
        doc.setPost("hello ,world.");
        getClient().prepareIndex("forum", "post").setId(doc.getId() + "")
            .setSource(JSON.toJSONString(doc)).execute().actionGet();
    }

    //matchQuery,termQuery
    public void searchIndex(){

        BoolQueryBuilder manQueryBuilder = QueryBuilders.boolQuery();
//        manQueryBuilder.should(QueryBuilders.multiMatchQuery("张三",new String[]{"post","title"}));
        manQueryBuilder.should(QueryBuilders.matchQuery("title","张三"));

        SearchResponse response = getClient().prepareSearch("forum").setTypes("post").setQuery(manQueryBuilder).setSize(5).execute().actionGet();//bunch of urls indexed
        String output = response.toString();
        System.out.println(output);
    }


    public static void main(String[] args) {
        EsClientTest esClient = new EsClientTest();
     //   esClient.createIndexs();
        esClient.searchIndex();
    }
}
