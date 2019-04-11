package com.taotao.service.impl;

import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 内容管理Service实现类
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 显示每个页面内容
     *
     * @param categoryId 分类ID
     * @param page       页码
     * @param row        每页显示数量
     * @return 通用返回类型
     */
    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, int page, int row) {
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);

        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
        EasyUIDataGridResult dataGridResult = new EasyUIDataGridResult();
        dataGridResult.setTotal(pageInfo.getTotal());
        dataGridResult.setRows(list);
        return dataGridResult;
    }

    /**
     * 插入页面内容
     *
     * @param content 内容
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult insertContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        return TaotaoResult.ok();
    }

    /**
     * 删除内容
     *
     * @param ids id
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult deleteContent(List<Long> ids) {
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        contentMapper.deleteByExample(example);
        return TaotaoResult.ok();
    }

    /**
     * 更新内容信息
     *
     * @param content 更新的内容
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult updateContent(TbContent content) {
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(content.getId());
        contentMapper.updateByExampleSelective(content, example);
        return TaotaoResult.ok();
    }

    /**
     * 根据ID获取内容
     *
     * @param id ID
     * @return 内容信息
     */
    @Override
    public TbContent getContentById(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

}
