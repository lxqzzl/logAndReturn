package com.test.ge.common.returns;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一返回状态码
 *
 * @author lxq
 */

public enum ResultCode {
    /* 请求成功 */
    SUCCESS(20000,"成功"),
    SUCCESS_NO_DATA(20001,"请求成功，但无数据"),

    /* 参数错误 */
    REQUEST_PARAM_TYPE_ERROR(40001, "参数格式类型错误"),
    PARAM_VALIDATE_ERROR(40002, "参数校验错误"),

    /* 数据库错误 */

    /* 请求失败 */
    RESOURCES_NOT_FOUND(40004,"未找到请求资源"),
    REQUEST_METHOD_ERROR(40005, "请求方式错误"),
    UNKNOWN_ERROR(50000,"未知原因,服务发生错误");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    //校验重复的code值
    public static void main(String[] args) {
        ResultCode[] ApiResultCodes = ResultCode.values();
        List<Integer> codeList = new ArrayList<Integer>();
        for (ResultCode ApiResultCode : ApiResultCodes) {
            if (codeList.contains(ApiResultCode.code)) {
                System.out.println(ApiResultCode.code);
            } else {
                codeList.add(ApiResultCode.code());
            }
        }
    }
}
