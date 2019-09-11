package com.example.eng_shady.MyResturant.models.Client.Register;

import com.example.eng_shady.MyResturant.models.Client.RegisterData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ClientRegister {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private com.example.eng_shady.MyResturant.models.Client.RegisterData data;

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

    public RegisterData getData() {
        return data;
    }

    public void setData(RegisterData data) {
        this.data = data;
    }

}
