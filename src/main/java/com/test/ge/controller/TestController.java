package com.test.ge.controller;

import com.test.ge.common.returns.Pagination;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.ge.service.TestService;
import com.test.ge.entity.TestDO;

import io.swagger.annotations.Api;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * TestController前端控制器
 *
 * @author lxq
 */

@RestController
@RequestMapping("/tests")
@Api(tags = "user_test表操作API")	
public class TestController {

    private TestService testService;
    @Autowired
    public TestController(TestService testService){
        this.testService = testService;
    }

    /**
     * 列表数据
     */
    @GetMapping
    public Pagination<TestDO> list(@RequestParam(required = false) Long page,
                       @RequestParam(required = false) Long size){
        Pagination<TestDO> testDO_pagination = testService.queryTestDOS(page, size, null);
        return testDO_pagination;
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public TestDO info(@PathVariable("id") Long id){
        TestDO testDO = testService.getById(id);
        return testDO;
    }

    /**
     * 保存
     */
    @PostMapping
    public Boolean save(@RequestBody @Validated TestDO testDO){
        testService.save(testDO);
        return true;
    }

    /**
     * 修改
     */
    @PutMapping
    public Boolean update(@RequestBody TestDO testDO){
        testService.saveOrUpdate(testDO);
        return true;
    }

    /**
     * 部分修改
     */
    @PatchMapping
    public Boolean update(@RequestBody List<TestDO> testDOs){
        testService.saveOrUpdateBatch(testDOs);
        return true;
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id){
        testService.removeById(id);
        return true;
    }
}