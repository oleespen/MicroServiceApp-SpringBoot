package com.project.dao;

import com.project.dto.Media;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MediaDao implements MediaDaoImpl {

    private SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Media.class)
            .buildSessionFactory();

    Session session = factory.getCurrentSession();

    @Override
    public Media save(Media media) {
        session.save(media);
        return media;
    }

    @Override
    public List<Media> search(String uid, Media.Type type) {

        List<Media> list = (List<Media>) session.createQuery("from media").list();

        return list;

    }

    @Override
    public Media searchById(String uid, String mid) {

        Media media = (Media) session.createQuery("select id from media where id = uid");
        return media;
    }
}
