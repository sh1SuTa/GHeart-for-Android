package top.putileaf.greenhearts.entity;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据
}
