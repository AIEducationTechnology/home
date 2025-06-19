package org.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一的API响应结构
 */
@ApiModel("通用API响应")
public class ApiResponse<T> {
    
    @ApiModelProperty(value = "响应状态码", example = "200")
    private int status;
    
    @ApiModelProperty(value = "响应消息", example = "请求成功")
    private String message;
    
    @ApiModelProperty(value = "响应数据")
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getter 和 Setter 方法

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 创建成功的响应
     * @param data 响应数据
     * @return ApiResponse 对象
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "请求成功", data);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(500, message, null);
    }

}