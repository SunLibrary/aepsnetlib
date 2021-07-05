package com.netpaisa.aepsriseinlib;

import com.google.gson.JsonObject;
import com.netpaisa.aepsriseinlib.model.AepsBalanceModel;
import com.netpaisa.aepsriseinlib.model.AepsGetOTPModel;
import com.netpaisa.aepsriseinlib.model.AepsInstaBankModel;
import com.netpaisa.aepsriseinlib.model.AepsMiniStmntModel;
import com.netpaisa.aepsriseinlib.model.AepsOutletModel;
import com.netpaisa.aepsriseinlib.model.AepsRegisterModel;
import com.netpaisa.aepsriseinlib.model.AepsStatusModel;
import com.netpaisa.aepsriseinlib.model.AepsWithdrawalModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiServiceAeps {

    /*************************AEPS Transaction***********************************/

    @POST("https://pma.netpaisa.in/reseller/api/AEPSSDKV1/banklist.php")
    @FormUrlEncoded
    Single<AepsInstaBankModel> getAepsInstaBankList(@FieldMap Map<String, String> param);

    @POST("AEPSSDKV1/transaction.php")
    @FormUrlEncoded
    Single<AepsBalanceModel> getAepsInstaBalance(@FieldMap Map<String, String> param);

    @POST("AEPSSDKV1/transaction.php")
    @FormUrlEncoded
    Single<AepsWithdrawalModel>  getAepsWithdrawal(@FieldMap Map<String, String> param);

    @POST("AEPSSDKV1/transaction.php")
    @FormUrlEncoded
    Single<AepsMiniStmntModel> getAepsMiniStatement(@FieldMap Map<String, String> param);




    /*************************AEPS KYC***********************************/

//    @POST("aeps/outlet-details.php")
//    @FormUrlEncoded
//    Single<AepsOutletModel> getOutletDetailsKYC(@FieldMap Map<String, String> param);
//
//    @POST("aeps/aeps-kyc-documents.php")
//    @FormUrlEncoded
//    Single<AepsStatusModel> getAepsOutletStatus(@FieldMap Map<String, String> param);
//
//
//    @POST("aeps/get-aeps-registration-otp.php")
//    @FormUrlEncoded
//    Single<AepsGetOTPModel> getAepsRegisterOtp(@FieldMap Map<String, String> param);
//
//
//    @POST("aeps/aeps-registration.php")
//    @FormUrlEncoded
//    Single<AepsRegisterModel> sendAepsRegisteration(@FieldMap Map<String, String> param);
//
//
//    @POST("aeps/aeps-submit-kyc.php")
//    @Multipart
//    Single<JsonObject> submitAepsInstaKYC(@PartMap() HashMap<String, RequestBody> partMap, @Part List<MultipartBody.Part> files);



}
