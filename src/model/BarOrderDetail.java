package model;

import tm.AfterBarCartTm;
import tm.AfterKitchenCartTm;

import java.util.ArrayList;

public class BarOrderDetail {

    private ArrayList<AfterBarCartTm> afterBarCartTms;

    public ArrayList<AfterBarCartTm> getAfterBarCartTms() {
        return afterBarCartTms;
    }

    public void setAfterBarCartTms(ArrayList<AfterBarCartTm> afterBarCartTms) {
        this.afterBarCartTms = afterBarCartTms;
    }

    public BarOrderDetail(ArrayList<AfterBarCartTm> afterBarCartTms) {
        this.afterBarCartTms = afterBarCartTms;
    }
}
