package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.IDUtils;
import com.taotao.common.pojo.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 商品信息管理service
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    /**
     * 根据商品ID查询商品信息
     *
     * @param itemId 商品id
     * @return 商品信息类
     */
    @Override
    public TbItem getItemById(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 查询所有商品信息
     * @param page  页码
     * @param rows  每页显示多少条信息
     * @return 根据EasyUI的API返回通用的EasyUI返回类
     */
    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        if(page == null || page < 1){
            page = 1;
        }
        if(rows == null || rows < 1){
            rows = 30;
        }
        PageHelper.startPage(page,rows);
        TbItemExample example = new TbItemExample();
        example.createCriteria();
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);

        //使用根据API封装的工具类封装返回结果并返回
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    /**
     * 新增商品信息，需要插入商品信息、商品描述信息、商品规格参数信息
     *
     * @param item      商品信息
     * @param desc      商品描述信息
     * @param itemParam 商品规格参数信息
     * @return 通用的TaotaoResult类给前端判断状态
     */
    @Override
    public TaotaoResult createItem(TbItem item, String desc, String itemParam) {
        //补充item中实例域
        //1.根据ID生成策略的工具类生成商品ID
        //2.设置商品状态，1-正常 2-下架 3-删除，新创建的商品默认设置为正常
        //3.设置创建时间和更新时间，新增商品时默认为两者相同
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);

        //TbItem封装完成，插入商品表
        itemMapper.insert(item);

        //插入商品描述信息
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDesc.setItemDesc(desc);
        //插入到商品描述信息表
        itemDescMapper.insert(itemDesc);

        //插入商品规格参数，该表的ID是自增的
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);
        itemParamItem.setParamData(itemParam);
        itemParamItemMapper.insert(itemParamItem);

        return TaotaoResult.ok();
    }

    /**
     * 根据商品ID获取商品参数
     *
     * @param itemId 商品ID
     * @return 字符串
     */
    @Override
    public String getItemParamHtml(Long itemId) {
        //根据商品ID查询规格参数
        TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = itemParamItemExample.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(itemParamItemExample);
        if (list == null || list.size() == 0) {
            return "";
        }
        //取规格参数信息
        TbItemParamItem itemParamItem = list.get(0);
        String paramData = itemParamItem.getParamData();

        //生成html
        // 把规格参数json数据转换成java对象
        List<HashMap> mapList = JsonUtils.jsonToList(paramData, HashMap.class);
        StringBuffer sb = new StringBuffer();
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
        sb.append("    <tbody>\n");
        for (HashMap map : mapList) {
            sb.append("        <tr>\n");
            sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
            sb.append("        </tr>\n");
            List<HashMap> list2 = (List<HashMap>) map.get("params");
            for (HashMap m2 : list2) {
                sb.append("        <tr>\n");
                sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
                sb.append("            <td>" + m2.get("v") + "</td>\n");
                sb.append("        </tr>\n");
            }
        }
        sb.append("    </tbody>\n");
        sb.append("</table>");
        return sb.toString();
    }

    /**
     * "删除"商品信息，商品状态置为3
     *
     * @param ids 商品id
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult deleteItem(List<Long> ids) {
        //创建TbItem
        TbItem item = new TbItem();
        int count = 0;

        //将商品状态置为2
        item.setStatus((byte) 3);

        for (Long id : ids) {
            TbItem items = this.getItemById(id);
            if (items.getStatus() == 3) {
                count++;
            }
        }

        if (count > 0) {
            return new TaotaoResult(500, "error", null);
        } else {
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(ids);
            itemMapper.updateByExampleSelective(item, example);
            return TaotaoResult.ok();
        }
    }

    /**
     * 下架商品，商品状态置为2
     *
     * @param ids 商品ID
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult updateItemStatusInstock(List<Long> ids) {
        //创建TbItem
        TbItem item = new TbItem();
        int count = 0;

        //将商品状态置为2
        item.setStatus((byte) 2);

        for (Long id : ids) {
            TbItem items = this.getItemById(id);
            if (items.getStatus() == 2) {
                count++;
            }
        }

        if (count > 0) {
            return new TaotaoResult(500, "error", null);
        } else {
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(ids);
            itemMapper.updateByExampleSelective(item, example);
            return TaotaoResult.ok();
        }
    }

    /**
     * 上架商品信息，商品状态置为1
     *
     * @param ids 商品ID
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult updateItemStatusReshelf(List<Long> ids) {
        TbItem item = new TbItem();
        int count = 0;

        //将商品状态置为1
        item.setStatus((byte) 1);

        for (Long id : ids) {
            TbItem items = this.getItemById(id);
            if (items.getStatus() == 1) {
                count++;
            }
        }

        if (count > 0) {
            return TaotaoResult.build(500, "error");
        } else {
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(ids);
            itemMapper.updateByExampleSelective(item, example);
            return TaotaoResult.ok();
        }
    }
}
