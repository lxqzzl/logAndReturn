package com.test.ge.common.handler;

import com.test.ge.common.returns.GlobalErrorParam;
import com.test.ge.common.returns.GlobalErrorResult;
import com.test.ge.common.returns.ResultCode;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局异常处理
 *
 * @author lxq
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
	// 匹配引号的正则表达式

	/**
	* 全局异常
	* @param e UnauthorizedException
	* @return GlobalErrorResult
	*/
	@ExceptionHandler(Exception.class)
    public GlobalErrorResult globalDefaultExceptionHandler(Exception e){
        return GlobalErrorResult.failure(e);
    }

	/**
	 * 参数校验错误
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public GlobalErrorResult MethodArgumentNotValidHandler(MethodArgumentNotValidException e) {
		//按需重新封装需要返回的错误信息
		List<GlobalErrorParam> globalErrorParams = new ArrayList<>();
		//解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			GlobalErrorParam globalErrorParam = GlobalErrorParam.builder()
					.fieldName(fieldError.getField())
					.rejectedValue(fieldError.getRejectedValue())
					.errorMsg(fieldError.getDefaultMessage()).build();
			globalErrorParams.add(globalErrorParam);
		}
		return GlobalErrorResult.failure(ResultCode.PARAM_VALIDATE_ERROR, e ,HttpStatus.BAD_REQUEST, globalErrorParams);
	}

    /**
     * 请求体参数错误（post）
     * @param e HttpMessageNotReadableException
	 * @return GlobalErrorResult
	 */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public GlobalErrorResult HttpMessageNotReadableHandler(HttpMessageNotReadableException e){
		List<GlobalErrorParam> globalErrorParams = new ArrayList<>();
		// 获取原本的错误信息
		String errorMsg = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
        errorMsg = errorMsg != null ? errorMsg:"";

		// 匹配获取错误值
		Pattern pattern = Pattern.compile("\"(.*?)\"");
		Matcher matcher = pattern.matcher(errorMsg);
		String errorValue = null;
		if(matcher.find()){
			errorValue = matcher.group().trim().replace("\"","");
		}

		globalErrorParams.add(GlobalErrorParam.builder()
				.rejectedValue(errorValue)
				.errorMsg(errorMsg).build());
        return GlobalErrorResult.failure(ResultCode.REQUEST_PARAM_TYPE_ERROR, e.getRootCause(), HttpStatus.BAD_REQUEST, globalErrorParams);
    }


    /**
     * 请求参数异常(get)
     * @param e MethodArgumentTypeMismatchException
     * @return GlobalErrorResult
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public GlobalErrorResult methodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException e) {
		List<GlobalErrorParam> globalErrorParams = new ArrayList<>();
		// 获取原本的错误信息
		String errorMsg = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
		errorMsg = errorMsg != null ? errorMsg:"";

		// 匹配获取错误值
		Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(errorMsg);
		String errorValue = null;
		if(matcher.find()){
			errorValue = matcher.group().trim().replace("\"","");
		}

		globalErrorParams.add(GlobalErrorParam.builder()
				.rejectedValue(errorValue)
				.errorMsg(errorMsg).build());
		return GlobalErrorResult.failure(ResultCode.REQUEST_PARAM_TYPE_ERROR, e.getRootCause(), HttpStatus.BAD_REQUEST, globalErrorParams);
    }

    /**
     * 找不到请求内容
     * @param e NotFoundException
     * @return GlobalErrorResult
     */
    @ExceptionHandler(NotFoundException.class)
    public GlobalErrorResult notFoundExceptionHandler(
            NotFoundException e){
        return GlobalErrorResult.failure(ResultCode.RESOURCES_NOT_FOUND, e, HttpStatus.NOT_FOUND);
    }

    /**
     * 请求方式异常
     * @param e HttpRequestMethodNotSupportedException
     * @return GlobalErrorResult
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public GlobalErrorResult httpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
		return GlobalErrorResult.failure(ResultCode.REQUEST_METHOD_ERROR, e, HttpStatus.METHOD_NOT_ALLOWED);
    }
//
//    /**
//     * 登录验证失败
//     * @param ex IncorrectCredentialsException
//     * @return
//     */
//    @ExceptionHandler(IncorrectCredentialsException.class)
//    public Result authenticationExceptionHandler(
//            IncorrectCredentialsException ex){
//        logger.error("登录失败，{}", ex.getMessage());
//        if(ex.getMessage().contains("loginTypeEnum=Client")) {
//            return Result.error(40011, "登录失败,原因：用户名密码有误");
//        }
//        if(ex.getMessage().contains("loginTypeEnum=Sms")) {
//            return Result.error(40012, "登录失败,原因：验证码有误");
//        }
//        if(ex.getMessage().contains("loginTypeEnum=Wechat")) {
//            return Result.error(40013, "登录失败,原因：微信code有误");
//        }
//        return Result.error(40010, "登录失败");
//    }
//
//    /**
//     * 登录验证失败
//     * @param ex UnknownAccountException
//     * @return
//     */
//    @ExceptionHandler(UnknownAccountException.class)
//    public Result unknownAccountExceptionHandler(
//            UnknownAccountException ex){
//        logger.error("登录失败，{}", ex.getMessage());
//        return Result.error(40015, "未创建用户");
//    }
//
//    /**
//     * 登录验证失败
//     * @param ex SocketTimeoutException
//     * @return
//     */
//    @ExceptionHandler(SocketTimeoutException.class)
//    public void socketTimeoutExceptionnHandler(
//            SocketTimeoutException ex){
//        logger.error("网络通讯超时，错误原因：{}", ex.getMessage());
//    }
}
