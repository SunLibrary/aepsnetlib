package com.netpaisa.aepsriseinlib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BankListAepsModel implements Serializable{

    @SerializedName("ERR_STATE")
    @Expose
    private long errState;
    @SerializedName("MSG")
    @Expose
    private String msg;
    @SerializedName("DATA")
    @Expose
    private List<DATum> data = null;

    public long getErrState() {
        return errState;
    }

    public void setErrState(long errState) {
        this.errState = errState;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DATum> getData() {
        return data;
    }

    public void setData(List<DATum> data) {
        this.data = data;
    }




    public static class DATum implements Serializable {

        @SerializedName("bank_name")
        @Expose
        private String bankName;
        @SerializedName("bankiin")
        @Expose
        private String bankiin;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankiin() {
            return bankiin;
        }

        public void setBankiin(String bankiin) {
            this.bankiin = bankiin;
        }
    }
}
