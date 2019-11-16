package vn.edu.topica.edumall.security.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.topica.edumall.data.enumtype.RoleEnum;
import vn.edu.topica.edumall.data.model.*;
import vn.edu.topica.edumall.security.core.model.SSOUserInfo;
import vn.edu.topica.edumall.security.core.service.LmsUserService;
import vn.edu.topica.edumall.security.jdbc.repository.*;

import java.util.Random;


@Service
@Transactional
public class LmsUserServiceImpl implements LmsUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("teacherRepositoryInSecurity")
    private TeacherRepository teacherRepository;

    @Autowired
    @Qualifier("folderRepositoryInSecurity")
    private FolderRepository folderRepository;

    @Value("${teacher.root.folder}")
    private String teacherRootFolder;

    @Autowired
    @Qualifier("warehouseRepositoryInSecurity")
    private WarehouseRepository warehouseRepository;

    @Autowired
    @Qualifier("RoleInfoRepositoryInSecurity")
    RoleInfoRepository roleInfoRepository;

    @Autowired
    @Qualifier("userRoleRepositoryInSecurity")
    UserRoleRepository userRoleRepository;

    public User createTeacher(SSOUserInfo ssoUserInfo, User userFind) {
        if (userFind == null) {
            User user = saveUser(ssoUserInfo);

            Teacher teacher = Teacher.builder()
                    .teacherCode(generateTeacherCode())
                    .user(user)
                    .build();
            teacherRepository.save(teacher);

            Folder folder = Folder.builder()
                    .name(teacherRootFolder)
                    .createdBy(user.getId())
                    .build();
            folderRepository.save(folder);

            WareHouse wareHouse = WareHouse.builder()
                    .teacher(teacher)
                    .folder(folder)
                    .createdBy(user.getId())
                    .build();
            warehouseRepository.save(wareHouse);
            return user;
        }
        return null;
    }

    @Override
    public void updateUserInfo(SSOUserInfo ssoUserInfo, User userFind) {
        userFind.setName(ssoUserInfo.getName());
        userFind.setMobile(ssoUserInfo.getPhoneNumber());
        userFind.setAvatar(ssoUserInfo.getAvatar());
        userRepository.save(userFind);
    }

    public User saveUser(SSOUserInfo ssoUserInfo) {
        User user = User.builder()
                .email(ssoUserInfo.getEmail())
                .name(ssoUserInfo.getName())
                .mobile(ssoUserInfo.getPhoneNumber())
                .avatar(ssoUserInfo.getAvatar())
                .build();
        User userSave = userRepository.save(user);

        RoleInfo roleInfo = roleInfoRepository.findByName(RoleEnum.TEACHER.getRoleName());

        UserRoleId userRoleId = UserRoleId.builder()
                .user(userSave)
                .role(roleInfo)
                .build();

        UserRole userRole = UserRole.builder()
                .id(userRoleId)
                .build();

        userRoleRepository.save(userRole);

        return userSave;
    }

    private String generateTeacherCode() {
        Random rand = new Random();
        String teacherCode = "";

        while(true) {
            int numCode = rand.nextInt(99999) + 1;
            String formattedString = String.format("%05d", numCode);
            teacherCode = "INS6" + formattedString;

            boolean isTeacherExisted = teacherRepository.existsByTeacherCode(teacherCode);

            if(!isTeacherExisted) {
                break;
            }
        }
        return teacherCode;
    }
}
