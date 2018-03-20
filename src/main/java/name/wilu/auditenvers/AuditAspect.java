package name.wilu.auditenvers;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Aspect @Service
class AuditAspect {
    //
    private final AuditContext ctx;
    //
    AuditAspect(AuditContext ctx) {this.ctx = ctx;}

    @Before(value = "@annotation(org.springframework.transaction.annotation.Transactional)")
    public void before(JoinPoint jp) {
        ctx.operation("bingo");
    }
}
