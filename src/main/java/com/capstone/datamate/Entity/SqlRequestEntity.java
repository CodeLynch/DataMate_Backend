package com.capstone.datamate.Entity;


public class SqlRequestEntity {
    private String tblName;
    private String vals;
    private int operation;

    public SqlRequestEntity(String tblName, String vals, int operation) {
        this.tblName = tblName;
        this.vals = vals;
        this.operation = operation;
    }

    public String getTblName() {
        return tblName;
    }

    public void setTblName(String tblName) {
        this.tblName = tblName;
    }

    public String getVals() {
        return vals;
    }

    public void setVals(String vals) {
        this.vals = vals;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    

    

    
}
