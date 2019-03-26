package com.taotao.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class TestMapper {
    @Autowired
    private TbItemMapper itemMapper;

    @Test
    public void testItemMapper(){
        PageHelper.startPage(1,10);
        TbItemExample example = new TbItemExample();
        example.createCriteria();
        List<TbItem> items = itemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo = new PageInfo<>(items);
        System.out.println(pageInfo.getFirstPage());
        System.out.println(pageInfo.getList().size());
    }
}
