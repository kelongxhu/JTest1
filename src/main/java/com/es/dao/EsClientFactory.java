package com.es.dao;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.plugin.deletebyquery.DeleteByQueryPlugin;

import java.net.InetAddress;

import static org.apache.commons.lang.StringUtils.split;


public class EsClientFactory {

    private static TransportClient client;

    private EsClientFactory() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Client getEsTransportClient() {
        if (client == null) {
            synchronized (EsClientFactory.class) {
                if (client == null) {
                    new EsClientFactory();
                }
            }
        }
        return client;
    }

    public void init() throws Exception {
        //        PropertiesParser pro = ConfigUtil.getServerConfig();

        //        String nodes = pro.getString("es.cluster.nodes");
        //        String clusterName = pro.getString("es.cluster.name");
        String nodes = "";
        String clusterName = "";

        final Settings settings = Settings.settingsBuilder()
            .put("cluster.name", clusterName)
            .put("client.transport.ping_timeout", "2m")
            .build();

        client =
            TransportClient.builder().settings(settings).addPlugin(DeleteByQueryPlugin.class).build();

        for (final String node : split(nodes, ',')) {
            final String[] parts = split(node, ':');
            if (0 == parts.length) {
                continue;
            }
            final String host = parts[0];
            int port = 9300;
            if (parts.length > 1) {
                try {
                    port = Integer.valueOf(parts[1]);
                } catch (final Exception e) {
                    // no-op
                }
            }
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        }

    }

    public static void main(String[] args) {
        new EsClientFactory();
        if (client != null) {
            System.out.println("Ok");
        }
    }
}
