package com.taotao.common.pojo;

import lombok.Data;

@Data
public class PictureResult {
    /**
     * 上传图片返回值，成功：0	失败：1
     */
    private Integer error;
    /**
     * 回显图片使用的url
     */
    private String url;
    /**
     * 错误时的错误消息
     */
    private String message;

    public PictureResult() {
    }

    public PictureResult(Integer state, String url) {
        this.url = url;
        this.error = state;
    }

    public PictureResult(Integer state, String url, String errorMessage) {
        this.url = url;
        this.error = state;
        this.message = errorMessage;
    }
}
