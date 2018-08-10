package com.nbc.app.config;

import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
@RefreshScope
public class ElasticsearchConfiguration {
	@Resource
	private Environment environment;

	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;

	@Value("${spring.data.elasticsearch.cluster-nodes}")
	private String clusterNode;

	@Value("${spring.data.elasticsearch.properties.userName}")
	private String userName;

	@Value("${spring.data.elasticsearch.properties.password}")
	private String password;

	@Value("${port}")
	private String port;

	@Bean
	public RestHighLevelClient restClient() throws NumberFormatException, UnknownHostException {

		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

		RestClientBuilder builder = RestClient
				.builder("Y".equals(port) ? new HttpHost(clusterNode, 9200) : new HttpHost(clusterNode))
				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
					@Override
					public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					}
				});

		RestHighLevelClient client = new RestHighLevelClient(builder);
		return client;
	}

}
