package name.wilu.auditenvers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Service
@Scope(value = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
class AuditContext {
    //
    private String operation;

    AuditContext operation(String operation) {
        this.operation = operation;
        return this;
    }

    String operation() {
        return operation;
    }
}

@Service
class AuditContextProvider implements ApplicationContextAware {
    //
    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public static AuditContext get() {
        return ctx.getBean(AuditContext.class);
    }
}