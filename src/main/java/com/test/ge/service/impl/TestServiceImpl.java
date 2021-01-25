package com.test.ge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.ge.common.returns.Pagination;
import com.test.ge.common.utils.PageUtil;
import org.springframework.stereotype.Service;

import com.test.ge.entity.TestDO;
import com.test.ge.dao.TestMapper;
import com.test.ge.service.TestService;

import java.util.List;

/**
 * TestServiceImpl服务实现类
 *
 * @author lxq
 */
@Service("TestService")
public class TestServiceImpl extends ServiceImpl<TestMapper, TestDO> implements TestService {
    /**
     * 查询列表
     * 若size == -1，返回全部数据
     */
    @Override
    public Pagination queryTestDOS(Long page, Long size, QueryWrapper<TestDO> queryWrapper){
        if(size != null && size == -1){
            // 查询列表
            List<TestDO> testDO_list = this.list(queryWrapper);
            return new Pagination(testDO_list);
        }
        // 构造分页
        IPage<TestDO> testDO_iPage = this.page(new PageUtil<TestDO>().getPage(page, size), queryWrapper);
        return  new Pagination(testDO_iPage);
    }
}