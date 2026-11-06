package com.de200118.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public String handleException(Exception ex, Model model) {
        log.error("Unhandled exception while processing request", ex);
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        return "error-page";
    }
}
