package model;

import tm.AfterBarCartTm;
import tm.AfterKitchenCartTm;

import java.util.ArrayList;

public class KitchenOrderDetail {
    private ArrayList<AfterKitchenCartTm> afterKitchenCartTms;

    public KitchenOrderDetail(ArrayList<AfterKitchenCartTm> afterKitchenCartTms) {
        this.afterKitchenCartTms = afterKitchenCartTms;
    }

    public ArrayList<AfterKitchenCartTm> getAfterKitchenCartTms() {
        return afterKitchenCartTms;
    }

    public void setAfterKitchenCartTms(ArrayList<AfterKitchenCartTm> afterKitchenCartTms) {
        this.afterKitchenCartTms = afterKitchenCartTms;
    }




}
