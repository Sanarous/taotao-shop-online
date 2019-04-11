package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 内容分类Service接口
 */
@Repository
public interface ContentCategoryService {

    /**
     * 根据父ID获取所有的内容列表
     *
     * @param parentId 父ID
     * @return 包含通用返回类型的List
     */
    List<EasyUITreeNode> getContentCatList(Long parentId);

    /**
     * 新增内容
     *
     * @param parentId 父ID
     * @param name     类目名
     * @return 通用返回类型
     */
    TaotaoResult insertCategory(Long parentId, String name);

    /**
     * 更新内容
     *
     * @param id   内容ID
     * @param name 内容名
     * @return 通用返回类型
     */
    TaotaoResult updateCategory(Long id, String name);

    /**
     * 删除内容
     *
     * @param id 内容ID
     * @return 通用返回类型
     */
    TaotaoResult deleteCategory(Long id);
}
