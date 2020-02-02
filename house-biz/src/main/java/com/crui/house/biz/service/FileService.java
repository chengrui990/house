package com.crui.house.biz.service;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * VM Args:
 *
 * @author crui
 */
@Service
public class FileService {
    @Value("file.path")
    private String filepath;

    public List<String> getImgPath(List<MultipartFile> files){
        List<String> paths = Lists.newArrayList();
        files.forEach(file -> {
            File localFile = null;
            if (!file.isEmpty()){
                try {
                    localFile = saveToLocal(file,filepath);
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filepath);
                    paths.add(path);
                }catch (Exception e){
                    throw new IllegalArgumentException(e);
                }

            }
        });
        return paths;
    }

    private File saveToLocal(MultipartFile file, String filepath) throws IOException {
        File newFile = new File(filepath + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
        if (!newFile.exists()){
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(),newFile);
        return newFile;
    }
}
