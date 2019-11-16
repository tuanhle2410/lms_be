package vn.edu.topica.edumall.api.lms.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.bus.Event;
import reactor.bus.EventBus;
import vn.edu.topica.edumall.api.lms.repository.UserRepository;
import vn.edu.topica.edumall.api.lms.upload.queue.ConsumerEnum;
import vn.edu.topica.edumall.data.model.UserActivityLog;
import vn.edu.topica.edumall.security.core.exception.BadRequestException;


import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    @Autowired
    EventBus eventBus;

    @Autowired
    UserRepository userRepository;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("within(vn.edu.topica.edumall.api.lms.controller..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Before("applicationPackagePointcut() && springBeanPointcut()")
    public void logBefore(JoinPoint joinPoint) {

        try {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            String controller = joinPoint.getSignature().getDeclaringTypeName();
            String action = joinPoint.getSignature().getName();
            String method = request.getMethod();
            String email = request.getUserPrincipal().getName();
            int userId = (int) userRepository.findFirstByEmail(email).getId();
            String browser = request.getHeader("User-Agent");
            String params = Arrays.toString(toJsonParams(joinPoint));

            String remoteAddr = "";
            if (request != null) {
                remoteAddr = request.getHeader("X-FORWARDED-FOR");
                if (remoteAddr == null || "".equals(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            }

            UserActivityLog userActivityLog = UserActivityLog.builder()
                    .userId(userId)
                    .action(action)
                    .browser(browser)
                    .controller(controller)
                    .ipAddress(remoteAddr)
                    .params(params)
                    .method(method)
                    .build();

            eventBus.notify(ConsumerEnum.LOG, Event.wrap(userActivityLog));


        } catch (HttpClientErrorException e) {
            System.out.println("There is a client error!");
        } catch (HttpServerErrorException e) {
            System.out.println("There is a server error!");
        } catch (BadRequestException e) {
            System.out.println("There is something wrong in your request!");
        } catch (NullPointerException e){
            System.out.println("Only accept PUT, POST, DELETE request!");;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isDTO(Object object) {
        Class<?> clazz = object.getClass();
        System.out.println(clazz.toString());
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("LoggingDTO")) {
                System.out.println(annotation.annotationType().getSimpleName());
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String [] toJsonParams(JoinPoint joinPoint) {
        String [] arrayJsonParams = new String[joinPoint.getArgs().length];
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            for (int i=0; i < joinPoint.getArgs().length; i++) {
                if(isDTO(joinPoint.getArgs()[i])) {
                    arrayJsonParams[i] = (objectMapper.writeValueAsString(joinPoint.getArgs()[i]));
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return arrayJsonParams;
    }


}
