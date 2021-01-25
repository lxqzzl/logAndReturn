package com.test.ge.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.ge.common.returns.Pagination;
import com.test.ge.entity.TestDO;

/**
 * 服务类接口
 *
 * @author lxq
 */
public interface TestService extends IService<TestDO> {
    /**
     * 查询列表
     * 若size = -1，返回全部数据
     */
    Pagination queryTestDOS(Long page, Long size, QueryWrapper<TestDO> queryWrapper);


}