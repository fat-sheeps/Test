//package org.example.path;
//
//import io.undertow.server.HttpHandler;
//import io.undertow.server.handlers.PathHandler;
//import io.undertow.servlet.ServletExtension;
//import io.undertow.servlet.api.DeploymentInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import javax.servlet.ServletContext;
//import java.util.Map;
//
//@Slf4j
//public class NonBlockingServletExtension implements ServletExtension {
//    @Override
//    public void handleDeployment(DeploymentInfo deploymentInfo, final ServletContext servletContext) {
//        deploymentInfo.addInitialHandlerChainWrapper(handler -> {
//            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//            Map<String, Object> handlers = ctx.getBeansWithAnnotation(HandlerPath.class);
//
//            PathHandler rootHandler = new PathHandler();
//            rootHandler.addPrefixPath( "/", handler);
//            for (Map.Entry<String, Object> handlerEntry : handlers.entrySet()) {
//                if (handlerEntry.getValue() instanceof HttpHandler) {
//                    HttpHandler httpHandler = (HttpHandler) handlerEntry.getValue();
//                    String path = httpHandler.getClass().getAnnotation(HandlerPath.class).path();
//                    log.info("@HandlerPath:{}", path);
//                    rootHandler.addPrefixPath(path, httpHandler);
//
//                }
//
//            }
//            return rootHandler;
//        });
//    }
//}
//
