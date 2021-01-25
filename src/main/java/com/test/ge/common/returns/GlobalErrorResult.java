package com.test.ge.common.returns;

import com.test.ge.common.utils.RequestContextHolderUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 错误返回数据结构
 *
 * @author lxq
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GlobalErrorResult implements Result{

    private static final long serialVersionUID = -8206961367943424440L;

    /**
     * HTTP响应状态码 {@link org.springframework.http.HttpStatus}
     */
    private Integer httpStatus;

    /**
     * HTTP响应状态码的英文提示
     */
    private String httpError;

    /**
     * 系统内部自定义的返回值编码，{@link ResultCode} 是对错误更加详细的编码
     */
    private Integer code;

    /**
     * 系统内部自定义的错误信息，{@link ResultCode} 是对错误更加详细的信息
     */
    private String msg;

    /**
     * 调用接口路径
     */
    private String requestPath;

    /**
     * 异常的名称
     */
    private String exceptionName;

    /**
     * 异常的信息
     */
    private String exceptionMsg;

    /**
     * 异常的错误传递的数据
     */
    private List<GlobalErrorParam> errorParams;

    /**
     * 时间戳
     */
    private String timestamp;

    public static GlobalErrorResult failure(Throwable e) {
        return GlobalErrorResult.failure(ResultCode.UNKNOWN_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static GlobalErrorResult failure(ResultCode resultCode, Throwable e) {
        return GlobalErrorResult.failure(resultCode, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static GlobalErrorResult failure(ResultCode resultCode, Throwable e, HttpStatus httpStatus, List<GlobalErrorParam> errorParams) {
        GlobalErrorResult globalErrorResult = GlobalErrorResult.failure(resultCode, e, httpStatus);
        globalErrorResult.setErrorParams(errorParams);
        return globalErrorResult;
    }

    public static GlobalErrorResult failure(ResultCode resultCode, Throwable e, HttpStatus httpStatus) {
        GlobalErrorResult result = new GlobalErrorResult();
        result.setHttpStatus(httpStatus.value());
        result.setHttpError(httpStatus.getReasonPhrase());

        result.setCode(resultCode.code());
        result.setMsg(resultCode.message());

        result.setRequestPath(RequestContextHolderUtil.getRequest().getRequestURI());

        result.setExceptionName(e.getClass().getName());
        result.setExceptionMsg(e.getMessage());

        result.setTimestamp(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")
        ));
        return result;
    }
}
