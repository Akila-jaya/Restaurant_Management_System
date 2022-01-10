package model;

public class BarChartM {
    private String date;
    private int total;



    public BarChartM(String date, int total) {
        this.date = date;
        this.total = total;
    }

    public String getdate() {
        return date;
    }

    public void setProduct(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
