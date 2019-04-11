package com.taotao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品规格参数实体类
 */
@Data
public class TbItemParamItem implements Serializable {
    private Long id;

    //商品id
    private Long itemId;

    //创建时间
    private Date created;

    //更新时间
    private Date updated;

    //参数信息，是一个JSON格式字符串
    private String paramData;
}