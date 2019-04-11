package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.HttpClientUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容管理Controller
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REDIS_CONTENT_SYNC_URL}")
    private String REDIS_CONTENT_SYNC_URL;

    @Autowired
    private ContentService contentService;

    /**
     * 展示每个节点内容
     *
     * @param categoryId 分类ID
     * @return 通用返回类型
     */
    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        EasyUIDataGridResult result = contentService.getContentList(categoryId, page, rows);
        return result;
    }

    /**
     * 添加内容
     *
     * @param content 内容
     * @return 通用返回类型
     */
    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult insertContent(TbContent content) {
        TaotaoResult result = contentService.insertContent(content);
        //调用taotao-rest发布的服务以同步缓存
        HttpClientUtil.doGet(REST_BASE_URL + REDIS_CONTENT_SYNC_URL + content.getCategoryId());
        return result;
    }

    /**
     * 删除内容
     *
     * @param ids ids
     * @return 通用返回类型
     */
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContent(@RequestParam("ids") List<Long> ids) {
        List<Long> categoryIds = new ArrayList<>();
        for (Long id : ids) {
            TbContent content = contentService.getContentById(id);
            categoryIds.add(content.getCategoryId());
        }
        contentService.deleteContent(ids);
        //调用taotao-rest发布的服务以同步缓存
        for (Long long1 : categoryIds) {
            String catId = long1 + "";
            HttpClientUtil.doGet(REST_BASE_URL + REDIS_CONTENT_SYNC_URL + catId);
        }
        return TaotaoResult.ok();
    }

    /**
     * 更新内容
     *
     * @param content 更新的内容
     * @return 通用返回类型
     */
    @RequestMapping("/edit")
    @ResponseBody
    public TaotaoResult updateContent(TbContent content) {
        contentService.updateContent(content);
        //调用taotao-rest发布的服务以同步缓存
        HttpClientUtil.doGet(REST_BASE_URL + REDIS_CONTENT_SYNC_URL + content.getCategoryId());
        return TaotaoResult.ok();
    }
}
