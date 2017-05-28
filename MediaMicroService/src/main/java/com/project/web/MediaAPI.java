package com.project.web;

import java.util.List;

import com.project.dto.Media;
import com.project.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/media")
public class MediaAPI {

    @Autowired
    private MediaService mediaService;

    @RequestMapping(method = RequestMethod.POST)
    public void mediaUpload(@RequestParam(name="file",required=false) MultipartFile[] files, Media media) throws Exception {
        if(media.getTyp()==Media.Type.YTB){
            mediaService.save("123", null, media);
        }else{
            for (MultipartFile file : files) {
                mediaService.save("123", file, media);
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Media> listMedia() {

        return mediaService.search("123", Media.Type.IMG);
    }
    @RequestMapping(value="/{id}",method = RequestMethod.POST)
    public Media ediatMedia(@RequestParam(name="file",required=false) MultipartFile file, Media media) throws Exception{

        return mediaService.editMedia("123", file, media);
    }

}
