package com.taotao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品信息
 */
@Data
public class TbItem implements Serializable {
    //商品id
    private Long id;

    //商品标题
    private String title;

    //卖点
    private String sellPoint;

    //商品价格
    private Long price;

    //商品库存
    private Integer num;

    //二维码
    private String barcode;

    //图片的url
    private String image;

    private Long cid;

    //商品状态，1-正常，2-下架，3-删除
    private Byte status;

    //商品创建日期
    private Date created;

    //商品更新日期
    private Date updated;
}