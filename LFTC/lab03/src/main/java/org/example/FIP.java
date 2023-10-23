package org.example;

public class FIP {
    Integer code;

    String tsCode;

    public FIP(Integer code, String tsCode) {
        this.code = code;
        this.tsCode = tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }
}