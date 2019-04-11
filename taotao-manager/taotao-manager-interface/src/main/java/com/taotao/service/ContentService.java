package com.taotao.service;


import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 内容管理Service接口
 */
@Repository
public interface ContentService {

    /**
     * 新增内容跟
     *
     * @param content 内容
     * @return 通用返回类型
     */
    TaotaoResult insertContent(TbContent content);

    /**
     * 根据分类ID获取内同列表
     *
     * @param categoryId 分类ID
     * @param page       页码
     * @param row        每页显示数量
     * @return 通用返回类型
     */
    EasyUIDataGridResult getContentList(Long categoryId, int page, int row);

    /**
     * 根据ID获取内同
     *
     * @param id ID
     * @return 内容信息
     */
    TbContent getContentById(Long id);

    /**
     * 删除内容
     *
     * @param ids id
     * @return 通用返回类型
     */
    TaotaoResult deleteContent(List<Long> ids);

    /**
     * 更新内容信息
     *
     * @param content 更新的内容
     * @return 通用返回类型
     */
    TaotaoResult updateContent(TbContent content);
}
