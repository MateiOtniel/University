package org.example;

public class FIP {

    String atom;

    Integer code;

    String tsCode;

    public FIP(String atom, Integer code, String tsCode) {
        this.atom = atom;
        this.code = code;
        this.tsCode = tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }

    public int getCode() {
        return code;
    }

    public String getAtom() {
        return atom;
    }

    public void setAtom(String atom) {
        this.atom = atom;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTsCode() {
        return tsCode;
    }
}