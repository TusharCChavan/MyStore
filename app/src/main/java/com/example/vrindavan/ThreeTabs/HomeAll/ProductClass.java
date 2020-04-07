package com.example.vrindavan.ThreeTabs.HomeAll;

public class ProductClass {
   public String pname,pid,pdiscription,ptype,price,pquantity,pdate,ptime,pdiscount,psnap,pactivation;

    public ProductClass(String pname, String pid,String pdiscription,String ptype,String price, String pquantity, String pdate, String ptime, String pdiscount, String psnap, String pactivation) {
        this.pname = pname;
        this.pid = pid;
        this.pdiscription = pdiscription;
        this.ptype = ptype;
        this.price = price;
        this.pquantity = pquantity;
        this.pdate = pdate;
        this.ptime = ptime;
        this.pdiscount = pdiscount;
        this.psnap = psnap;
        this.pactivation = pactivation;


    }

    public ProductClass() {
    }

    public String getPname() {
        return pname;
    }

    public String getPid() {
        return pid;
    }

    public String getPdiscription() {
        return pdiscription;
    }

    public String getPtype() {
        return ptype;
    }

    public String getPrice() {
        return price;
    }

    public String getPquantity() {
        return pquantity;
    }

    public String getPdate() {
        return pdate;
    }

    public String getPtime() {
        return ptime;
    }

    public String getPdiscount() {
        return pdiscount;
    }

    public String getPsnap() {
        return psnap;
    }

    public String getPactivation() {
        return pactivation;
    }
}