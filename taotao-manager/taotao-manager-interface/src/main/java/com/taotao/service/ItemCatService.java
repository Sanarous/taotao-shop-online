package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品类目service接口
 */
@Repository
public interface ItemCatService {

    /**
     * 根据父ID获取分类列表
     *
     * @param parentId 父ID
     * @return 通用返回类型的集合
     */
    List<EasyUITreeNode> getItemCatList(long parentId);
}
