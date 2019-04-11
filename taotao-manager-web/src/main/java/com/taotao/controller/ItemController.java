package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品Controller
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 显示商品列表、新增等页面
     * @return 目标页面
     */
    @RequestMapping("/{page}")
    public String show(@PathVariable String page){
        return page;
    }

    /**
     * 查询所有商品信息
     * @return 通用返回类型
     */
    @ResponseBody
    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    public EasyUIDataGridResult queryAllItems(@RequestParam("page") Integer pageNum,@RequestParam("rows") Integer pageSize) {
        return itemService.getItemList(pageNum, pageSize);
    }

    /**
     * 新增商品信息
     *
     * @param item       商品信息
     * @param desc       商品描述信息
     * @param itemParams 商品参数
     * @return 通用TaotaoResult给前端
     */
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
        return itemService.createItem(item, desc, itemParams);
    }

    /**
     * 展示商品信息
     *
     * @param itemId 商品ID
     * @param model  Model
     * @return Json字符串
     */
    @RequestMapping("/page/item/{itemId}")
    public String showItemParam(@PathVariable Long itemId, Model model) {
        String html = itemService.getItemParamHtml(itemId);
        model.addAttribute("myhtml", html);
        return "itemparam";
    }

    /**
     * 删除商品信息
     *
     * @param ids 商品ID
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public TaotaoResult deleteItem(@RequestParam("ids") List<Long> ids) {
        itemService.deleteItem(ids);
        return TaotaoResult.ok();
    }

    /**
     * 下架商品
     *
     * @param ids 商品ID
     * @return 通用返回类型
     */
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public TaotaoResult updateItemStatusInstock(@RequestParam("ids") List<Long> ids) {
        itemService.updateItemStatusInstock(ids);
        return TaotaoResult.ok();
    }

    /**
     * 上架商品
     *
     * @param ids 商品ID
     * @return 通用返回类型
     */
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public TaotaoResult updateItemStatusReshelf(@RequestParam("ids") List<Long> ids) {
        itemService.updateItemStatusReshelf(ids);
        return TaotaoResult.ok();
    }
}
