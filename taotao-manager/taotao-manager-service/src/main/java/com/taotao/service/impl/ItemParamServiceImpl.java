package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品规格参数Service
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;

    /**
     * 获取所有的规格参数信息
     *
     * @param page 页码
     * @param rows 每页信息数
     * @return 通用返回类型
     */
    @Override
    public EasyUIDataGridResult getItemParamList(int page, int rows) {
        PageHelper.startPage(page, rows);

        TbItemParamExample example = new TbItemParamExample();
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        //取出分页信息
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
        //返回处理结果
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    /**
     * 根据cid查询规格模板信息
     *
     * @param cid ID
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult getItemParamByCid(Long cid) {
        //根据cid查询规格参数模板
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if (list != null && list.size() > 0) {
            TbItemParam itemParam = list.get(0);
            return TaotaoResult.ok(itemParam);
        }
        return TaotaoResult.ok();
    }

    /**
     * 新增规格参数信息
     *
     * @param cid       ID
     * @param paramData 规格参数
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult insertItemParam(Long cid, String paramData) {
        //创建一个pojo
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    /**
     * 删除规格参数信息
     *
     * @param ids ID
     * @return 通用返回类型
     */
    @Override
    public TaotaoResult deleteItemParam(List<Long> ids) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        itemParamMapper.deleteByExample(example);
        return TaotaoResult.ok();
    }
}
