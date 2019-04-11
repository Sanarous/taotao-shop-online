package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品内容管理Controller
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 查询所有内容分类
     *
     * @param parentId 父ID
     * @return 通用返回类型的集合
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCatList(parentId);
    }

    /**
     * 创建分类
     *
     * @param parentId 父ID
     * @param name     节点名
     * @return 通用返回类型
     */
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createNode(Long parentId, String name) {
        return contentCategoryService.insertCategory(parentId, name);
    }

    /**
     * 更新分类
     *
     * @param name 节点名
     * @return 通用返回类型
     */
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateNode(@RequestParam("id") Long id, String name) {
        return contentCategoryService.updateCategory(id, name);
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 通用返回类型
     */
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteCategory(Long id) {
        return contentCategoryService.deleteCategory(id);
    }
}
