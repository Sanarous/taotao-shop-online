package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品参数管理Controller
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    /**
     * 获取所有规格参数信息
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
        return result;
    }

    /**
     * 打开规格参数
     *
     * @param cid cid
     * @return 通用返回类型
     */
    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public TaotaoResult getItemCatByCid(@PathVariable Long cid) {
        TaotaoResult result = itemParamService.getItemParamByCid(cid);
        return result;
    }

    /**
     * 提交规格参数
     *
     * @param cid       cid
     * @param paramData 规格参数类
     * @return 通用返回类型
     */
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData) {
        itemParamService.insertItemParam(cid, paramData);
        return TaotaoResult.ok();
    }

    /**
     * 删除规格参数
     *
     * @return 通用返回类型
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public TaotaoResult deleteItemParamByCid(@RequestParam("ids") List<Long> ids) {
        itemParamService.deleteItemParam(ids);
        return TaotaoResult.ok();
    }
}
