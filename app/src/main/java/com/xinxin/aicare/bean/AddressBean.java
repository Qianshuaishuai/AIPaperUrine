package com.xinxin.aicare.bean;

public class AddressBean {
    private String ADDRESS_ID;
    private String ADDRESS;
    private String CNAME;
    private String CPHONE;
    private String ISDEFAULT;

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getADDRESS_ID() {
        return ADDRESS_ID;
    }

    public void setADDRESS_ID(String ADDRESS_ID) {
        this.ADDRESS_ID = ADDRESS_ID;
    }

    public String getCNAME() {
        return CNAME;
    }

    public void setCNAME(String CNAME) {
        this.CNAME = CNAME;
    }

    public String getCPHONE() {
        return CPHONE;
    }

    public void setCPHONE(String CPHONE) {
        this.CPHONE = CPHONE;
    }

    public String getISDEFAULT() {
        return ISDEFAULT;
    }

    public void setISDEFAULT(String ISDEFAULT) {
        this.ISDEFAULT = ISDEFAULT;
    }
}
