package vn.edu.topica.edumall.api.lms.service.impl;

import com.netflix.discovery.converters.Auto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.dto.UserDetailDTO;
import vn.edu.topica.edumall.api.lms.dto.UserKellyDTO;
import vn.edu.topica.edumall.api.lms.repository.FolderRepository;
import vn.edu.topica.edumall.api.lms.repository.TeacherRepository;
import vn.edu.topica.edumall.api.lms.repository.UserRepository;
import vn.edu.topica.edumall.api.lms.service.UserService;
import vn.edu.topica.edumall.data.enumtype.RoleEnum;
import vn.edu.topica.edumall.data.model.*;
import vn.edu.topica.edumall.locale.config.Translator;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;
import vn.edu.topica.edumall.security.core.service.LmsUserService;
import vn.edu.topica.edumall.security.jdbc.repository.RoleInfoRepository;
import vn.edu.topica.edumall.security.jdbc.repository.UserRoleRepository;
import vn.edu.topica.edumall.security.jdbc.repository.WarehouseRepository;

import javax.transaction.Transactional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userRepositoryInLmsApi")
    UserRepository userRepository;

    @Value("${teacher.root.folder}")
    private String teacherRootFolder;

    @Autowired
    @Qualifier("RoleInfoRepositoryInSecurity")
    RoleInfoRepository roleInfoRepository;

    @Autowired
    @Qualifier("userRoleRepositoryInSecurity")
    UserRoleRepository userRoleRepository;

    @Autowired
    @Qualifier("warehouseRepositoryInSecurity")
    private WarehouseRepository warehouseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    TeacherRepository teacherRepository;


    @Override
    public UserDetailDTO getUserDetail(Authentication authentication) {

        Long userId = ((UserPrincipal) authentication.getCredentials()).getId();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"User"}),
                    null);
        }

        Folder rootFolder = folderRepository.getFolderByUserId(userId);
        if (rootFolder == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"Folder"}),
                    null);
        }

        UserDetailDTO userDetailDTO = modelMapper.map(user, UserDetailDTO.class);
        userDetailDTO.setTeacherCode(teacherRepository.getTeacherByUserId(user.getId()).getTeacherCode());
        userDetailDTO.setRootFolderId(rootFolder.getId());

        return userDetailDTO;
    }

    @Override
    @Transactional
    public User importUserFromKelly(UserKellyDTO userKellyDTO) {

        if(userRepository.findFirstByEmail(userKellyDTO.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User exists!", null);
        }

        //create user
        User user =  User.builder()
                .email(userKellyDTO.getEmail())
                .name(userKellyDTO.getName())
                .avatar(userKellyDTO.getAvatar())
                .username(userKellyDTO.getUsername())
                .mobile(userKellyDTO.getMobile())
                .kellyId(userKellyDTO.getKellyId()).build();
        User userSave = userRepository.save(user);

        //create role
        RoleInfo roleInfo = roleInfoRepository.findByName(RoleEnum.TEACHER.getRoleName());

        UserRoleId userRoleId = UserRoleId.builder()
                .user(userSave)
                .role(roleInfo)
                .build();

        UserRole userRole = UserRole.builder()
                .id(userRoleId)
                .build();

        userRoleRepository.save(userRole);

        // create teacher
        Teacher teacher = Teacher.builder()
                .teacherCode(generateTeacherCode())
                .user(userSave)
                .build();
        teacherRepository.save(teacher);

        //create root folder
        Folder folder = Folder.builder()
                .name(teacherRootFolder)
                .createdBy(userSave.getId())
                .build();
        folderRepository.save(folder);

        //create warehouse
        WareHouse wareHouse = WareHouse.builder()
                .teacher(teacher)
                .folder(folder)
                .createdBy(userSave.getId())
                .build();
        warehouseRepository.save(wareHouse);

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
