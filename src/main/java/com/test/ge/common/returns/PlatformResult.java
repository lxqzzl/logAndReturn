package com.test.ge.common.returns;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成功返回数据结构
 *
 * @author lxq
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlatformResult implements Result{
    /* 状态码 */
    private Integer code;
    /* 返回信息 */
    private String msg;
    /* 返回数据 */
    private Object data;

    /**
     *  根据Result赋值
     */
    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }

    /* 成功返回结果（无数据） */
    public static PlatformResult success(){
        PlatformResult platformResult = new PlatformResult();
        platformResult.setResultCode(ResultCode.SUCCESS);
        return platformResult;
    }

    /* 成功返回结果 */
    public static PlatformResult success(Object data){
        PlatformResult platformResult = new PlatformResult();
        platformResult.setResultCode(ResultCode.SUCCESS);
        platformResult.setData(data);
        return platformResult;
    }
}
