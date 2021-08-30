package com.ddkcommunity;


import com.ddkcommunity.fragment.mapmodule.model.GroupBonusModel;
import com.ddkcommunity.fragment.mapmodule.model.dailyBonusModel;
import com.ddkcommunity.fragment.mapmodule.model.direcetBidModel;
import com.ddkcommunity.fragment.mapmodule.model.directPurchesModel;
import com.ddkcommunity.fragment.mapmodule.model.directReferralModel;
import com.ddkcommunity.fragment.mapmodule.model.finnelModel;
import com.ddkcommunity.fragment.mapmodule.model.groupModel;
import com.ddkcommunity.fragment.mapmodule.model.overFlowModel;
import com.ddkcommunity.fragment.mapmodule.model.phaseOneBonusModel;
import com.ddkcommunity.fragment.mapmodule.model.powerOfXModel;
import com.ddkcommunity.fragment.mapmodule.model.powerXSubFragment;
import com.ddkcommunity.fragment.mapmodule.model.summaryModelNavi;
import com.ddkcommunity.fragment.paybillsModule.models.billerAllModel;
import com.ddkcommunity.fragment.paybillsModule.models.catModel;
import com.ddkcommunity.fragment.paybillsModule.models.categoryAllModel;
import com.ddkcommunity.fragment.settingModule.ARPDirectModel;
import com.ddkcommunity.fragment.settingModule.ARPHistoryModel;
import com.ddkcommunity.fragment.settingModule.ARPWalletBalanceModel;
import com.ddkcommunity.fragment.settingModule.ownershipListShowModel;
import com.ddkcommunity.fragment.settingModule.ownershipStatusModel;
import com.ddkcommunity.model.AllowGoogleModel;
import com.ddkcommunity.model.Announcement;
import com.ddkcommunity.model.BankList;
import com.ddkcommunity.model.CashOutAddBankResponse;
import com.ddkcommunity.model.CheckOtpResponse;
import com.ddkcommunity.model.EthModelBalance;
import com.ddkcommunity.model.FreeFlightVoucherList;
import com.ddkcommunity.model.LoggedUser;
import com.ddkcommunity.model.MapTransactionReceiverModel;
import com.ddkcommunity.model.OtpResponse;
import com.ddkcommunity.model.PdfViewPojo;
import com.ddkcommunity.model.PollingHistoryTransction;
import com.ddkcommunity.model.RedeemOptionModel;
import com.ddkcommunity.model.ReferalSubWalletListModel;
import com.ddkcommunity.model.ReferralPayoutModel;
import com.ddkcommunity.model.RequestModel;
import com.ddkcommunity.model.SAMPDModel;
import com.ddkcommunity.model.SMPDCompanyDetailsModel;
import com.ddkcommunity.model.ShowRequestApiModel;
import com.ddkcommunity.model.SliderImgResponse;
import com.ddkcommunity.model.SliderWithType;
import com.ddkcommunity.model.SubModelReeralList;
import com.ddkcommunity.model.TransactionDate;
import com.ddkcommunity.model.TransactionFeesResponse;
import com.ddkcommunity.model.UserBankListResponse;
import com.ddkcommunity.model.activityModel;
import com.ddkcommunity.model.addActivityModel;
import com.ddkcommunity.model.adsDialogModel;
import com.ddkcommunity.model.arpstausModel;
import com.ddkcommunity.model.bankDepositeModel;
import com.ddkcommunity.model.bankDetailsModel;
import com.ddkcommunity.model.bankLstModel;
import com.ddkcommunity.model.baseUrlModel;
import com.ddkcommunity.model.buyCryptoModel;
import com.ddkcommunity.model.checkRefferalModel;
import com.ddkcommunity.model.conatcModel;
import com.ddkcommunity.model.currencyModelClick;
import com.ddkcommunity.model.depositeModelSaveData;
import com.ddkcommunity.model.emergencyModel;
import com.ddkcommunity.model.exchange.CurrencyList;
import com.ddkcommunity.model.getGoogleAuthSecraModel;
import com.ddkcommunity.model.getSettingModel;
import com.ddkcommunity.model.googleAuthPasswordModel;
import com.ddkcommunity.model.mapLoginModel;
import com.ddkcommunity.model.mapSubscriptionModel;
import com.ddkcommunity.model.mazigneModel;
import com.ddkcommunity.model.navigationModel;
import com.ddkcommunity.model.owneeshipstausModel;
import com.ddkcommunity.model.projects.PoolingTransactionHistory;
import com.ddkcommunity.model.referalRequestsendModel;
import com.ddkcommunity.model.referral.ReferralChain;
import com.ddkcommunity.model.referralSublistModel;
import com.ddkcommunity.model.samBalanceModel;
import com.ddkcommunity.model.samModel;
import com.ddkcommunity.model.scanQRModel;
import com.ddkcommunity.model.sfioAddModelsee;
import com.ddkcommunity.model.sfioHeaderModel;
import com.ddkcommunity.model.sfioModel;
import com.ddkcommunity.model.sfioSubPackageModel;
import com.ddkcommunity.model.smpdModelNew;
import com.ddkcommunity.model.smpdfavmodel;
import com.ddkcommunity.model.summaryHistoryModel;
import com.ddkcommunity.model.usdtreddeemModel;
import com.ddkcommunity.model.userInviteModel;
import com.ddkcommunity.model.userPackagesModel;
import com.ddkcommunity.model.verifcationFundSource;
import com.ddkcommunity.model.versionModel;
import com.ddkcommunity.model.withdrawal.Withdrawal;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface LoadInterface {

    //@POST("create-secret")

    @POST("credentials/create-secret")
    Call<ResponseBody> createSecret(@Query("token") String token);
   // @POST("TransferApi/get-credentials")

    @POST("credentials/get-credentials")
    Call<ResponseBody> credentialList(@Query("token") String token, @Body HashMap<String, String> hm);
    //@POST("secondFace/get-credentials")

    @POST("credentials/get-credentials-secondface")
    Call<ResponseBody> credentialListForFragment(@Query("token") String token);
    @Multipart
    @POST("credentials/create-wallet")
    Call<ResponseBody> createWalletCall(
            @Query("token") String token,
            @Part("email") RequestBody user_email,
            @Part("secret") RequestBody secret,
            @Part("login_via") RequestBody loginvia,
            @Part("device_type") RequestBody device_type,
            @Part("device_token") RequestBody device_token,
            @Part("name") RequestBody name,
            @Part("notes") RequestBody notes,
            @Part("referal") RequestBody referal
    );

   // @POST("get-event-list")

    @POST("commonevent/get-event-list")
    Call<ResponseBody> eventList(
            @Query("token") String token
    );
    @Multipart
    //@POST("delete-event")
    @POST("commonevent/delete-event")
    Call<ResponseBody> deleteEvent(
            @Query("token") String token,
            @Part("event_id") RequestBody event_id
    );

    @Multipart
    @POST("credentials/delete-credentials")
    Call<ResponseBody> deleteCredential(
            @Query("token") String token,
            @Part("credential_id") RequestBody credential_id
    );

    @Multipart
    //@POST("add-event")
    @POST("commonevent/add-event")
    Call<ResponseBody> addEvent(
            @Query("token") String token,
            @Part("event_name") RequestBody event_name,
            @Part("event_address") RequestBody event_address,
            @Part("event_start_date") RequestBody event_start_date,
            @Part("event_end_date") RequestBody event_end_date,
            @Part("event_start_time") RequestBody event_start_time,
            @Part("event_end_time") RequestBody event_end_time,
            @Part("event_status") RequestBody event_status,
            @Part("event_type") RequestBody event_type,
            @Part MultipartBody.Part file
    );

    @Multipart
   // @POST("edit-event")
    @POST("commonevent/edit-event")
    Call<ResponseBody> updateEvent(
            @Query("token") String token,
            @Part("event_name") RequestBody event_name,
            @Part("event_address") RequestBody event_address,
            @Part("event_start_date") RequestBody event_start_date,
            @Part("event_end_date") RequestBody event_end_date,
            @Part("event_start_time") RequestBody event_start_time,
            @Part("event_end_time") RequestBody event_end_time,
            @Part("event_status") RequestBody event_status,
            @Part("event_type") RequestBody event_type,
            @Part("event_id") RequestBody event_id,
            @Part MultipartBody.Part file
    );

    @Multipart
    //@POST("edit-event")
    @POST("commonevent/edit-event")
    Call<ResponseBody> updateEvent(
            @Query("token") String token,
            @Part("event_name") RequestBody event_name,
            @Part("event_address") RequestBody event_address,
            @Part("event_start_date") RequestBody event_start_date,
            @Part("event_end_date") RequestBody event_end_date,
            @Part("event_start_time") RequestBody event_start_time,
            @Part("event_end_time") RequestBody event_end_time,
            @Part("event_status") RequestBody event_status,
            @Part("event_type") RequestBody event_type,
            @Part("event_id") RequestBody event_id
    );

    @Multipart
    //@POST("thirdFace/add-credentials")
    @POST("credentials/add-credentials")
    Call<ResponseBody> addCredential(
            @Query("token") String token,
            @Part("name") RequestBody name,
            @Part("ddkcode") RequestBody ddkcode,
            @Part("referal_link") RequestBody referal_link,
            @Part("passphrase") RequestBody passphrase,
            @Part("second_passphrase") RequestBody second_passphrase,
            @Part("notes") RequestBody notes
    );

    /*@POST("edit-credentials")*/
    @Multipart
   // @POST("apitesting/edit-credentials")
    @POST("credentials/edit-credentials")
    Call<ResponseBody> editCredential(
            @Part("credential_id") RequestBody credential_id,
            @Query("token") String token,
            @Part("name") RequestBody name,
            @Part("ddkcode") RequestBody ddkcode,
            @Part("referal_link") RequestBody referal_link,
            @Part("passphrase") RequestBody passphrase,
            @Part("second_passphrase") RequestBody second_passphrase,
            @Part("notes") RequestBody notes,
            @Part("status") RequestBody status
    );
    @Multipart
   // @POST("ninethface/buy-crypto-stripe-pay")
    @POST("cryptopayemnt/buy-crypto-stripe-pay")
    Call<ResponseBody> paymentBuy(
            @Query("token") String token,
            @Part("token_id") RequestBody token_id,
            @Part("amt") RequestBody amt,
            @Part("wallet_id") RequestBody wallet_id,
            @Part("crypto_type") RequestBody crypto_type,
            @Part("credit_card_fee") RequestBody credit_card_fee,
            @Part("blockchain_fee") RequestBody blockchain_fee,
            @Part("total_fees") RequestBody total_fee,
            @Part("conversion") RequestBody conversion
    );

    @POST("commondetails/get-tutorials")
    Call<ResponseBody> tutorials(
            @Query("token") String token
    );

    @Multipart
   // @POST("update-to-archive")
    @POST("commonevent/update-to-archive")
    Call<ResponseBody> updateArchive(
            @Query("token") String token,
            @Part("event_list") RequestBody eventId
    );

    @POST("apiredeem/get-usdt-transaction-fees")
    Call<usdtreddeemModel> getUsdtTransactionFee(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("apiredeem/redeem-usdt-transaction")
    Call<ResponseBody> submitusdtTransaction(@Query("token") String token, @Body HashMap<String, String> hm);

    /************for version 2.4 **********/
    //@POST("user-login-dev")
    //userauth/user-login-testing
    //@POST("userauth/user-login")
    @POST("userauth/user-login")
    Call<ResponseBody> loginCall(@Body HashMap<String, String> hm);

    //@POST("apitesting/create_user")
    @Multipart
    @POST("userauth/create-user")
    Call<ResponseBody> signUpCall(
            @Part("dob") RequestBody dateofbirth,
            @Part("name") RequestBody first_name,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("password") RequestBody password,
            @Part("country_id") RequestBody country_id,
            @Part("fcm_id") RequestBody fcm_id,
            @Part("phone_code") RequestBody phone_code,
            @Part("device_type") RequestBody device_type,
            @Part("referal_code") RequestBody referal_code,
            @Part MultipartBody.Part file,
            @Part MultipartBody.Part file1
    );

    //@GET("country-list")
    @GET("userauth/get-country-list")
    Call<ResponseBody> countryList();

    //@POST("eightface/user-profile")
    @POST("userauth/user-profile")
    Call<ResponseBody> getProfileCall(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("check_otp")
    @Multipart
    @POST("userauth/check-otp")
    Call<ResponseBody> CheckOtpCall(
            @Query("token") String token,
            @Part("email") RequestBody userId,
            @Part("user_otp") RequestBody otp,
            @Part("verify_for") RequestBody verifyfor);

    // @POST("apigetreferralcode/get-referral-code")
    @POST("userauth/get-referral-code")
    Call<userInviteModel> getInviteFriends(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("user-wallet/scan-qrcode")
    Call<scanQRModel> getScanQRCode(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("recent_otp")
    @Multipart
    @POST("userauth/recent-otp")
    Call<ResponseBody> resendOtp(@Query("token") String token, @Part("email") RequestBody email, @Part("otp_type") RequestBody otptype);

    //@POST("secondFace/switch-user")
    @POST("userauth/switch-login")
    Call<ResponseBody> switchUser(@Body HashMap<String, String> hm);

    @GET("commondetails/get-app-version")
    Call<versionModel> getVersioning();

    @POST("commondetails/general-functionality")
    Call<getSettingModel> getGeneralFunctionality(@Query("token") String token,@Body HashMap<String, String> hm);

    //@GET("ninethface/social-media-share")
    @GET("commondetails/social-media-share")
    Call<ResponseBody> getFacebookShare();

    // @POST("ninethface/buy-crypto-list")
    @POST("commondetails/buy-crypto-list")
    Call<buyCryptoModel> getbuyCryptoList(@Query("token") String token);

    @POST("commondetails/get-constant-url")
    Call<baseUrlModel> getConstantUrl(@Query("token") String token,@Body HashMap<String, String> hm);

    // @GET("ninethface/sliders")
    @GET("commondetails/sliders")
    Call<SliderImgResponse> getBannerLayout();

    @Multipart
    //@POST("tenthface/add-contact-us")
    @POST("commondetails/add-contact-us")
    Call<conatcModel> sendContactUs(
            @Query("token") String token,
            @Part("subject") RequestBody message,
            @Part("message") RequestBody description,
            @Part MultipartBody.Part attachment);

    //    @POST("tenthface/add-contact-us")
    @Multipart
    @POST("commondetails/add-contact-us")
    Call<conatcModel> sendContactUswithout(
            @Query("token") String token,
            @Part("subject") RequestBody message,
            @Part("message") RequestBody description);

    //for kyc
    @POST("kyc/kyc-email-verification")
    Call<ResponseBody> emailVerification(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("kyc/kyc-mobile-verification")
    Call<ResponseBody> mobileVerification(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("kyc/kyc-mobile-otp-verification")
    Call<ResponseBody> mobileVerifiedData(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("kyc/kyc-otp-verification")
    Call<ResponseBody> emailVerifiedData(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("kyc/kyc-fund-source-verification")
    Call<ResponseBody> kycFundSource(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("kyc/kyc-verification-status")
    Call<ResponseBody> getVerificationStatus(@Query("token") String token);

    @POST("kyc/kyc-rules")
    Call<ResponseBody> getKYCRules(@Query("token") String token, @Body HashMap<String, String> hm);

    @Multipart
    @POST("kyc/kyc-identity-submission")
    Call<ResponseBody> submitIdentityVerification(
            @Query("token") String token,
            @Part("govt_id_1") RequestBody first_name,
            @Part("govt_id_2") RequestBody secondname,
            @Part MultipartBody.Part id_proof_1_front,
            @Part MultipartBody.Part id_proof_1_back,
            @Part MultipartBody.Part id_proof_2_front,
            @Part MultipartBody.Part id_proof_2_back);

    @Multipart
    @POST("kyc/kyc-single-video-submission")
    Call<ResponseBody> submitVideoData(
            @Query("token") String token,
            @Part MultipartBody.Part single_video);

    @Multipart
    @POST("kyc/kyc-address-submission")
    Call<ResponseBody> submitaddresssubmission(
            @Query("token") String token,
            @Part MultipartBody.Part id_proof_1_front);


    @Multipart
    @POST("sfio/sfio-bank-document-save")
    Call<ResponseBody> sfioSubmiticon(
            @Query("token") String token,
            @Part("sfio_id") RequestBody email,
            @Part MultipartBody.Part id_proof_1_front);

    @Multipart
    @POST("userauth/reset-password")
    Call<ResponseBody> resetPassword(
            @Part("email") RequestBody email,
            @Part("forget_password_token") RequestBody forget_password_token,
            @Part("new_password") RequestBody new_password,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("userauth/upload-photo-id")
    Call<ResponseBody> loginUserIdUpdate(
            @Query("token") String token,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("userauth/edit-user-profile")
    Call<ResponseBody> updateProfileCall(
            @Part("alt_name") RequestBody altname,
            @Part("alt_email") RequestBody altemail,
            @Part("alt_contact_number") RequestBody altcontactno,
            @Part("address") RequestBody address,
            @Part("gender_name") RequestBody gender_name,
            @Part("gender") RequestBody gender,
            @Part("zip") RequestBody zip,
            @Part("province") RequestBody province,
            @Part("city") RequestBody city,
            @Part("dob") RequestBody bod,
            @Query("token") String token,
            @Part("name") RequestBody first_name,
            @Part("mobile") RequestBody mobile,
            @Part("country_id") RequestBody country_id,
            @Part("ddkcode") RequestBody DDKCode,
            @Part("phone_code") RequestBody phone_code,
            @Part("designation") RequestBody designation);

    //@POST("apitesting/edit-user-profile")
    @Multipart
    @POST("userauth/edit-user-profile")
    Call<ResponseBody> updateProfileCall(
            @Part("alt_name") RequestBody altname,
            @Part("alt_email") RequestBody altemail,
            @Part("alt_contact_number") RequestBody altcontactno,
            @Part("address") RequestBody address,
            @Part("gender_name") RequestBody gender_name,
            @Part("gender") RequestBody gender,
            @Part("zip") RequestBody zip,
            @Part("province") RequestBody province,
            @Part("city") RequestBody city,
            @Part("dob") RequestBody bod,
            @Query("token") String token,
            @Part("name") RequestBody first_name,
            @Part("mobile") RequestBody mobile,
            @Part("country_id") RequestBody country_id,
            @Part("ddkcode") RequestBody DDKCode,
            @Part("phone_code") RequestBody phone_code,
            @Part("designation") RequestBody designation,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("userauth/forgot-password")
    Call<ResponseBody> forgotCall(
            @Part("email") RequestBody id
    );

    @Multipart
    @POST("userauth/change-password")
    Call<ResponseBody> changePasswordCall(
            @Query("token") String token,
            @Part("old_password") RequestBody oldpass,
            @Part("new_password") RequestBody newpass
    );

    @GET("user-wallet/get-wallet-balance")
    Call<EthModelBalance> getPHPBalance(@Query("token") String token);

    @POST("commondetails/get-usdt-balace")
    Call<EthModelBalance> getUSDTBalance(@Query("token") String token, @Body HashMap<String, String> hm);


    @POST("commondetails/get-tron-balace")
    Call<EthModelBalance> getTRONBalance(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("commondetails/get-sam-koin-balance")
    Call<EthModelBalance> getSAMKOINBalance(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("commondetails/get-btc-balace")
    Call<EthModelBalance> getBTCBalance(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("commondetails/eth-balance")
    Call<EthModelBalance> getEthBalance(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("commondetails/sam-pd-activities")
    Call<FreeFlightVoucherList> getFreeVoucherActivity(@Body JsonObject body);

    @POST("commondetails/sam-pd-docs")
    Call<PdfViewPojo> getFreeVoucherDocs(@Body JsonObject gsonObject);

    @POST("commondetails/sliders-with-type")
    Call<SliderWithType> getSlidersType(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("samads/get-ads")
    Call<adsDialogModel> getAds(@Query("token") String token);

    @POST("ibayad/category-list")
    Call<categoryAllModel> getCategorylist(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("ibayad/biller-list")
    Call<billerAllModel> getBillerlist(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("ibayad/biller-parameter-list")
    Call<ResponseBody> getBillerParamterList(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("ibayad/search-billers-category")
    Call<billerAllModel> getSearchList(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sampd-company/company-list")
    Call<smpdModelNew> getSMPDLIST(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("ibayad/ibayad-home")
    Call<catModel> getPayBillCat(@Query("token") String token);

    @POST("sampd-company/add-remove-fav-sampd-company")
    Call<smpdfavmodel> AddFavItem(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("userauth/update-emergency-contact")
    Call<emergencyModel> AddEmergencyContact(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("userauth/update-email-address")
    Call<emergencyModel> updateEmailAddress(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("modify-referral/update-referal-code")
    Call<referalRequestsendModel> updateReferalCode(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("ninethface/token")
    @POST("commondetails/token")
    Call<samModel> getSameToken(@Query("token") String token);

    @POST("redeem/redeem-options")
    Call<RedeemOptionModel> getRedeemList(@Query("token") String token);

    @POST("commondetails/api-constant-key")
    Call<ResponseBody> getKeyListData(@Query("token") String token);

    @POST("commondetails/get_map_login_url")
    Call<ResponseBody> getMapLoginUrl(@Query("token") String token);

    @POST("userauth/user-profile-web-view")
    Call<ResponseBody> getProfilewebview(@Query("token") String token);

    @POST("userauth/delete-profile-image")
    Call<ResponseBody> removePhotoCall(@Query("token") String token);

    //@POST("receive-koin")
    //@POST("TransferApi/receive-koin")
    @Multipart
    @POST("commondetails/receive-koin")
    Call<ResponseBody> receiveCall(@Query("token") String token, @Part("wallet_id") RequestBody walletid, @Part("iv") RequestBody iv, @Part("key") RequestBody key);

    @Multipart
    @POST("commondetails/get-address")
    Call<ResponseBody> walletList(
            @Query("token") String token,
            @Part("request_via") RequestBody request_via,
            @Part("email") RequestBody email);

   // @POST("announcement")
    @POST("commonevent/announcement")
    Call<Announcement> getAppAnnouncement(@Query("token") String token);

    //@POST("announcement-do-not-show")
    @POST("commonevent/announcement-do-not-show")
    Call<ResponseBody> cancelAnnouncement(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("cryptopayemnt/send-koin-pooling-dev-new-manual")
     Call<ResponseBody> sendKoinPoolingManual(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sam-payment/send-koin-pooling-dev-new-manual")
    Call<ResponseBody> sendSubscriptionPayment(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("map/subscription")
    Call<mapSubscriptionModel> mapSubscription(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("purchase-direct-position")
    Call<directPurchesModel> mapDirectSubscription(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sfio/add")
    Call<sfioAddModelsee> addSFIOAmt(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("delete-user-logged-in-device")
    @POST("userauth/delete-user-logged-in-device")
    Call<ResponseBody> deleteUser(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("userauth/get-user-logged-in-device")
    Call<LoggedUser> getLoggedUser(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("pooling-transaction-history")
    @POST("cryptopayemnt/pooling-transaction-history")
    Call<PoolingTransactionHistory> getPoolingTransaction(@Query("token") String token, @Body HashMap<String, String> hm);

    //tenthface/payout-chain
    //@POST("user-referral-chain-payout")
   // @POST("tenthface/user-referral-chain-payout")
    @POST("referral/user-referral-chain-payout")
    Call<ReferralChain> getReferralChain(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("ddk-withdrawal-list")
    @POST("cryptopayemnt/ddk-withdrawal-list")
    Call<Withdrawal> getWithdrawalData(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("ddk-withdrawal-list-manual")
    @POST("cryptopayemnt/ddk-withdrawal-list-manual")
    Call<Withdrawal> getWithdrawalDataManual(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("save-withdrawal-request")
    @POST("cryptopayemnt/save-withdrawal-request")
    Call<ResponseBody> postCancellationData(@Query("token") String token, @Body HashMap<String, Object> hm);

    // @POST("tenthface/payout-chain-refferal-wallet-list-user")
    @POST("referral/payout-chain-refferal-wallet-list-user")
    Call<referralSublistModel> getWalletReferrlList(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("tenthface/payout-chain-refferal-wallet-list")
    @POST("referral/payout-chain-refferal-wallet-list")
    Call<ReferalSubWalletListModel> getSubWalletReferralList(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("tenthface/payout-chain-refferal-list")
    @POST("referral/payout-chain-refferal-list")
    Call<SubModelReeralList> getSubPayoutListList(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("all_transactions/google-auth-secret")
    @POST("userauth/google-auth-secret")
    Call<getGoogleAuthSecraModel> getQRSecrateAuth(@Query("token") String token);

   // @POST("all_transactions/check-auth-code")
    @POST("userauth/check-auth-code")
    Call<googleAuthPasswordModel> checkAuthCode(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("all_transactions/update-user-auth-status")
    @POST("userauth/update-user-auth-status")
    Call<AllowGoogleModel> sendUpdateAuth(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("tenthface/payout-chain")
    @POST("referral/payout-chain")
    Call<ReferralPayoutModel> getPayoutList(@Query("token") String token, @Body HashMap<String, String> hm);

    //@GET("payout-save-transaction-date")
    //Call<TransactionDate> getTransactionDate(@Query("token") String token);
   // @POST("tenthface/payout-save-transaction-date")
    @POST("referral/payout-save-transaction-date")
    Call<TransactionDate> getTransactionDate(@Query("token") String token, @Body HashMap<String, String> hm);

   // @GET("sixthface/ddkPrice")
    // @GET("test/ex-market-sam-koin")
    @GET("cryptopayemnt/ddkPrice")
    Call<ResponseBody> getDDKValue();

   // @GET("ninethface/our-team")
    @GET("commonevent/our-team")
    Call<ResponseBody> getOurTeam();

    @GET()
    Call<ResponseBody> getUSDTETHKValue(@Url String url);

  //  @POST("secondFace/bank-name")
    @POST("commonevent/bank-name")
    Call<BankList> getTheBankList(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("user-bank/add")
    Call<CashOutAddBankResponse> addBankAccount(@Query("token") String token, @Body HashMap<String, String> body);

    @POST("commondetails/check-user-account-limits")
    Call<OtpResponse> checkUserAccountLimit(@Query("token") String token, @Body HashMap<String, String> hm);

    // @POST("secondFace/email-otp")
    @POST("commonevent/email-otp")
    Call<OtpResponse> postOtp(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("modify-referral/validate-referal-code")
    Call<referalRequestsendModel> validateReferral(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("modify-referral/count-referal-level")
    Call<referalRequestsendModel> countReferalLevel(@Query("token") String token);

    @POST("userauth/verify-mobile-otp")
    Call<OtpResponse> OtpVerified(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("userauth/send-mobile-otp")
    Call<OtpResponse> sendtOtp(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("userauth/forgot-password")
    Call<OtpResponse> forgotPAssword(@Body HashMap<String, String> hm);

    @POST("userauth/check-otp")
    Call<CheckOtpResponse> checkOtp(@Body HashMap<String, String> hm);

    //@GET("thirdFace/currency-list")
    @GET("commonevent/currency-list")
    Call<CurrencyList> getCurrencyList();

    //@POST("thirdFace/convert-price")
    //@POST("apicoinconversion/convert-price")
    @POST("cryptopayemnt/convert-price")
    Call<ResponseBody> postConvertPrice(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("apicancelrequest/insert-requested-subscription")
    @POST("cryptopayemnt/insert-requested-subscription")
    Call<ResponseBody> insertrequestapi(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("eightface/get-btc-unconfirmed-balance")
    @POST("cryptopayemnt/get-btc-unconfirmed-balance")
    Call<ResponseBody> checkPaymentStatus(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("ninethface/crypto-estimate-pooling-fees")
    @POST("cryptopayemnt/crypto-estimate-pooling-fees")
    Call<ResponseBody> EthBtcUsdtConvertPrice(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sam-payment/crypto-estimate-pooling-fees")
    Call<ResponseBody> SamConvertPrice(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("map/map-transaction-fees-calculation")
    Call<ResponseBody> mapTransactionFeesCaluation(@Query("token") String token, @Body HashMap<String, String> hm);

    // @POST("fifthface/check-ddk-transaction-status")
    @POST("cryptopayemnt/check-ddk-transaction-status-fifthface")
    Call<ResponseBody> checkDDKTransactionStatus(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("sixthface/usd-to-php")
    @POST("cryptopayemnt/usd-to-php")
    Call<ResponseBody> convertUsdToPhp(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("cryptopayemnt/usd-to-other")
    Call<ResponseBody> convertUsdToAny(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("TransferApi/send-cash-out")
    @POST("cryptopayemnt/send-cash-out")
    //@POST("apitesting/send-cash-out")
    Call<ResponseBody> sendCashOut(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("kpay/withdrawl")
    Call<ResponseBody> sendCashOutSamKoinIndonesia(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sam-payment/send-cash-out")
    Call<ResponseBody> sendCashOutSamKoin(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("cryptopayemnt/send-cash-out_new")
    Call<ResponseBody> sendCashOutNew(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("user-bank/list")
    Call<UserBankListResponse> userBankList(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("user-bank/edit")
    Call<ResponseBody> userBankEdit(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("user-bank/delete")
    Call<ResponseBody> userBankDelete(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("ninethface/buy-crypto-estimation-fees")
    @POST("cryptopayemnt/buy-crypto-estimation-fees")
    Call<ResponseBody> sendBuyFees(@Query("token") String token, @Body HashMap<String, String> hm);

    //@GET("sixthface/cash-out-fees")
    @GET("cryptopayemnt/cash-out-fees")
    Call<TransactionFeesResponse> gettransactionFees(@Query("token") String token);

    @GET("sfio/get-currency-list")
    Call<bankLstModel> getCurrenecySfioList();

    @GET("kyc/get-source-fund")
    Call<verifcationFundSource> getFundSource();

    @GET("kyc/get-government-id")
    Call<verifcationFundSource> getVerificationsoucer();

    //Check cash out transaction status
    //@GET("eightface/received-address")
    @GET("credentials/received-address")
    Call<TransactionFeesResponse> getreceiverAddress();

    @GET("credentials/map-received-address")
    Call<MapTransactionReceiverModel> getMapreceiverAddress();

    //................
    //@POST("sixthface/check-ddk-transaction-status")
    @POST("cryptopayemnt/check-ddk-transaction-status")
    Call<ResponseBody> checCashOutTransactionStatus(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("ninethface/check-sam-token-status")
    @POST("redeem/check-sam-token-status")
    Call<ResponseBody> checReedemStatus(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("cryptopayemnt/check-send-ddk-transaction")
    Call<ResponseBody> checkSendDDKTransaction(@Query("token") String token, @Body HashMap<String, String> hm);

    // @POST("apitesting/create-ddk-transaction")
   // @POST("TransferApi/create-ddk-transaction")
    @POST("cryptopayemnt/create-ddk-transaction")
    Call<ResponseBody> createDDKTransaction(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("user-wallet/send-wallet-amount")
    Call<ResponseBody> sendWalletAmountTransaction(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("user-wallet/send-amount-after-qrscan")
    Call<ResponseBody> sendAmountAfterScan(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sam-payment/create-crypto-transaction")
    Call<ResponseBody> createSAMKOINTransaction(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sampd-company/company-details")
    Call<SMPDCompanyDetailsModel> getCompanydetails(@Query("token") String token, @Body HashMap<String, String> hm);

    // @POST("apicancelrequest/show-active-subscription")
    @POST("redeem/show-active-subscription")
    Call<ShowRequestApiModel> showactivesubscription(@Query("token") String token);

   // @POST("apicancelrequest/show-all-request")
    @POST("redeem/show-all-request")
    Call<RequestModel> showallrequest(@Query("token") String token);

    //@POST("all_transactions/check-active-subscription-user")
    @POST("redeem/check-active-subscription-user")
    Call<SAMPDModel> getActivteSubscriptionStatus(@Query("token") String token);


    @POST("commondetails/get-magazine")
    Call<mazigneModel> getMagazieapi(@Query("token") String token);

    // @POST("all_transactions/all-transaction-history")
    //@POST("seventhface/all-transaction-history")
   // @POST("commondetails/all-transaction-history-new")
    @POST("commondetails/all-transaction-history-new")
    //@POST("commondetails/all-transaction-history-seventhface")
    Call<PollingHistoryTransction> getAllTransactionHistory(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("all_transactions/get-summary-history")
    //@POST("seventhface/get-summary-history")
    @POST("commondetails/get-summary-history")
    Call<summaryHistoryModel> getPoolingTransactionHistory(@Query("token") String token);

    //@POST("seventhface/create-eth-transaction")
    @POST("cryptopayemnt/create-eth-transaction")
    Call<ResponseBody> createETHTransaction(@Query("token") String token, @Body HashMap<String, String> hm);

    // @POST("ninethface/update-token")
    //@POST("apitesting/update-token")
    @POST("redeem/update-token")
    Call<ResponseBody> updateTokenRedeem(@Query("token") String token, @Body HashMap<String, String> hm);

    //@POST("apibalance/get-redeem-balance")
    @POST("redeem/get-redeem-balance")
    Call<samBalanceModel> getRedeemBalance(@Query("token") String token);

   // @POST("eightface/create-btc-transaction")
    @POST("cryptopayemnt/create-btc-transaction")
    Call<ResponseBody> createBTCTransaction(@Query("token") String token, @Body HashMap<String, String> hm);

   // @POST("eightface/create-usdt-transaction")
    @POST("cryptopayemnt/create-usdt-transaction")
    Call<ResponseBody> createUSDTTransaction(@Query("token") String token, @Body HashMap<String, String> hm);

    /**************************************/

    /************************SIGN_UP_CALL*********************************/
     //app Version 1.0.21
    //secondFace/create_user
    //eightface/create_user

//    @Multipart
//    @POST("user-login")
//    Call<ResponseBody> loginCall(
//            @Part("email") RequestBody email,
//            @Part("password") RequestBody password,
//            @Part("fcm_id") RequestBody fcm_id,
//            @Part("device_type") RequestBody device_type
//    );


//    http://157.245.52.206/api/delete-user-logged-in-device

    @POST("get-joined-list")
    Call<ResponseBody> joinList(@Query("token") String token
    );

    @POST("get-invite-event")
    Call<ResponseBody> invitesList(@Query("token") String token);

    @POST("get-ddkcoin")
    Call<ResponseBody> ddkCoinCall(
            @Query("token") String token
    );

    @Multipart
    @POST("add-id-proof")
    Call<ResponseBody> addIdProof(
            @Query("token") String token,
            @Part List<MultipartBody.Part> file
    );

    //    App Version i.0.15
//    add-credentials

//    @Multipart
//    @POST("stripe-payment")
//    Call<ResponseBody> payment(
//            @Part("user_id") RequestBody id,
//            @Part("token_id") RequestBody token_id,
//            @Part("amount") RequestBody amount,
//            @Part("ddkcoin") RequestBody ddkcoin,
//            @Part("ddk_rate") RequestBody rate,
//            @Part("wallet_id") RequestBody walletId
//    );

    //@POST("stripe-payment-dev")
    //@POST("ninethface/buy-crypto-stripe-
    //buy with test key @POST("apitesting/buy-crypto-stripe-pay")
    // @POST("apitesting/buy-crypto-stripe-pay")


    @POST("get-marketing")
    Call<ResponseBody> marketingCall(
            @Query("token") String token
    );

    @POST("get-history")
    Call<ResponseBody> historyCall(
            @Query("token") String token
    );

    /************************HISTORY*********************************/

    @Multipart
    @POST("transaction-history")
    Call<ResponseBody> historyCall(
            @Query("token") String token, @Part("wallet_id") RequestBody walletid);


    @Multipart
    @POST("social-login")
    Call<ResponseBody> socialLoginCall(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("social_id") RequestBody social_id,
            @Part("social_type") RequestBody social_type,
            @Part("device_token") RequestBody device_token,
            @Part("device_type") RequestBody device_type,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("social-login")
    Call<ResponseBody> socialLoginCall(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("social_id") RequestBody social_id,
            @Part("social_type") RequestBody social_type,
            @Part("device_token") RequestBody device_token,
            @Part("device_type") RequestBody device_type
    );

    @Multipart
    @POST("import-wallet")
    Call<ResponseBody> importWalletCall(
            @Query("token") String token,
            @Part("secret") RequestBody secret,
            @Part("name") RequestBody name,
            @Part("device_type") RequestBody device_type,
            @Part("device_token") RequestBody device_token);

    @Multipart
    @POST("pool-list")
    Call<ResponseBody> poolListCall(@Part("user_id") RequestBody user_id);

    @Multipart
    @POST("delegate-new-list")
    Call<ResponseBody> delegateCall(@Query("token") String token,
                                    @Part("address") RequestBody address);

    @Multipart
    @POST("my-votes")
    Call<ResponseBody> myVotesCall(
            @Query("token") String token,
            @Part("address") RequestBody address);

    @Multipart
    @POST("stack-list")
    Call<ResponseBody> stakeListCall(@Query("token") String token,
                                     @Part("secret") RequestBody secret);

    @Multipart
    @POST("stack-send")
    Call<ResponseBody> stakeSendCall(@Query("token") String token,
                                     @Part("secret") RequestBody secret,
                                     @Part("receipent_id") RequestBody receipent_id,
                                     @Part("public_key") RequestBody public_key,
                                     @Part("stake_id") RequestBody stake_id,
                                     @Part("address") RequestBody address,
                                     @Part("freez_amount") RequestBody freez_amount,
                                     @Part("second_secret") RequestBody second_secret,
                                     @Part("device_type") RequestBody device_type
    );

    @Multipart
    @POST("stack-koins")
    Call<ResponseBody> stakeKoinsCall(@Query("token") String token,
                                      @Part("secret") RequestBody secret,
                                      @Part("amount") RequestBody amount,
                                      @Part("public_key") RequestBody public_key,
                                      @Part("second_secret") RequestBody second_secret,
                                      @Part("address") RequestBody address,
                                      @Part("device_type") RequestBody device_type
    );

    @Multipart
    @POST("send-koin")
    Call<ResponseBody> sendWalletCall(
            @Query("token") String token,
            @Part("secret") RequestBody secret,
            @Part("amount") RequestBody amount,
            @Part("recipientid") RequestBody recipientid,
            @Part("public_key") RequestBody public_key,
            @Part("second_secret") RequestBody second_secret,
            @Part("address") RequestBody address,
            @Part("device_type") RequestBody device_type
    );


    @Multipart
    @POST("add-my-vote")
    Call<ResponseBody> addMyVotesCall(
            @Query("token") String token,
            @Part("secret") RequestBody secret,
            @Part("delegate_key") RequestBody delegate_key,
            @Part("public_key") RequestBody public_key,
            @Part("second_secret") RequestBody second_secret,
            @Part("address") RequestBody address,
            @Part("vote_type") RequestBody vote_type,
            @Part("device_type") RequestBody device_type
    );

    @Multipart
    @POST("send-refferal")
    Call<ResponseBody> sendReferralCall(@Query("token") String token,
                                        @Part("address") RequestBody address,
                                        @Part("email") RequestBody email
    );

    @Multipart
    @POST("refferal-list")
    Call<ResponseBody> refferalListCall(@Query("token") String token,
                                        @Part("address") RequestBody address
    );

//    @POST("referal-list ")
//    Call<ReferralListNew> getReferralListNew(@Query("token") String token);

    @Multipart
    @POST("reward-history")
    Call<ResponseBody> rewardListCall(@Query("token") String token,
                                      @Part("address") RequestBody address
    );

    @Multipart
    @POST("refferal-stake")
    Call<ResponseBody> referralstakeCall(@Query("token") String token,
                                         @Part("address") RequestBody address
    );

    @POST("send-koin-pooling")
    Call<ResponseBody> sendKoinPooling(@Body HashMap<String, String> hm);

    @POST("send-koin-pooling-dev")
    Call<ResponseBody> sendKoinPoolingNew(@Body HashMap<String, String> hm);

    //app version 1.0.13
    @POST("send-koin-pooling-dev-new")
    Call<ResponseBody> sendKoinPoolingAutoTrans(@Body HashMap<String, String> hm);

    //app version 1.0.16 change by the vinod sir
   //  @POST("apitesting/send-koin-pooling-dev-new-manual-test")

    @GET("thirdFace/exchange-rate")
    Call<ResponseBody> getCurrencyExchangeRateList();

    @POST("check-refcode-exist")
    Call<checkRefferalModel> getRefferalCode(@Body HashMap<String, String> hm);

    @POST("get-user-token")
    Call<checkRefferalModel> getUserToken(@Body HashMap<String, String> hm);

    @POST("get-downline")
    Call<checkRefferalModel> getDowlineview(@Body HashMap<String, String> hm);

    @POST("commondetails/map-registation-without-subscription")
    Call<checkRefferalModel> checkSusbcription(@Query("token") String token);

   /*
    @POST("subscribe-package")
    Call<mapregistrationCombineModel> getSubscriptionREgiterCombine(@Body HashMap<String, String> hm);
   */

    @POST("create-user-from-sam")
    Call<checkRefferalModel> cretaeUserFromSam(@Body HashMap<String, String> hm);

    @POST("user-wallet/convert-sam_koin-to-php")
    Call<mapSubscriptionModel> sendSamKoinInPHPAmount(@Query("token") String token,@Body HashMap<String, String> hm);

    @POST("check-user-active")
    Call<checkRefferalModel> CheckUserActive(@Body HashMap<String, String> hm);

    @POST("login")
    Call<mapLoginModel> mapLoginApp(@Body HashMap<String, String> hm);

    @POST("get-direct-purchase-list")
    Call<direcetBidModel> getDirectPurches(@Query("token") String token);

    @GET("progress")
    Call<navigationModel> mapNavigationData(@Query("token") String token);

    @GET("summary")
    Call<summaryModelNavi> mapSummaryData(@Query("token") String token);

    @GET("funnel")
    Call<finnelModel> getFunnelData(@Query("token") String token,@Query("status") String status);

    @GET("phase-one-bonus")
    Call<phaseOneBonusModel> getPhaseOneBonusData(@Query("token") String token);

    @GET("direct-bonus")
    Call<directReferralModel> getDirectbonus(@Query("token") String token,@Query("search") String status);

    @GET("daily-bonus")
    Call<dailyBonusModel> getDailyBonus(@Query("token") String token);

    @GET("power-bonus")
    Call<powerOfXModel> getPowerBonus(@Query("token") String token);

    @GET("platinum-bonus")
    Call<powerOfXModel> getPlatinumBonus(@Query("token") String token);

    @GET("titanium-bonus")
    Call<powerOfXModel> getTitaniumBonus(@Query("token") String token);

    @GET("power-bonus-details")
    Call<powerXSubFragment> getPowerBonusDetails(@Query("token") String token,@Query("from_date") String from_date, @Query("to_date") String to_date);

    @GET("platinum-bonus-details")
    Call<powerXSubFragment> getPlatinumDetails(@Query("token") String token,@Query("from_date") String from_date, @Query("to_date") String to_date);

    @GET("titanium-bonus-details")
    Call<powerXSubFragment> getTitaniumDetails(@Query("token") String token,@Query("from_date") String from_date, @Query("to_date") String to_date);

    @GET("overflow")
    Call<overFlowModel> getOverflowBonus(@Query("token") String token);

    @GET("group-bonus")
    Call<GroupBonusModel> getGroupBonus(@Query("token") String token);

    @GET("group")
    Call<groupModel> getGroup(@Query("token") String token,@Query("groupno") String groupno);

    @POST("map-activity/list")
    Call<activityModel> mapActivityList(@Query("token") String token);

    @POST("arp/create-arp-wallet")
    Call<arpstausModel> getArpStatus(@Query("token") String token);

    @POST("arp/arp-wallet-status")
    Call<ARPWalletBalanceModel> getArpWalletBalance(@Query("token") String token);

    @POST("userauth/change-mail-status")
    Call<ARPWalletBalanceModel> getChangeEmail(@Query("token") String token);

    @POST("sfio/get-user-sum-subscription-points")
    Call<sfioHeaderModel> getSumSubsciption(@Query("token") String token);

    @POST("sfio/sfio-list")
    Call<sfioModel> getSFIOData(@Query("token") String token);

    @POST("sfio/get-bank-details")
    Call<bankDepositeModel> getBankDetails(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sfio/add-sfio-ownership")
    Call<arpstausModel> addSfioOwnership(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sfio/update-sfio-ownership")
    Call<owneeshipstausModel> updateSFIOData(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sfio/check-sfio-ownership")
    Call<ownershipStatusModel> checkSfioOwnership(@Query("token") String token);

    @POST("sfio/get-sfio-ownership")
    Call<ownershipStatusModel> getSFIOOwnershipData(@Query("token") String token);

    @POST("sfio/sfio-ownership")
    Call<ownershipListShowModel> getOwnershipList(@Query("token") String token);

    @POST("arp/arp-history")
    Call<ARPHistoryModel> getARPHistory(@Query("token") String token);

     @POST("arp/arp-redeem-list")
    Call<ARPHistoryModel> getARPRedeem(@Query("token") String token);

    @POST("arp/arp-direct-referral")
    Call<ARPDirectModel> getARPDirect(@Query("token") String token);

    @POST("sfio/usd-to-others")
    Call<currencyModelClick> getUSDTToConvert(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sfio/sfio-bank-details-save")
    Call<depositeModelSaveData> getSFIOBankDepositeSave(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("map-activity/add")
    Call<addActivityModel> addActivity(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("arp/transfer-arp-balance")
    Call<addActivityModel> transaferarpBalance(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("userauth/profile-bank-details-save")
    Call<sfioModel> addBankPRofileDetail(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("userauth/profile-bank-details-list")
    Call<bankDetailsModel> getBankPRofileDetail(@Query("token") String token);

    @POST("get-user-all-package")
    Call<userPackagesModel> getAllPackage(@Body HashMap<String, String> hm);

    @POST("sfio/sfio-monthly-list")
    Call<sfioSubPackageModel> getSFIOSubPAckage(@Query("token") String token, @Body HashMap<String, String> hm);


    @POST("sfio/sfio-monthly-status-change")
    Call<sfioSubPackageModel> getRedeemStatus(@Query("token") String token, @Body HashMap<String, String> hm);

    @POST("sfio/sfio-cancellation-status-change")
    Call<sfioSubPackageModel> getCancellationstatus(@Query("token") String token, @Body HashMap<String, String> hm);


    //    http://157.245.52.206/new_api/api/thirdFace/currency-list
//    http://157.245.52.206/new_api/api/thirdFace/exchange-rate

    //    http://157.245.52.206/new_api/api/secondFace/switch-user


//    http://157.245.52.206/api/sendconFace/bank-name
//    http://157.245.52.206/api/sendconFace/email-otp
}
