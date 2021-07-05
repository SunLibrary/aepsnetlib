package com.netpaisa.aepsriseinlib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AepsInstaBankModel implements Serializable {

    @SerializedName("ERROR_CODE")
    @Expose
    private long errorCode;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("DATA")
    @Expose
    private List<DATum> data = null;

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
