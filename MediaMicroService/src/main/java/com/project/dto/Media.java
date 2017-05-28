package com.project.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Media {

    public static enum Type{
        IMG,VID,YTB
    }
    public static enum Src{
        THUMB,ORIG
    }
    private String id;
    private String res;
    private String name;
    private List<Media> ver;
    private String crBy;
    private Date crDate;
    private Map<String,String> meta;
    private Type typ;
    private Src src;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRes() {
        return res;
    }
    public void setRes(String res) {
        this.res = res;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCrBy() {
        return crBy;
    }
    public void setCrBy(String crBy) {
        this.crBy = crBy;
    }
    public Date getCrDate() {
        return crDate;
    }
    public void setCrDate(Date crDate) {
        this.crDate = crDate;
    }
    public Map<String, String> getMeta() {
        return meta;
    }
    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }
    public Type getTyp() {
        return typ;
    }
    public void setTyp(Type typ) {
        this.typ = typ;
    }
    public Src getSrc() {
        return src;
    }
    public void setSrc(Src src) {
        this.src = src;
    }
    public List<Media> getVer() {
        return ver;
    }
    public void setVer(List<Media> ver) {
        this.ver = ver;
    }
}
