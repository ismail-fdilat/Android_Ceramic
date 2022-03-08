package com.shrinkcom.alsaadceramicapp.pojo;

public class ProductModel {

    String uid;
    String pid;
    String pname;
    String pimage;
    String pcolor;
    String pprice="";
    String psize;
    String pquantity;
    String desp;
    String sellerid;
    String sku;
    String size_name;
    String mainQty;

    public String getMainQty() {
        return mainQty;
    }

    public void setMainQty(String mainQty) {
        this.mainQty = mainQty;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getDesp() {
        return desp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getPcolor() {
        return pcolor;
    }

    public void setPcolor(String pcolor) {
        this.pcolor = pcolor;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPsize() {
        return psize;
    }

    public void setPsize(String psize) {
        this.psize = psize;
    }



    public String getPquantity() {
        return pquantity;
    }

    public void setPquantity(String pquantity) {
        this.pquantity = pquantity;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "uid='" + uid + '\'' +
                ", pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", pimage='" + pimage + '\'' +
                ", pcolor='" + pcolor + '\'' +
                ", pprice='" + pprice + '\'' +
                ", psize='" + psize + '\'' +
                ", pquantity='" + pquantity + '\'' +
                ", desp='" + desp + '\'' +
                ", sellerid='" + sellerid + '\'' +
                ", sku='" + sku + '\'' +
                ", size_name='" + size_name + '\'' +
                '}';
    }
}
