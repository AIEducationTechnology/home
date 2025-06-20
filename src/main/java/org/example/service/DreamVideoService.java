package org.example.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.utils.URIBuilder;
import org.example.utils.Sign;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class DreamVideoService {
    private static final String AUTH_TOKEN = "AKLTYzc1MTVhMjYwNGY2NGEyM2IwYTQ1M2VlYzQyNGU0MDc"; // 替换为你的 AccessKey
    private static final String SECRET_KEY = "TVROaU56RXdNek15TldNek5EQmhaamhoWVdWak5HRTJOMkZpT0RWaVpESQ=="; // 替换为你的 SecretKey
    private static final String REGION = "cn-north-1";
    private static final String SERVICE = "cv";
    private static final String ENDPOINT = "https://visual.volcengineapi.com";
    private static final String PATH = "/api/video/submit";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public DreamVideoService() {
        // 初始化时无需初始化ApiClient
    }

    public String submitVideoGenerationTask(String prompt) throws Exception {
        // 使用 URIBuilder 安全地构建包含查询参数的 URL
        URIBuilder uriBuilder = new URIBuilder(ENDPOINT + PATH);
        uriBuilder.addParameter("Action", "CVSync2AsyncSubmitTask");
        uriBuilder.addParameter("Version", "2022-08-31");

        URI uri = uriBuilder.build();
        HttpPost httpPost = new HttpPost(uri);

        // 手动构建JSON请求体
        String jsonBody = String.format("{\"aspect_ratio\":\"9:16\", \"req_key\":\"jimeng_vgfm_t2v_l20\", \"prompt\":\"%s\"}", prompt);

        // 设置请求体
        StringEntity entity = new StringEntity(jsonBody, "UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        // 使用Sign类生成签名
        Sign sign = new Sign(REGION, SERVICE, "https", "visual.volcengineapi.com", PATH, AUTH_TOKEN, SECRET_KEY);
        
        // 构造查询参数
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Action", "CVSync2AsyncSubmitTask");
        queryMap.put("Version", "2022-08-31");
        
        // 获取当前时间
        Date date = new Date();
        
        // 执行签名并获取带签名的请求头
        Map<String, String> signedHeaders = sign.getSignedHeaders(httpPost.getMethod(), queryMap, jsonBody.getBytes(StandardCharsets.UTF_8), date);
        
        // 设置请求头
        for (Map.Entry<String, String> entry : signedHeaders.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }

        // 发送请求并获取响应
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(httpPost);

            // 处理响应
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                JsonNode jsonResponse = objectMapper.readTree(responseBody);

                // 检查响应是否包含任务ID
                if (jsonResponse.has("data") && jsonResponse.get("data").has("task_id")) {
                    return jsonResponse.get("data").get("task_id").asText();
                } else {
                    throw new Exception("API返回数据为空: " + responseBody);
                }
            } else {
                String errorMessage = "请求失败，状态码: " + (response != null ? response.getStatusLine().getStatusCode() : "null");
                if (response != null) {
                    String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                    errorMessage += ", 返回内容: " + responseBody;
                }
                throw new Exception(errorMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}