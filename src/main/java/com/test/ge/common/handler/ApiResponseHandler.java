package com.test.ge.common.handler;

import com.test.ge.common.returns.PlatformResult;
import com.test.ge.common.returns.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回类型封装处理
 *
 * @author lxq
 */

@RestControllerAdvice
public class ApiResponseHandler implements ResponseBodyAdvice {

    /** 不封装的uri列表 */
    private List<String> noEncapsulationUriList = new ArrayList<String>(){{
        // swagger文档请求不封装
        add("/swagger-resources/**");
        add("/v2/api-docs");
        add("/error");
    }};

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 防止重复封装
        if(body instanceof Result){
            return body;
        }
        if(uriIsEncapsulation(request.getURI().getPath())){
            return body;
        }
        return PlatformResult.success(body);
    }

    /**
     * 判断请求的路径是否是不需要封装的路径
     * @param requestUri 请求的uri
     * @return Boolean
     */
    public Boolean uriIsEncapsulation(String requestUri){
        for(String noEncapsulationUri : noEncapsulationUriList){
            noEncapsulationUri = noEncapsulationUri.replace("/**","");
            if(requestUri.contains(noEncapsulationUri)) {
                return true;
            }
        }
        return false;
    }
}
