package com.taotao.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 商品分类类目实体类
 */
@Data
public class TbItemCat {
    //类目id
    private Long id;

    //父类目ID，ID为0时代表是顶级类目
    private Long parentId;

    //类目名称
    private String name;

    //类目状态可选值:1(正常),2(删除)
    private Integer status;

    //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
    private Integer sortOrder;

    //是否是父类目
    private Boolean isParent;

    //类目创建日期
    private Date created;

    //类目更新日期
    private Date updated;
}