package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String VIEW_ERROR = "error";

    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleXXException(HttpClientErrorException ex) {
        logger.error("log HttpClientErrorException: ", ex);

        return getModelAndViewForError(ex);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ModelAndView handleXXException(HttpServerErrorException ex) {
        logger.error("log HttpServerErrorException: ", ex);
        return getModelAndViewForError(ex);
    }

    // catch unknown error
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        logger.error("log unknown error", ex);
        ModelAndView modelAndView = new ModelAndView(VIEW_ERROR);
        modelAndView.addObject("errorMsg", ex.getMessage());
        return modelAndView;
    }

    private ModelAndView getModelAndViewForError(HttpStatusCodeException ex) {
        ModelAndView modelAndView = new ModelAndView(VIEW_ERROR);
        modelAndView.addObject("errorMsg", ex.getMessage());
        modelAndView.addObject("statusCode", ex.getStatusCode());
        modelAndView.addObject("statusText", ex.getStatusText());
        return modelAndView;
    }
}
