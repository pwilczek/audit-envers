package name.wilu.auditenvers;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Aspect @Service
class AuditAspect {
    //
    @Before(value = "@annotation(org.springframework.transaction.annotation.Transactional)")
    public void before(JoinPoint jp) {
        AuditContextProvider.get().operation(jp.getSignature().toShortString());
    }
}
