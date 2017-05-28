package com.project.service;

import com.project.dto.Media;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    public List<Media> search(String uid, Media.Type type);

    Media save(String uid, MultipartFile file, Media media) throws Exception;

    Media editMedia(String uid, MultipartFile file, Media media) throws Exception;
}
