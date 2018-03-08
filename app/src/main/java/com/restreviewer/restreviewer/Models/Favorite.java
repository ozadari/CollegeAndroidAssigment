package com.restreviewer.restreviewer.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ozadari on 03/2018.
 */

public class Favorite implements Serializable{
    private String id;
    private String UID;
    private String RID;

    public Favorite() {
    }

    public Favorite(String uid, String rid) {
        this.UID = uid;
        this.RID = rid;
    }


    public String getUID() {
        return UID;
    }

    public void setUID(String uid) {
        this.UID = uid;
    }

    public String getRID() {
        return RID;
    }

    public void setRID(String rid) {
        this.RID = rid;
    }

    public String getId() { return id;}

    public void setId(String ID) { this.id = ID;}

}
