package org.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 文生视频服务测试类
 */
public class DreamVideoServiceTest {
    
    @InjectMocks
    private DreamVideoService dreamVideoService;
    
    // 初始化Mockito注解
    public DreamVideoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    
    /**
     * 测试提交视频生成任务
     * @throws Exception
     */
    @Test
    public void testSubmitVideoGenerationTask() throws Exception {
        String prompt = "一个穿宇航服的猫在月球上漫步";
        
        // 测试空提示词的情况
        Assertions.assertThrows(Exception.class, () -> {
            dreamVideoService.submitVideoGenerationTask(null);
        });
        
        // 测试有效请求
        String taskId = dreamVideoService.submitVideoGenerationTask(prompt);
        Assertions.assertNotNull(taskId, "任务ID不应为null");
        Assertions.assertTrue(taskId.length() > 0, "任务ID应有有效长度");
        System.out.println("视频生成任务已提交，任务ID：" + taskId);
    }
    
}