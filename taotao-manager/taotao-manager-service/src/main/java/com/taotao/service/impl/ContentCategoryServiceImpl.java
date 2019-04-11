package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理Service
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 查询全部分类内容
     *
     * @param parentId 父ID
     * @return 通用返回类型
     */
    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        //根据ParentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        //转换成EasyUITreeNode
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            resultList.add(node);
        }
        return resultList;
    }

    /**
     * 新增内容分类
     *
     * @param parentId 父ID
     * @param name     类目名
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult insertCategory(Long parentId, String name) {
        // 创建一个pojo
        TbContentCategory category = new TbContentCategory();
        category.setName(name);
        category.setParentId(parentId);
        category.setStatus(1);
        category.setIsParent(false);
        //排序序号，表示同级目录的展现次序，如数值相等则按名称次序排序，取值范围：大于0的整数
        category.setSortOrder(1);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        //插入数据
        contentCategoryMapper.insert(category);
        //取返回的主键
        Long id = category.getId();
        //判断父节点的isparent属性
        //查询父节点
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        //返回主键
        return TaotaoResult.ok(id);
    }

    /**
     * 更新内容
     *
     * @param id   内容ID
     * @param name 内容名
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult updateCategory(Long id, String name) {
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        tbContentCategory.setName(name);
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        contentCategoryMapper.updateByExample(tbContentCategory, example);
        return TaotaoResult.ok();
    }

    /**
     * 删除内容节点
     *
     * @param id 内容ID
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult deleteCategory(Long id) {
        //根据当前id获取父节点ID
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = tbContentCategory.getParentId();

        if (tbContentCategory.getIsParent() == false) {
            //如果要删除的节点不是父节点，直接删除
            contentCategoryMapper.deleteByPrimaryKey(id);


        } else {
            //如果删除的是父节点，那么将该父节点下所有子节点利用递归全部删除
            deleteChildList(id);
            contentCategoryMapper.deleteByPrimaryKey(id);
        }

        //并判断其父节点下是否还有子节点，如果有，则不更新父节点状态，如果没有，则更新父节点状态
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria();
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

        int index = 0;
        for (TbContentCategory tbContentCategory2 : list) {
            if (tbContentCategory2.getParentId() == parentId) {
                //如果是父节点下还有子节点，那么开始计数
                index++;
            }
        }

        if (index == 0) {
            TbContentCategory primaryKey = contentCategoryMapper.selectByPrimaryKey(parentId);
            primaryKey.setIsParent(false);

            TbContentCategoryExample example2 = new TbContentCategoryExample();
            Criteria criteria2 = example2.createCriteria();
            criteria2.andIdEqualTo(parentId);

            contentCategoryMapper.updateByExample(primaryKey, example2);
            return TaotaoResult.ok();
        } else {
            return TaotaoResult.ok();
        }

    }

    /**
     * 递归删除某个父节点下所有子节点方法
     *
     * @param id id
     */
    public void deleteChildList(Long id) {
        //查询所有
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria();
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

        for (TbContentCategory tbContentCategory : list) {
            //获取当前父节点下的一级子节点
            if (tbContentCategory.getParentId() == id) {
                if (tbContentCategory.getIsParent() == false) {
                    //如果当前父节点下的子节点是叶子节点，直接删除
                    contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
                } else {
                    //如果当前父节点下的子节点仍然是父节点，递归删除
                    deleteChildList(tbContentCategory.getId());
                }
            }
        }
    }
}
