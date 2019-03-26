package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 商品Controller
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 显示商品列表、新增等页面
     * @return
     */
    @RequestMapping("/{page}")
    public String show(@PathVariable String page){
        return page;
    }

    /**
     * 查询所有商品信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    public EasyUIDataGridResult queryAllItems(@RequestParam("page") Integer pageNum,@RequestParam("rows") Integer pageSize) {
        return itemService.getItemList(pageNum, pageSize);
    }
}
