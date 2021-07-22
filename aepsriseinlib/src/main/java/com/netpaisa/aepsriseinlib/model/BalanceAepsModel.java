package com.netpaisa.aepsriseinlib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BalanceAepsModel implements Serializable {
/*{"ERR_STATE":0,"MSG":"Transaction Successful","RES":{"statuscode":"TNS","status":"Transaction Successful",
"data":{"tran_id":"DIJ012120018512973","amount":"0.00","amount_txn":0,"balance":"1915.44","account_no":"9540320399","status":"SUCCESS"}}}
 */
    @SerializedName("ERR_STATE")
    @Expose
    private long errState;
    @SerializedName("MSG")
    @Expose
    private String msg;
    @SerializedName("RES")
    @Expose
    private Res res;

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

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public static class Res implements Serializable{

        @SerializedName("statuscode")
        @Expose
        private String statuscode;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private Data data;

        public String getStatuscode() {
            return statuscode;
        }

        public void setStatuscode(String statuscode) {
            this.statuscode = statuscode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }

    public static class Data implements Serializable {

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
