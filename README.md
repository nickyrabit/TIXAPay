<<<<<<< HEAD
<<<<<<< HEAD
# Handler USSD API

[![Platform](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17) 
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/romellfudi/VoIpUSSDSample/blob/master/LICENSE)
[![Bintray](https://img.shields.io/bintray/v/romllz489/maven/ussd-library.svg)](https://bintray.com/romllz489/maven/ussd-library)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Void%20USSD%20Library-green.svg?style=flat )]( https://android-arsenal.com/details/1/7151 )
[![Jitpack](https://jitpack.io/v/romellfudi/VoIpUSSDSample.svg)](https://jitpack.io/#romellfudi/VoIpUSSDSample)

### by Romell Dominguez
[![](snapshot/icono.png)](https://www.romellfudi.com/)

## Target Development [High Quality](https://raw.githubusercontent.com/romellfudi/VoIpUSSD/Rev04/snapshot/device_recored.gif):

![](snapshot/device_recored.gif#gif)

To comunicate with ussd display, It is necessary to have present that the interface depends on the SO and on the manufacturer of Android device.

## USSD LIBRARY

`latestVersion` is 1.1.b

Add the following in your app's `build.gradle` file:

```groovy
repositories {
    jcenter()
}
dependencies {
    implementation 'com.romellfudi.ussdlibrary:ussd-library:{latestVersion}'
}
```

* Writing xml config file from [here](https://github.com/romellfudi/VoIpUSSD/blob/master/ussd-library/src/main/res/xml/ussd_service.xml) to res/xml folder (if necessary), this config file allow link between App and SO:

```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    .../>
```

### Application

Puts dependencies on manifest, into manifest put CALL_PHONE, READ_PHONE_STATE and SYSTEM_ALERT_WINDOW:

```xml
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

Add service:

```xml
    <service
        android:name="com.romellfudi.ussdlibrary.USSDService"
        android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
        <intent-filter>
            <action android:name="android.accessibilityservice.AccessibilityService" />
        </intent-filter>
        <meta-data
            android:name="android.accessibilityservice"
            android:resource="@xml/ussd_service" />
    </service>
```

# How use:

Instance an object ussController with activity

```java
ussdController = USSDController.getInstance(activity);
ussdController.callUSSDInvoke(phoneNumber, new USSDController.CallbackInvoke() {
    @Override
    public void responseInvoke(String message) {
        // message has the response string data
        dataToSend // send "data" into USSD's input text
        ussdController.send(dataToSend,new USSDController.CallbackMessage(){
            @Override
            public void responseMessage(String message) {
                // message has the response string data from USSD
            }
        });
    }

    @Override
    public void over(String message) {
        // message has the response string data from USSD
        // response no have input text, NOT SEND ANY DATA
    }
});

```

if you need work with your custom messages, use this structure:

```java
ussdController.callUSSDInvoke(phoneNumber, new USSDController.CallbackInvoke() {
    @Override
    public void responseInvoke(String message) {
        // first option list - select option 1
        ussdController.send("1",new USSDController.CallbackMessage(){
            @Override
            public void responseMessage(String message) {
                // second option list - select option 1
                ussdController.send("1",new USSDController.CallbackMessage(){
                    @Override
                    public void responseMessage(String message) {
                        ...
                    }
                });
            }
        });
    }

    @Override
    public void over(String message) {
        // message has the response string data from USSD
        // response no have input text, NOT SEND ANY DATA
    }
    ...
});
```

## OverlayShowingService Widget (not required)

A problem huge working with ussd is you cant invisible, disenable, resize or put on back in progressDialog
But now on Android O, Google allow build a nw kind permission from overlay widget, my solution was a widget call OverlayShowingService:
For use need add permissions at AndroidManifest:

```xml
<uses-permission android:name="android.permission.ACTION_MANAGE_OVERLAY_PERMISSION" />
```

Add Broadcast Service:

```xml
<service android:name="com.romellfudi.ussdlibrary.OverlayShowingService"
         android:exported="false" />
```

Invoke like a normal services, need a tittle set extra vallue `EXTRA`:

```java
Intent svc = new Intent(activity, OverlayShowingService.class);
svc.putExtra(OverlayShowingService.EXTRA,"PROCESANDO");
getActivity().startService(svc);
```

### EXTRA: Use Voip line

In this secction leave the lines to call to Telcom (ussd hadh number) for connected it:

```java
ussdPhoneNumber = ussdPhoneNumber.replace("#", uri);
Uri uriPhone = Uri.parse("tel:" + ussdPhoneNumber);
context.startActivity(new Intent(Intent.ACTION_CALL, uriPhone));
```

Once initialized the call will begin to receive and send the **famous USSD windows**

![image](snapshot/telcom.png#center)

<style>
img[src*='#center'] { 
    width:390px;
    display: block;
    margin: auto;
}
img[src*='#gif'] { 
    width:200px;
    display: block;
    margin: auto;
}
</style>
=======
# TIXAPay
This is an app to securely assist USSD use cases for Tanzanians
>>>>>>> c8d6d3f715b0127aa7af1526d9584b24368a4694
=======

>>>>>>> b3c88173b3f5a9472845e5212f417dc329a10c0e
