package com.sojeong.kakaopayproject;

public class ImageData {
    private String collection;
    private String thumbnail_url;
    private String image_url;
    private String width;
    private String height;
    private String display_sitename;
    private String doc_url;
    private String datetime;

    public ImageData() {
    }

    public ImageData(String collection, String thumbnail_url, String image_url, String width, String height, String display_sitename, String doc_url, String datetime) {
//        Log.e("sojeong","collection: "+collection+"\nthumnail_url: "+thumbnail_url+"\nimage_url: "+image_url+"\nwidht: " +
//                width+"\nheight: "+height+"\ndisplay_sitename: "+display_sitename+"\ndoc_url: "+doc_url+"\ndatetime: "+datetime);

        this.collection = collection;
        this.thumbnail_url = thumbnail_url;
        this.image_url = image_url;
        this.width = width;
        this.height = height;
        this.display_sitename = display_sitename;
        this.doc_url = doc_url;
        this.datetime = datetime;
    }

    public String getCollection() {
        return collection;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getDisplay_sitename() {
        return display_sitename;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public String getDatetime() {
        return datetime;
    }

}
