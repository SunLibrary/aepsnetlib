package com.netpaisa.aepsriseinlib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MiniStmntAepsModel implements Serializable{

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


    public static class Res implements Serializable {

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

        public static class Data implements Serializable{

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
            @SerializedName("mini_statement")
            @Expose
            private List<MiniStatement> miniStatement = null;

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

            public List<MiniStatement> getMiniStatement() {
                return miniStatement;
            }

            public void setMiniStatement(List<MiniStatement> miniStatement) {
                this.miniStatement = miniStatement;
            }



            public static class MiniStatement implements Serializable{

                @SerializedName("date")
                @Expose
                private String date;
                @SerializedName("txnType")
                @Expose
                private String txnType;
                @SerializedName("amount")
                @Expose
                private String amount;
                @SerializedName("narration")
                @Expose
                private String narration;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getTxnType() {
                    return txnType;
                }

                public void setTxnType(String txnType) {
                    this.txnType = txnType;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getNarration() {
                    return narration;
                }

                public void setNarration(String narration) {
                    this.narration = narration;
                }

            }

        }
    }
}
