package com.wynk.consumerservice.proxy;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/

@Slf4j
@Service
public class HttpProxy {

    private static Map<Integer, HttpClient> httpClientMap = new ConcurrentHashMap<>();
    private static PoolingClientConnectionManager poolingClientConnectionManager;

    static {
        poolingClientConnectionManager = new PoolingClientConnectionManager();
        poolingClientConnectionManager.setMaxTotal(500);
        poolingClientConnectionManager.setDefaultMaxPerRoute(200);
    }

    public static String postData(String url, String data, Map<String, String> headerParams, int timeout) {
        HttpPost post = null;
        String responseString = null;
        ByteArrayInputStream bis = null;
        try {
            post = new HttpPost(url);
            for(String key : headerParams.keySet()) {
                post.addHeader(key,headerParams.get(key));
            }
            bis = new ByteArrayInputStream(data.getBytes());
            HttpEntity entity = new InputStreamEntity(bis, bis.available());
            post.setEntity(entity);
            HttpResponse response = getHttpClient(timeout).execute(post);
            HttpEntity entityResponse = response.getEntity();
            log.info("Http response : {}", response.getStatusLine());
            responseString = EntityUtils.toString(entityResponse, "UTF-8");
        } catch (Exception e) {
            log.error("Error requesting url [{}] with timeout [{}] , {}", url, timeout, e.getMessage(),e);
        } finally {
            if (post != null)
                post.releaseConnection();
            if(bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
        return responseString;
    }

    private static HttpClient getHttpClient(int timeOut) {
        HttpClient httpClient = httpClientMap.get(timeOut);
        if (httpClient == null) {
            httpClient = new DecompressingHttpClient(new DefaultHttpClient(poolingClientConnectionManager));
            HttpParams params = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, timeOut);
            HttpConnectionParams.setSoTimeout(params, timeOut);
            httpClientMap.put(timeOut, httpClient);
        }
        return httpClient;
    }
}
