package me.alfredobejarano.myreceipts;

/**
 * Created by jacorona on 11/10/15.
 */
public class Receipt {

    private int id;
    private String emitter;
    private String receptor;
    private String date;
    private double importQuantity;
    private double subtotalQuantity;
    private double ivaQuantity;
    private double ivaRetQuantiy;
    private double isrQuantity;
    private double totalQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Receipt(int id, String emitter, String receptor, String date, double importQuantity, double subtotalQuantity, double ivaQuantity, double ivaRetQuantiy, double isrQuantity, double totalQuantity) {
        this.id = id;
        this.emitter = emitter;
        this.receptor = receptor;
        this.date = date;
        this.importQuantity = importQuantity;
        this.subtotalQuantity = subtotalQuantity;
        this.ivaQuantity = ivaQuantity;
        this.ivaRetQuantiy = ivaRetQuantiy;
        this.isrQuantity = isrQuantity;
        this.totalQuantity = totalQuantity;
    }

    public Receipt(String emitter, String receptor, String date, double importQuantity, double subtotalQuantity, double ivaQuantity, double ivaRetQuantiy, double isrQuantity, double totalQuantity) {
        this.emitter = emitter;
        this.receptor = receptor;
        this.date = date;
        this.importQuantity = importQuantity;
        this.subtotalQuantity = subtotalQuantity;
        this.ivaQuantity = ivaQuantity;
        this.ivaRetQuantiy = ivaRetQuantiy;
        this.isrQuantity = isrQuantity;
        this.totalQuantity = totalQuantity;
    }
}
