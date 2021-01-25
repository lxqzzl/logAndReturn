package com.test.ge.common.returns;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 列表数据返回数据结构
 *
 * @Author lxq
 */

@Data
public class Pagination<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 数据总条数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long pages;
    /**
     * 请求页
     */
    private Long page;
    /**
     * 单页限制
     */
    private Long size;
    /**
     * 数据
     */
    private List<T> list;

    /**
     * 分页
     * @param iPage
     */
    public Pagination(IPage<T> iPage){
        this.total = iPage.getTotal();
        this.pages = iPage.getPages();
        this.page = iPage.getCurrent();
        this.size = iPage.getSize();
        this.list = iPage.getRecords();
    }

    /**
     * 不分页
     * @param list 列表数据
     */
    public Pagination(List<T> list){
        this.total = new Long((long)list.size());
        this.pages = 1L;
        this.page = 1L;
        this.size = new Long((long)list.size());;
        this.list = list;
    }

}
