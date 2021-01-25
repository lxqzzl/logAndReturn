package com.test.ge.common.returns;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * 错误参数数据结构
 *
 * @author lxq
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalErrorParam {
    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 拒绝接收的值
     */
    private Object rejectedValue;

    /**
     * 错误信息
     */
    private String errorMsg;
}
