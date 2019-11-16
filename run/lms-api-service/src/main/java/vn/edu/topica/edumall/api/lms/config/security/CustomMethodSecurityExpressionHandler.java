package vn.edu.topica.edumall.api.lms.config.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import vn.edu.topica.edumall.api.lms.repository.*;

@Component
public class CustomMethodSecurityExpressionHandler
        extends DefaultMethodSecurityExpressionHandler {
    private AuthenticationTrustResolver trustResolver =
            new AuthenticationTrustResolverImpl();

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    CourseVersionRepository courseVersionRepository;

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        OwnerSecurityExpressionRoot root =
                new OwnerSecurityExpressionRoot(authentication,
                        fileRepository, folderRepository, courseVersionRepository,
                        chapterRepository, lectureRepository);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(this.trustResolver);
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }
}