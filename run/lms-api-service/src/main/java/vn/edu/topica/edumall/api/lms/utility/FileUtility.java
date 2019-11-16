package vn.edu.topica.edumall.api.lms.utility;

import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.io.IURLProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.edumall.api.lms.dto.FileExistDTO;
import vn.edu.topica.edumall.api.lms.dto.MakeFileDTO;
import vn.edu.topica.edumall.data.enumtype.FileTypeEnum;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.data.model.Folder;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.util.*;

@Slf4j
@Component
public class FileUtility {
    static final String VIDEO = "3g2,3gp,avi,flv,h264,m4v,mkv,mov,mp4,mpg,mpeg,rm,swf,vob,wmv";
    static final String IMAGE = "ai,bmp,gif,ico,jpeg,jpg,png,ps,psd,svg,tif,tiff";
    static final String AUDIO = "aif,cda,mid,midi,mp3,mpa,ogg,wav,wma,wpl";

    /**
     * Used to get duplicateFileNameList
     *
     * @param folder
     * @param listFilename
     * @return
     */
    public List<FileExistDTO> getListDuplicateFileName(Folder folder, List<String> listFilename) {
        List<File> filesInFolder = folder.getFiles();
        if (filesInFolder.size() == 0) {
            return null;
        }

        Map<String, Long> fileNameMap = new HashMap();
        for (File file : filesInFolder) {
            fileNameMap.put(file.getName(), file.getId());
        }

        List<FileExistDTO> listFileExist = new ArrayList<>();
        for (String fileName : listFilename) {
            if (fileNameMap.containsKey(fileName.trim())) {
                FileExistDTO fileExistDTO = FileExistDTO.builder().build();
                fileExistDTO.setName(fileName.trim());
                fileExistDTO.setId(fileNameMap.get(fileName.trim()));
                listFileExist.add(fileExistDTO);
            }
        }
        return listFileExist;

    }


    /**
     * Used to check fileName is duplicate
     *
     * @param folder
     * @param fileName
     * @return
     */
    public Long isDuplicateFilename(Folder folder, String fileName) {
        List<File> filesInFolder = folder.getFiles();
        if (filesInFolder.size() == 0) {
            return 0L;
        }

        Map<String, Long> fileNameMap = new HashMap();
        for (File file : filesInFolder) {
            fileNameMap.put(file.getName(), file.getId());
        }
        Long isDuplicateFilename = fileNameMap.get(fileName);
        if (isDuplicateFilename == null) {
            return 0L;
        }
        return isDuplicateFilename;
    }


    public MakeFileDTO makeFile(MultipartFile multipartFile) {
        byte[] bufferedbytes = new byte[8196];
        String fileNameCreated = new Date().getTime() + "_" + multipartFile.getOriginalFilename();
        java.io.File file = new java.io.File(fileNameCreated);
        FileOutputStream outStream;
        int count = 0;
        Integer countByte = 0;
        try {
            BufferedInputStream fileInputStream = new BufferedInputStream(multipartFile.getInputStream());
            outStream = new FileOutputStream(file);
            while ((count = fileInputStream.read(bufferedbytes)) != -1) {
                outStream.write(bufferedbytes, 0, count);
                countByte++;
                log.info("Making file in server........................................" + (countByte*8) + "KB");
            }
            outStream.close();
        } catch (Exception e) {
            log.error(e.toString());
        }

        MakeFileDTO makeFileDTO = MakeFileDTO.builder()
                .file(file)
                .duration(getDuration(fileNameCreated))
                .build();

        return makeFileDTO;
    }

    /**
     * Used to get fileExtension
     *
     * @param fileExtension
     * @return
     */
    public FileTypeEnum getFileType(String fileExtension) {
        List<String> videos = new ArrayList<String>(Arrays.asList(VIDEO.split(",")));
        List<String> images = new ArrayList<String>(Arrays.asList(IMAGE.split(",")));
        List<String> audios = new ArrayList<String>(Arrays.asList(AUDIO.split(",")));

        if (videos.contains(fileExtension)) {
            return FileTypeEnum.VIDEO;
        }
        if (images.contains(fileExtension)) {
            return FileTypeEnum.IMAGE;
        }
        if (audios.contains(fileExtension)) {
            return FileTypeEnum.AUDIO;
        }
        return FileTypeEnum.OTHER;
    }

    public Long getDuration(String fileName) {
        IContainer container = IContainer.make();
        container.open(fileName, IContainer.Type.READ, null);
        long durationSecond = container.getDuration() / 1000 / 1000;
        return durationSecond;
    }
}
