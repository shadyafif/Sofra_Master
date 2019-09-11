
package com.example.eng_shady.MyResturant.models.Resturant.ResturantCommission;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("commissions")
    @Expose
    private int commissions;
    @SerializedName("payments")
    @Expose
    private int payments;
    @SerializedName("net_commissions")
    @Expose
    private int netCommissions;
    @SerializedName("commission")
    @Expose
    private String commission;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCommissions() {
        return commissions;
    }

    public void setCommissions(int commissions) {
        this.commissions = commissions;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public int getNetCommissions() {
        return netCommissions;
    }

    public void setNetCommissions(int netCommissions) {
        this.netCommissions = netCommissions;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

}
