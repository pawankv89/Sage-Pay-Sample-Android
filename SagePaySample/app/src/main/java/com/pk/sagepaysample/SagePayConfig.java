package com.pk.sagepaysample;

/**
 * Created by pawan on 06/04/18.
 */

public class SagePayConfig {

    //SagePayment credentials
    public final String sagePay_TESTURL  = "https://test.sagepay.com/gateway/service/vspform-register.vsp";
    public final String sagePay_LIVEURL  = "https://live.sagepay.com/gateway/service/vspform-register.vsp";
    public final String sagePay_SuccessURL  = "https://pawankv89.github.io/SagePaySuccess/index.html";
    public final String sagePay_FailureURL  = "https://pawankv89.github.io/SagePayFailed/index.html";
    public final String sagePay_VendorEmail  = "gopalreddy2440@gmail.com";
    public final String sagePay_VPSProtocol  = "3.00";
    public final String sagePay_TxType  = "DEFERRED";
    public final String sagePay_Vendor  = "protxross";
    public final String sagePay_Password  = "TPjs72eMz5qBnaTa";

    private static SagePayConfig instance;

    private SagePayConfig(){}  //private constructor.

    public static SagePayConfig getInstance(){
        if (instance == null){ //if there is no instance available... create new one
            instance = new SagePayConfig();
        }

        return instance;
    }

    /*

    //Amex
     //3742 0000 0000 004
     //01/19
     //1234
     */
}
