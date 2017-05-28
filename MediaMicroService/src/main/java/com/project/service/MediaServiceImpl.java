package com.project.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import com.project.dao.MediaDao;
import com.project.dao.MediaDaoImpl;
import com.project.dto.Media;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaDaoImpl mediaDao;

    @Value("${medialib.path}")
    private String uploadPath;

    @Override
    public Media save(String uid, MultipartFile file, Media media) throws Exception {
        String fid = RandomStringUtils.randomAlphanumeric(5);
        Media iMedia = new Media();
        iMedia.setTyp(media.getTyp());
        switch (media.getTyp()) {
            case IMG:

                processImage(file, fid);
                iMedia.setName(file.getOriginalFilename());
                break;
            case VID:
                processVideo(file, fid);
                iMedia.setName(file.getOriginalFilename());
                break;
            case YTB:
                URIBuilder uriBuilder = new URIBuilder(media.getMeta().get("link"));
                List<NameValuePair> urlParameters = uriBuilder.getQueryParams();
                String videoId = null;
                for (NameValuePair pair : urlParameters) {
                    if (pair.getName().equals("v")) {
                        videoId = pair.getValue();
                        break;
                    }
                }
                URL url = new URL("http://img.youtube.com/vi/" + videoId + "/1.jpg");
                InputStream in = new BufferedInputStream(url.openStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(uploadPath + "_thumb_" + fid+".jpg"));

                for (int i; (i = in.read()) != -1;) {
                    out.write(i);
                }
                in.close();
                out.close();
                iMedia.setName(videoId);
                iMedia.setMeta(media.getMeta());
                break;
        }
        iMedia.setCrBy(uid);
        iMedia.setCrDate(new Date());
        iMedia.setRes(fid);
        return mediaDao.save(iMedia);
    }

    @Override
    public List<Media> search(String uid, Media.Type type) {
        return mediaDao.search(uid, type);
    }

    private void processVideo(MultipartFile file, String fid) throws Exception {

        File vinputMedia = new File(uploadPath + fid + "_" + file.getOriginalFilename());
        file.transferTo(vinputMedia);

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(vinputMedia);
        grabber.start();
        int i=0;
        Frame frame=null;
        //most of the time starting frames are black so skip
        while(i++<50)
        {
            frame = grabber.grabImage();

        }
        Java2DFrameConverter conv = new Java2DFrameConverter();
        BufferedImage vthumb = conv.convert(frame);
        vthumb = Scalr.resize(vthumb, Method.QUALITY, Mode.FIT_TO_WIDTH, 120, 120);
        ImageIO.write(vthumb, "jpg", new File(uploadPath + "_thumb_" + fid+".jpg"));

        grabber.stop();
    }

    private void processImage(MultipartFile file, String fid) throws Exception {

        File inputMedia = new File(uploadPath + fid + "_" + file.getOriginalFilename());
        file.transferTo(inputMedia);

        BufferedImage bi = ImageIO.read(inputMedia);
        bi = Scalr.resize(bi, Method.QUALITY, Mode.FIT_TO_WIDTH, 120, 120);
        String ext = StringUtils.endsWith(file.getOriginalFilename().toLowerCase(), "png") ? "png" : "jpg";
        File thumb = new File(uploadPath + "_thumb_" + fid+"."+ext);
        ImageIO.write(bi, ext, thumb);
    }
    @Override
    public Media editMedia(String uid,MultipartFile file,Media media) throws Exception{
        Media existingM = mediaDao.searchById(uid, media.getId());
        Media iMedia = new Media();
        iMedia.setTyp(media.getTyp());
        String fid = RandomStringUtils.randomAlphanumeric(5);
        switch (existingM.getTyp()) {
            case IMG:

                processImage(file, fid);
                iMedia.setName(file.getOriginalFilename());
                break;
            case VID:
                processVideo(file, fid);
                iMedia.setName(file.getOriginalFilename());
                break;
            case YTB:
                URIBuilder uriBuilder = new URIBuilder(media.getMeta().get("link"));
                List<NameValuePair> urlParameters = uriBuilder.getQueryParams();
                String videoId = null;
                for (NameValuePair pair : urlParameters) {
                    if (pair.getName().equals("v")) {
                        videoId = pair.getValue();
                        break;
                    }
                }
                URL url = new URL("http://img.youtube.com/vi/" + videoId + "/1.jpg");
                InputStream in = new BufferedInputStream(url.openStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(uploadPath + "_thumb_" + fid+".jpg"));

                for (int i; (i = in.read()) != -1;) {
                    out.write(i);
                }
                in.close();
                out.close();
                iMedia.setName(videoId);
                iMedia.setMeta(media.getMeta());
                break;
        }
        iMedia.setCrBy(uid);
        iMedia.setCrDate(new Date());
        iMedia.setRes(fid);
        iMedia.setId(existingM.getId());
        List<Media> existingVersions = existingM.getVer();
        existingM.setVer(null);
        if(existingVersions==null){
            existingVersions = new ArrayList<Media>();
        }
        existingVersions.add(existingM);
        iMedia.setVer(existingVersions);
        return mediaDao.save(iMedia);

    }
}
