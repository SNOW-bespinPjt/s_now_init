package com.btc.snow.user.meeting.study;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Log4j2
@Service
public class StudyUploadFileService {

    public String upload(MultipartFile file, String id) {
        log.info("upload()");

        // 저장 유무
        boolean result = false;

        // File 저장
        String fileOriName = file.getOriginalFilename();
        String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
        String uploadDir = "c:\\snow\\user\\meeting";

        UUID uuid = UUID.randomUUID();
        String uniqueName = uuid.toString().replace("-", "");

        File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);

        if (!saveFile.exists())
            saveFile.mkdirs();

        try {
            file.transferTo(saveFile);

            result = true;

        } catch (Exception e) {
            e.printStackTrace();

        }

        // 성공시 저장
        if (result == true) {
            log.info("FILE UPLOAD SUCCESS!!");

            return uniqueName + fileExtension;

        } else {
            log.info("FILE UPLOAD FAIL!!");

            return null;

        }

    }
}
