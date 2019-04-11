package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品规格参数接口
 */
@Repository
public interface ItemParamService {

    /**
     * 根据cid获取商品规格参数
     *
     * @param cid ID
     * @return 通用返回类型
     */
    TaotaoResult getItemParamByCid(Long cid);

    /**
     * 新增商品规格参数
     *
     * @param cid       ID
     * @param paramData 规格参数
     * @return 通用返回类型
     */
    TaotaoResult insertItemParam(Long cid, String paramData);

    /**
     * 获取商品规格参数列表
     *
     * @param page 页码
     * @param rows 每页信息数
     * @return 通用返回类型
     */
    EasyUIDataGridResult getItemParamList(int page, int rows);

    /**
     * 删除规格参数
     *
     * @param ids ID
     * @return 通用返回类型
     */
    TaotaoResult deleteItemParam(List<Long> ids);
}
