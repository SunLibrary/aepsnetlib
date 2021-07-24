# SunLibrary
					
	•	Introduction  NetPaisa Provides AEPS SDK for integration our services in your android app. After the integration you can easily consume transactions (Cash withdrawal, Balance Enquiry, Mini Statement, Aadhaar Pay).

	•	SDK Requirements You need api_access_key, outletid, pan_no(Company PAN)  in order to use this sdk. That will be provided by NetPaisa Team. 

	•	Screenshots from SDK  	 
 
4. INTEGRATION STEPS.  
Step 1. Add the JitPack repository to your build file gradle maven sbt leiningen Add it in your root build.gradle at the end of repositories: 

allprojects {

 repositories {  

                        maven { url 'https://jitpack.io' }
 
                       } 
                    } 


Step 2. In App Gradle Add Dependencies

Note: Make sure you migrated your android app to Android X 
dependencies { 


    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.11'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0"
    implementation 'com.squareup.retrofit2:retrofit-adapters:2.7.1'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'
    implementation 'org.jsoup:jsoup:1.10.2'

    // Gson for Json
    implementation 'com.google.code.gson:gson:2.8.6'

    implementation 'com.intuit.sdp:sdp-android:1.0.6'
    implementation 'com.intuit.ssp:ssp-android:1.0.6'

    implementation 'com.github.smart-fun:XmlToJson:1.5.1'

    implementation 'com.github.SunLibrary:aepsnetlib:1.00.02'
 

 }

  
 
5). Transaction Intent for Balance Enquiry, Cash withdrawal, Mini statement and Aadhaar Pay :
 Intent intentTrans = new Intent(MainActivity.this, AepsRiseinActivity.class);
intentTrans.putExtra("api_access_key", " "); //Provided by NetPaisa Team
intentTrans.putExtra("outletid", " ");//Provided by NetPaisa Team
intentTrans.putExtra("pan_no", " ");//Provided by NetPaisa Team


intentTrans.putExtra("app_label", "xxxxxxxxx");//User Input
intentTrans.putExtra("primary_color", R.color.colorPrimary) //User Input;
intentTrans.putExtra("accent_color", R.color.colorAccent); //User Input
intentTrans.putExtra("primary_dark_color", R.color.colorPrimaryDark); //User Input
intentTrans.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
startActivity(intentTrans);
 
 

