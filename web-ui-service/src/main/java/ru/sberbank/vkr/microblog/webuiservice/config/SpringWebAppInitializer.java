package ru.sberbank.vkr.microblog.webuiservice.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.context.annotation.Configurations;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Creates context object
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();

        // Registers annotated configurations class
        ctx.register(Configurations.class);

        // Sets ContextLoaderListener to servletContext
        servletContext.addListener(new ContextLoaderListener(ctx));

        // Passes servlet context to context instance
        ctx.setServletContext(servletContext);

        //Registers dispatch servlet and passes context instance
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));

        //Maps URL pattern
        servlet.addMapping("/");

        //Sets creation priority
        servlet.setLoadOnStartup(1);

        // UTF8 Character Filter.
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);

        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
    }

}