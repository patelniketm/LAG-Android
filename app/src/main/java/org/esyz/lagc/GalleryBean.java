package org.esyz.lagc;

import java.util.ArrayList;

/**
 * Created by Sonu on 16-04-2017.
 */

public class GalleryBean {
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    private String eventName;

    public String getEventlink() {
        return eventlink;
    }

    public void setEventlink(String eventlink) {
        this.eventlink = eventlink;
    }

    private String eventlink;

    public ArrayList<ImageBean> getList() {
        return list;
    }

    public void setList(ArrayList<ImageBean> list) {
        this.list = list;
    }

    private ArrayList<ImageBean> list;
}
