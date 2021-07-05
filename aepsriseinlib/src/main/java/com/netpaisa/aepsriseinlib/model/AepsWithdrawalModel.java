package com.netpaisa.aepsriseinlib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AepsWithdrawalModel implements Serializable {

    @SerializedName("ERROR_CODE")
    @Expose
    private long errorCode;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("DATA")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable {
//{"ERROR_CODE":0,"MESSAGE":"Transaction Successful","DATA":{"tran_id":"CIJ012113014403389","amount":"100.00",
// "amount_txn":100,"balance":7775.6199999999998908606357872486114501953125,"account_no":"9540320399","status":"SUCCESS"}}
        @SerializedName("tran_id")
        @Expose
        private String tranId;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("amount_txn")
        @Expose
        private String amountTxn;
        @SerializedName("balance")
        @Expose
        private String balance;
        @SerializedName("account_no")
        @Expose
        private String accountNo;
        @SerializedName("status")
        @Expose
        private String status;

        public String getTranId() {
            return tranId;
        }

        public void setTranId(String tranId) {
            this.tranId = tranId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmountTxn() {
            return amountTxn;
        }

        public void setAmountTxn(String amountTxn) {
            this.amountTxn = amountTxn;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
