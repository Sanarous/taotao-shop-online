package com.taotao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品描述信息实体类
 */
@Data
public class TbItemDesc implements Serializable {
    //对应的商品ID
    private Long itemId;

    //创建时间
    private Date created;

    //更新时间
    private Date updated;

    //商品描述信息
    private String itemDesc;
}