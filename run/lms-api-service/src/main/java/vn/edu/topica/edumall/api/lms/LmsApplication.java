package vn.edu.topica.edumall.api.lms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import reactor.Environment;
import reactor.bus.EventBus;
import vn.edu.topica.edumall.api.lms.upload.queue.*;
import vn.edu.topica.edumall.image_optimize.config.EnableImageOptimize;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;
import vn.edu.topica.edumall.api.rest.config.EnableRestCommonApi;
import vn.edu.topica.edumall.data.config.EnableDataModel;
import vn.edu.topica.edumall.liquibase.config.EnableLiquibaseConfig;
import vn.edu.topica.edumall.locale.config.EnableLocaleModule;
import vn.edu.topica.edumall.s3.config.EnableServiceS3;
import vn.edu.topica.edumall.security.config.EnableSecurityModule;

import static reactor.bus.selector.Selectors.$;

@SpringBootApplication
@EnableDataModel
@EnableDiscoveryClient
@EnableRestCommonApi
@EnableServiceS3
@EnableImageOptimize
@EnableSecurityModule
@EnableLocaleModule
@EnableJpaRepositories("vn.edu.topica.edumall.api.lms.repository")
@EnableLiquibaseConfig
@EnableRetry(proxyTargetClass = true)
@Slf4j
public class LmsApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    private EventBus eventBus;

    @Autowired
    UploadConsumer uploadConsumer;

    @Autowired
    ProcessSendToMediaToolConsumer processSendToMediaToolConsumer;

    @Autowired
    LoggingConsumer loggingConsumer;

    @Autowired
    SendEmailConsumer sendEmailConsumer;

    @Autowired
    UpdateCourseKelleyConsumer updateCourseKelleyConsumer;

    @Autowired
    EmailToTeacherConsumer emailToTeacherConsumer;

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new io.sentry.spring.SentryExceptionResolver();
    }

    @Override
    public void run(String... args) throws Exception {
        eventBus.on($(ConsumerEnum.UPLOADS3), uploadConsumer);
        eventBus.on($(ConsumerEnum.PROCESS_SEND_TO_MEDIA_TOOL), processSendToMediaToolConsumer);
        eventBus.on($(ConsumerEnum.LOG), loggingConsumer);
        eventBus.on($(ConsumerEnum.PROCESS_SEND_EMAIL_WHEN_FAIL_UPLOAD_S3), sendEmailConsumer);
        eventBus.on($(ConsumerEnum.UPDATE_COURSE_KELLEY), updateCourseKelleyConsumer);
        eventBus.on($(ConsumerEnum.SEND_EMAIL_TO_TEACHER), emailToTeacherConsumer);
    }

    public static void main(String[] args) {
        log.info("Start lms api services");
        SpringApplication.run(LmsApplication.class, args);
    }
}
