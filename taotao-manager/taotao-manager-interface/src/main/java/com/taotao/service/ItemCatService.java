package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * 商品类目service接口
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);
}
