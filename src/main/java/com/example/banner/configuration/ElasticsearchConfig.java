package com.example.banner.configuration;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;


import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * Created by supritidan on 2017/09/23.
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Getter
@Setter
public class ElasticsearchConfig {
    private String host;
    private int port;
    private String cluster;
    private String node;

    @Bean
    public Client client(){
        String[] hosts = host.split(",");

        Settings settings = Settings.settingsBuilder().put("cluster.name", cluster).put("node.name", node).build();
        TransportClient transportClient = TransportClient.builder().settings(settings).build();
        try{
            for(String hostname : hosts){
                transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname.trim()), port));
            }
        }catch (Exception exceptin){
            transportClient.close();
            throw new RuntimeException();
        }
        return transportClient;
    }
}
