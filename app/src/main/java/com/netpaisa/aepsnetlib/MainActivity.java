package com.netpaisa.aepsnetlib;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.netpaisa.aepsriseinlib.AepsRiseinActivity;

//import com.netpaisa.aepsriseinlib.AepsRiseinActivity;

//import org.json.JSONObject;
//import org.json.XML;

//import fr.arnaudguyon.xmltojsonlib.XmlToJson;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView mGoToAEPS = (TextView) findViewById(R.id.idGoToAEPS);
        mGoToAEPS.setOnClickListener(this);

      // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff00DDED));

       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xAA00ACED));
       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff00DDED));


        /*        window.setStatusBarColor(Color.rgb(seekBarRed.getProgress()
                ,seekBarGreen.getProgress(),seekBarBlue.getProgress()));*/


       //        String resultPidData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PidData>   <DeviceInfo dc=\"25c1bec4-12f3-4ace-8aac-f6b72774b13f\" dpId=\"Morpho.SmartChip\" mc=\"MIIEHDCCAwSgAwIBAgIGAXqj8b8kMA0GCSqGSIb3DQEBCwUAMIG4MSEwHwYDVQQDDBhEUyBTTUFSVCBDSElQIFBWVCBMVEQgMTkxHjAcBgNVBDMTFVBMT1QgTk8gMSBBIFNFQ1RPUiA3MzEOMAwGA1UECQwFTk9JREExFjAUBgNVBAgMDVVUVEFSIFBSQURFU0gxITAfBgNVBAsMGERJR0lUQUwgU0VSVklDRSBERUxJVkVSWTEbMBkGA1UECgwSU01BUlQgQ0hJUCBQVlQgTFREMQswCQYDVQQGEwJJTjAeFw0yMTA3MTQwNzM2MDdaFw0yMTA4MTMwNzM2MDdaMIHFMRQwEgYDVQQKDAtNQVJQSE9SRFBPQzEMMAoGA1UECwwDRFNBMTEwLwYJKoZIhvcNAQkBFiJwYW5rYWouYWdhcndhbEBzbWFydGNoaXBvbmxpbmUuY29tMQ4wDAYDVQQHDAVOb2lkYTEWMBQGA1UECAwNVXR0YXIgUHJhZGVzaDELMAkGA1UEBhMCaW4xNzA1BgNVBAMMLnJkX2RldmljZV8yNWMxYmVjNC0xMmYzLTRhY2UtOGFhYy1mNmI3Mjc3NGIxM2YwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCrdJdD5EbjeVyRmEf6K4SeZ28FBr4D41VCbwgDa/MfGg+iK6RCXRpXOO6+OUddhm++11po1Rt0MzpI0Bj5Nrm5S/1CfUrVMesei3QPMyRgoVoEmVLuzAyNkxz6skAj7a+SPixG7P/KO4NRWsQDp7u7/JL4/q9hYKQMBuCUydT39baZcD2k1jMUl5lSouoGs9lMK4A9y00NPnMKymw/fUzp7aXGbrfcPqacyGo94aG+OsZpCslWdDHeSSkwonOQB3zQKXINGPM18Gpyvxmorr5eey4gTDJgkWzHo7yg3wQcMfhyXoE4qXninOJx/66CA2HYm+hDiXCAa+aAj5sMMh37AgMBAAGjHTAbMAwGA1UdEwQFMAMBAf8wCwYDVR0PBAQDAgGGMA0GCSqGSIb3DQEBCwUAA4IBAQAfoMn9LPOQ6a2acduCUQSfNSCNWJB6XnGh74PdcnbT9kjm4vqRjJPvEcGI8sxyO21Z+AJeZZZjjYNpYmXgEgD2ypQ1lbS1Ba7Km8bwBz2gDKN5gAij8mpXJiNb6WVN7q2+doEcPDJa2xJYHfCii3dbFncD1sUfeYfxGVtIyD7wC3aIwcq81p9W7sgsJv0J2lvM+HPyL0WYJMSBFWghevauouPrN1VGGFWtyuUFtuqNWy4+vnAomfHdZW/j2krf0j7MxjcAIODAJyM04s41e5/IZfb3HxdWZVlKB5Bh2cq8ePVSI6fo4N72NjPhMys62sXxN2EEkb3tFRLFzQjxSqRJ\" mi=\"MSO1300E2L0SW\" rdsId=\"SCPL.AND.001\" rdsVer=\"1.1.5\">      <additional_info>         <Param name=\"serial_number\" value=\"1944I010080\"/>         <Param name=\"srno\" value=\"1944I010080\"/>      </additional_info>   </DeviceInfo>   <Data type=\"X\">MjAyMS0wNy0xNFQxNTozMTowMRAHCUUcnNTXV3lLE5npvv9D9/f3M/Xjas5yNXEptES9uJT9jSlUWEUlc89+Iws4cA2GUI1gd9ZuUDo+Wzbx8JMqAmqDRfn+g58Mt9YPUM6b+szMsw42pcsg50cLQwdMFYsDFMzFY+oqV45qKRsWNUWJmy0rbdJMKZr5xwEipONdV7si1MavAiXTit37/d44gHXwuo/vXS1+Wt2kSXc5DeVwpVDZ89DWqLLgTBVnilsr/h77eCH7WDSBOPG0p1amBnp4wIRONSRP+XeqpxomArQ987PSPdxx5hQV6zv3OZZvLU4/QgLGIGgqXHBh2RfQIzQCNDROJUGobphbl/6v3sLWhfJ6gFkLQxzKodOSxLZa22MN7enS9qM4ea62Sl6h9+RKB36AV/DVETbUTF6ppWj76yfDWnxlo4qfQ3iaBCjC6Q6GmV2ZItqP1TKGEpoxKrvzXtALnrdHUhSw+0NBRhqlaJeRNK97DtJXScH+XalEE6CBK2ZI7BC3GGlJkDGr9Yqa+0En/6CmARO4raF/VZLxgBzusfMEVFadBPmj050YFtjRsMJZMBGrl0hbL3VJt0rov89hQZDi8+WswjcIobX2+zAE7FqdHIMQSlFUlx2kphNSUfJN60Nraj8t2q1oHAzFjLDnEDF/Iien3TmEcSVtcvHo071R9jKtb9E8nAQ9/mOq0n7gNYpkQ83REriYWUkXDo4TDqMLo1VOkb0HS3KSvRmyk2TdYcJRu/lOZVFtG89Btx1R61vzfmAWAX6aB4hPXSnIGeRisLCJ5HDcTOYSNFdEL2peN4n7kJexlsQEnJrA/Jz75jvKRDUpKdn7JKkgueuRkfmrGvgSkXdyBCtkfkquECE09Khduns0Ei8m3bLc4Ff8z+nt9CDAPASRwZLJlD795pCIO6CxrDTitwO5gq9vpBgYX9rsXLZQ/8d+gUlxp618tiJbpZzcUj2L7xocRKHeUgMmemKpmvhiSvcjhZUBHquw3YacEzI5p+qt/0czXrsEbkTqgYDPVCZXBeX9DnKpodl6Zi0tJXYEFHuIsaeaWAK3TuqBGL2rc19ysVf0mb2VhFyo1kWHUqUUxPe9W9k1xqGYEZbTDFhuoKVGqFujF7mlEB8CjZWe/fCC/QCgfxn9HcSzFaNxThaZ9HQ4IzU92GMT5lgTVdZolldz44wuGX0JuJ0FA2q1Qt6nWVh9HNfM/BzH7yPj+4DzL+8CS86Xf3D+PDsd2fSZVZRPH6n4</Data>   <Skey ci=\"20221021\">Nn/+w849ZFKLv7N67EQWV3y9jPaKaZahpdK2wHrtR7QMgwL8itbF3lzW39tCOYn7L8gI5CtZNkvBmHWVsfSkckLlSJ1N8CZ9ogOFlCUTB+52ePMo/7J7t209QqfkR/BjB+S7KKi1qW1R/W/aURBg2yCmU3fffEeTgZmXWKcOIcRocGR+GPVcSlIHCWAR6IWv7Lb/Wck9E2A4/ePen1+ClOtuqUHIj8rccx6THcU+o7VRns3bHk5qk9jLeZ1QA4oI4Njf0mQzCzc+yOQTysV94Z7ZUp5Oy60fZNmHR52R1woNAuB+mu48HjhjHchQ16VyFacQSEryvyNdv87PE7fecA==</Skey>   <Hmac>pLJK2gKBBybLJMDRJTHqX/RQMluHThL748QU6JZQ1QFWUTe4JCwEnqOvMhMAVuYQ</Hmac>   <Resp errCode=\"0\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" nmPoints=\"38\" pCount=\"0\" pType=\"\" qScore=\"40\"/></PidData>";
//        JSONObject jsonObj = null;
//        try {
//            //jsonObj = XML.toJSONObject(resultPidData);
//
//           XmlToJson xmlToJson = new XmlToJson.Builder(resultPidData).build();
//            // convert to a JSONObject
//           jsonObj = xmlToJson.toJson();
//            Log.e("jsonObj", jsonObj+"" );
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }



    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.idGoToAEPS:

                Intent intentTrans = new Intent(MainActivity.this, AepsRiseinActivity.class);
                intentTrans.putExtra("api_access_key", "2755870fa87c2aed97skg43fa9e49db1");
                intentTrans.putExtra("outletid", "16476");
                intentTrans.putExtra("pan_no", "AZRPG6750B");
                intentTrans.putExtra("app_label", "Aeps One");
                intentTrans.putExtra("primary_color", R.color.colorPrimary);
                intentTrans.putExtra("accent_color", R.color.colorAccent);
                intentTrans.putExtra("primary_dark_color", R.color.colorPrimaryDark);
                intentTrans.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentTrans);

                break;
        }
    }
}