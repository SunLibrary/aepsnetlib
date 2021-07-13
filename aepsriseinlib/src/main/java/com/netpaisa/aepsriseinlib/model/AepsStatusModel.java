/*
package com.netpaisa.aepsriseinlib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AepsStatusModel implements Serializable {

    @SerializedName("ERROR_CODE")
    @Expose
    private Integer errorCode;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("KYC")
    @Expose
    private String kyc;

    @SerializedName("REASON")
    @Expose
    private Reason reason;
    @SerializedName("SCREENING")
    @Expose
    private Screening screening;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKyc() {
        return kyc;
    }

    public void setKyc(String kyc) {
        this.kyc = kyc;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }



    public static class Reason implements Serializable {

        @SerializedName("Aadhaar")
        @Expose
        private String aadhaar;

        public String getAadhaar() {
            return aadhaar;
        }

        public void setAadhaar(String aadhaar) {
            this.aadhaar = aadhaar;
        }
    }


    public static class Screening implements Serializable {

        @SerializedName("Aadhaar")
        @Expose
        private String aadhaar;

        public String getAadhaar() {
            return aadhaar;
        }

        public void setAadhaar(String aadhaar) {
            this.aadhaar = aadhaar;
        }
    }
}
*/
