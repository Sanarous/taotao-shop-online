package com.taotao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品规格参数信息实体类
 */
@Data
public class TbItemParam implements Serializable {
    //对应的商品id
    private Long id;

    //商品类目ID
    private Long itemCatId;

    //创建时间
    private Date created;

    //更新时间
    private Date updated;

    //规格参数信息
    private String paramData;
}