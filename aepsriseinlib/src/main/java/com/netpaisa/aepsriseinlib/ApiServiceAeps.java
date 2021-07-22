package com.netpaisa.aepsriseinlib;


import com.netpaisa.aepsriseinlib.model.BalanceAepsModel;
import com.netpaisa.aepsriseinlib.model.BankListAepsModel;
import com.netpaisa.aepsriseinlib.model.MiniStmntAepsModel;
import com.netpaisa.aepsriseinlib.model.WithdrawalAepsModel;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServiceAeps {

    /*************************AEPS Transaction***********************************/

    //@POST("https://pma.netpaisa.in/reseller/api/AEPSSDKV1/banklist.php")

    @POST("AEPSMOBSDK/banklist.php")
    @FormUrlEncoded
    Single<BankListAepsModel> getAepsInstaBankList(@FieldMap Map<String, String> param);
    //Single<AepsInstaBankModel> getAepsInstaBankList(@FieldMap Map<String, String> param);

    //@POST("AEPSSDKV1/transaction.php")
    @POST("AEPSMOBSDK/transaction.php")
    @FormUrlEncoded
    Single<BalanceAepsModel> getAepsInstaBalance(@FieldMap Map<String, String> param);
    //Single<AepsBalanceModel> getAepsInstaBalance(@FieldMap Map<String, String> param);

    @POST("AEPSMOBSDK/transaction.php")
    @FormUrlEncoded
    Single<WithdrawalAepsModel>  getAepsWithdrawal(@FieldMap Map<String, String> param);
    //Single<AepsWithdrawalModel>  getAepsWithdrawal(@FieldMap Map<String, String> param);

    @POST("AEPSMOBSDK/transaction.php")
    @FormUrlEncoded
    Single<MiniStmntAepsModel> getAepsMiniStatement(@FieldMap Map<String, String> param);
    //Single<AepsMiniStmntModel> getAepsMiniStatement(@FieldMap Map<String, String> param);



    /*************************AEPS KYC***********************************/

//    @POST("aeps/outlet-details.php")
//    @FormUrlEncoded
//    Single<AepsOutletModel> getOutletDetailsKYC(@FieldMap Map<String, String> param);
//
//    @POST("aeps/aeps-kyc-documents.php")
//    @FormUrlEncoded
//    Single<AepsStatusModel> getAepsOutletStatus(@FieldMap Map<String, String> param);
//
//    @POST("aeps/get-aeps-registration-otp.php")
//    @FormUrlEncoded
//    Single<AepsGetOTPModel> getAepsRegisterOtp(@FieldMap Map<String, String> param);
//
//    @POST("aeps/aeps-registration.php")
//    @FormUrlEncoded
//    Single<AepsRegisterModel> sendAepsRegisteration(@FieldMap Map<String, String> param);
//
//    @POST("aeps/aeps-submit-kyc.php")
//    @Multipart
//    Single<JsonObject> submitAepsInstaKYC(@PartMap() HashMap<String, RequestBody> partMap, @Part List<MultipartBody.Part> files);



}
