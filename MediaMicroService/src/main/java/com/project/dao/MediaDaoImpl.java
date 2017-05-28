package com.project.dao;

import com.project.dto.Media;

import java.util.List;

public interface MediaDaoImpl {

    Media save(Media media);

    List<Media> search(String uid, Media.Type type);

    Media searchById(String uid, String mid);
}
