package model;

import tm.AfterBarCartTm;
import tm.AfterKitchenCartTm;

import java.util.ArrayList;

public class Order {
    private String id;
    private String custId;
    private String oType;
    private String oDate;
    private String oTime;
    private ArrayList<AfterKitchenCartTm> afterKitchenCartTms;
    private ArrayList<AfterBarCartTm> afterBarCartTms;


    public Order() {
    }

    public Order(String id, String custId, String oType, String oDate, String oTime, ArrayList<AfterKitchenCartTm> afterKitchenCartTms, ArrayList<AfterBarCartTm> afterBarCartTms) {
        this.id = id;
        this.custId = custId;
        this.oType = oType;
        this.oDate = oDate;
        this.oTime = oTime;
        this.afterKitchenCartTms = afterKitchenCartTms;
        this.afterBarCartTms = afterBarCartTms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getoType() {
        return oType;
    }

    public void setoType(String oType) {
        this.oType = oType;
    }

    public String getoDate() {
        return oDate;
    }

    public void setoDate(String oDate) {
        this.oDate = oDate;
    }

    public String getoTime() {
        return oTime;
    }

    public void setoTime(String oTime) {
        this.oTime = oTime;
    }

    public ArrayList<AfterKitchenCartTm> getAfterKitchenCartTms() {
        return afterKitchenCartTms;
    }

    public void setAfterKitchenCartTms(ArrayList<AfterKitchenCartTm> afterKitchenCartTms) {
        this.afterKitchenCartTms = afterKitchenCartTms;
    }

    public ArrayList<AfterBarCartTm> getAfterBarCartTms() {
        return afterBarCartTms;
    }

    public void setAfterBarCartTms(ArrayList<AfterBarCartTm> afterBarCartTms) {
        this.afterBarCartTms = afterBarCartTms;
    }
}