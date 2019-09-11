
package com.example.eng_shady.MyResturant.models.Resturant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResturantDtails {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DataResturant data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataResturant getData() {
        return data;
    }

    public void setData(DataResturant data) {
        this.data = data;
    }

}
