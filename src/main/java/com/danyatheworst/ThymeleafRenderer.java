package com.danyatheworst;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThymeleafRenderer {
    private Writer writer;
    private WebContext webContext;
    private TemplateEngine engine;

    private ThymeleafRenderer() {}

    private ThymeleafRenderer(TemplateEngine engine, WebContext webContext, Writer writer) {
        this.webContext = webContext;
        this.writer = writer;
        this.engine = engine;
    }

    public static Builder fromRequest(HttpServletRequest request, HttpServletResponse response) {
        return new Builder(request, response);
    }

    public void render(String template) {
        engine.process(template, webContext, writer);
    }

    public static void renderFromRequest(String template, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fromRequest(request, response).build().render(template);
    }

    public static class Builder {
        private final HttpServletRequest request;
        private final HttpServletResponse response;
        private final Map<String, Object> contextVariables = new LinkedHashMap<>();

        private Builder(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }

        public Builder addVariableToContext(String name, Object object) {
            this.contextVariables.put(name, object);
            return this;
        }

        public ThymeleafRenderer build() throws IOException {
            TemplateEngine engine = buildTemplateEngine(this.request.getServletContext());
            WebContext webContext = buildWebContext(this.request, this.response, this.request.getServletContext());

            for (Map.Entry<String, Object> entry : this.contextVariables.entrySet()) {
                webContext.setVariable(entry.getKey(), entry.getValue());
            }

            return new ThymeleafRenderer(engine, webContext, this.response.getWriter());
        }

        private WebContext buildWebContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
            JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(servletContext);
            IServletWebExchange webExchange = application.buildExchange(request, response);

            return new WebContext(webExchange);
        }

        private TemplateEngine buildTemplateEngine(ServletContext context) {
            IWebApplication application = JakartaServletWebApplication.buildApplication(context);
            ITemplateResolver templateResolver = buildTemplateResolver(application);
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
            return templateEngine;
        }

        private ITemplateResolver buildTemplateResolver(IWebApplication application) {
            WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
            templateResolver.setTemplateMode(TemplateMode.HTML);
            templateResolver.setPrefix("/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setCharacterEncoding("UTF-8");
            return templateResolver;
        }
    }
}