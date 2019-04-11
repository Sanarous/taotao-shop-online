package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 商品相关的service接口
 */
@Repository
public interface ItemService {

    /**
     * 根据商品Id查询商品信息
     *
     * @param itemId 商品id
     * @return 商品信息类
     */
    TbItem getItemById(Long itemId);

    /**
     * 查询所有商品信息
     * @return 根据EasyUI的API返回EasyUI工具类
     */
    EasyUIDataGridResult getItemList(Integer page,Integer rows);

    /**
     * 新增商品信息
     *
     * @param item      封装了Item的实体类
     * @param desc      商品描述信息，由于太长要与Tb_Item分表
     * @param itemParam 商品参数
     * @return 通用的TaotaoResult返回类给前端判断创建状态
     */
    TaotaoResult createItem(TbItem item, String desc, String itemParam);

    /**
     * 根据商品ID获取商品参数
     *
     * @param itemId 商品ID
     * @return 字符串
     */
    String getItemParamHtml(Long itemId);

    /**
     * 删除商品信息
     *
     * @param ids 商品id
     * @return 通用返回类型
     */
    TaotaoResult deleteItem(List<Long> ids);

    /**
     * 下架商品
     *
     * @param ids 商品ID
     * @return 通用返回类型
     */
    TaotaoResult updateItemStatusInstock(List<Long> ids);

    /**
     * 上架商品
     *
     * @param ids 商品ID
     * @return 通用返回类型
     */
    TaotaoResult updateItemStatusReshelf(List<Long> ids);
}
