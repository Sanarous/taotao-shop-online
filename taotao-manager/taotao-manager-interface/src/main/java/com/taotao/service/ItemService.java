package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import org.springframework.stereotype.Repository;


/**
 * 商品相关的serivce接口
 */
@Repository
public interface ItemService {

    /**
     * 查询所有商品信息
     * @return
     */
    EasyUIDataGridResult getItemList(Integer page,Integer rows);
}
