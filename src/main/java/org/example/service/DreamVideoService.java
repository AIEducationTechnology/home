package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DreamVideoService {
    private static final String BASE_URL = "https://visual.volcengineapi.com/api/video/submit";
    private static final String QUERY_URL = "https://visual.volcengineapi.com/api/video/query";
    private static final String AUTH_TOKEN = "your-access-key"; // 替换为你的 AccessKey

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 提交文生视频任务
    public String submitVideoGenerationTask(String prompt) throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("req_key", "jimeng_vgfm_t2v_l20");
        requestBody.put("prompt", prompt);
        requestBody.put("aspect_ratio", "9:16");

        String jsonInput = objectMapper.writeValueAsString(requestBody);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(BASE_URL);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Region", "cn-north-1");
            post.setHeader("Service", "cv");
            post.setEntity(new StringEntity(jsonInput));

            try (CloseableHttpResponse response = client.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                Map<String, Object> result = objectMapper.readValue(responseBody, Map.class);

                return (String) ((Map<String, Object>) result.get("data")).get("task_id");
            }
        }
    }

    // 查询视频生成结果
    public String queryVideoGenerationResult(String taskId) throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("req_key", "jimeng_vgfm_t2v_l20");
        requestBody.put("task_id", taskId);

        String jsonInput = objectMapper.writeValueAsString(requestBody);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(QUERY_URL);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Region", "cn-north-1");
            post.setHeader("Service", "cv");
            post.setEntity(new StringEntity(jsonInput));

            try (CloseableHttpResponse response = client.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                Map<String, Object> result = objectMapper.readValue(responseBody, Map.class);

                return (String) ((Map<String, Object>) result.get("data")).get("video_url");
            }
        }
    }
}