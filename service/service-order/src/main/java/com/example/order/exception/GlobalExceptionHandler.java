package com.example.order.exception;

import org.springframework.web.bind.annotation.*;

//@ResponseBody
//@ControllerAdvice

@RestControllerAdvice // 全局异常处理器
public class GlobalExceptionHandler {

//    @ExceptionHandler(Throwable.class)
//    public String error(Throwable e) {
//        return "Error: " + e.getMessage();
//    }

}
