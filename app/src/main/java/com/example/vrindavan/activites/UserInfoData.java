package com.example.vrindavan.activites;

public class UserInfoData {
    public String usname,usemail,uspasswo,usmobile,usuid,usaid;

    public UserInfoData(String usname, String usemail, String uspasswo, String usmobile, String usuid, String usaid) {
        this.usname = usname;
        this.usemail = usemail;
        this.uspasswo = uspasswo;
        this.usmobile = usmobile;
        this.usuid = usuid;
        this.usaid = usaid;
    }

    public UserInfoData() {
    }

    public String getUsname() {
        return usname;
    }

    public String getUsemail() {
        return usemail;
    }

    public String getUspasswo() {
        return uspasswo;
    }

    public String getUsmobile() {
        return usmobile;
    }

    public String getUsuid() {
        return usuid;
    }

    public String getUsaid() {
        return usaid;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    public void setUsemail(String usemail) {
        this.usemail = usemail;
    }

    public void setUspasswo(String uspasswo) {
        this.uspasswo = uspasswo;
    }

    public void setUsmobile(String usmobile) {
        this.usmobile = usmobile;
    }

    public void setUsuid(String usuid) {
        this.usuid = usuid;
    }

    public void setUsaid(String usaid) {
        this.usaid = usaid;
    }
}
