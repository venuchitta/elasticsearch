package org.cer.es;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.cer.es.AbstractApi;

public class TransportClientTest extends AbstractApi {

	public void connect() {
		//For testing purposes we will create a test node so we can connect to
		createLocalCluster("escluster2");
		
		//Create the configuration - you can omit this step and use 
		//non-argument constructor of TransportClient
		Settings settings = ImmutableSettings.settingsBuilder()
			.put("cluster.name", "escluster2").build();
		
		//Create the transport client instance
        TransportClient client = new TransportClient(settings);
        
        //add some addresses of ElasticSearch cluster nodes
        client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));

        //Now we can do something with ElasticSearch
        //...
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
            "}";
        IndexResponse response = client.prepareIndex("twitter", "tweet")
                .setSource(json)
                .execute()
                .actionGet();
        System.out.println(response.getIndex());

		//At the end we should close resources. In real scenario make sure do it in finally block.
		client.close();
	}

}
