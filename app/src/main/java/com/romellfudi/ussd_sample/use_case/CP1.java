package com.romellfudi.ussd_sample.use_case;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.romellfudi.ussd_sample.R;
import com.romellfudi.permission.PermissionService;
import com.romellfudi.ussdlibrary.OverlayShowingService;
import com.romellfudi.ussdlibrary.USSDController;

import android.Manifest.permission;

/**
 *
 * Nicholaus
 */
public class CP1 extends Fragment {

    TextView result;
    EditText phone;
    Button btn1,btn2,btn3;
    ImageView networkCarrierIconImageView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new PermissionService(getActivity()).request(
                new String[]{permission.CALL_PHONE},
                callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_op1, container, false);
        result = (TextView) view.findViewById(R.id.result);
        phone = (EditText) view.findViewById(R.id.phone);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn3 = (Button) view.findViewById(R.id.btn3);
        setHasOptionsMenu(false);




        Drawable airtelDrawable = getResources().getDrawable(R.drawable.airtel_logo);

        //Check what is the SIM Network in Slot 1
        if(simCardNetwork().equals("Airtel")){
            networkCarrierIconImageView = view.findViewById(R.id.imageViewCarrierIcon);
            networkCarrierIconImageView.setImageDrawable(airtelDrawable);
            //Place Airtel Branding Icons and Text
        }else {
            ////Some other phone line
        }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     String phoneNumber = phone.getText().toString().trim();
                final Intent svc = new Intent(getActivity(), OverlayShowingService.class);
                svc.putExtra(OverlayShowingService.EXTRA,"INACHAKATA, TAFADHALI SUBIRI...");
                 getActivity().startService(svc);
                final USSDController ussdController = USSDController.getInstance(getActivity());
                result.setText("");
                ussdController.callUSSDInvoke("*102#", new USSDController.CallbackInvoke() {
                    @Override
                    public void responseInvoke(String message) {
                        Log.d("APP",message);

                        result.append("\n-\n" + message);
                        // first option list - select option 1
                        ussdController.send("1",new USSDController.CallbackMessage(){
                            @Override
                            public void responseMessage(String message) {
                                Log.d("APP",message);
                                result.append("\n-\n" + message);
                                // second option list - select option 1
                                ussdController.send("1",new USSDController.CallbackMessage(){
                                    @Override
                                    public void responseMessage(String message) {
                                        Log.d("APP",message);


                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void over(String message) {
                        Log.d("APP",message);

                        String[] separated = message.split(" ");
                        result.setText("");


                        result.append("\n-\n" + "Salio Lako ni Shilingi "+ separated[4]);
                           getActivity().stopService(svc);
                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent svc = new Intent(getActivity(), OverlayShowingService.class);
                //svc.putExtra(OverlayShowingService.EXTRA,"TITTLE");
                getActivity().startService(svc);

                Log.d("APP","START OVERLAY DIALOG");
                String phoneNumber = phone.getText().toString().trim();
                final USSDController ussdController = USSDController.getInstance(getActivity());
                result.setText("");
                ussdController.callUSSDInvoke(phoneNumber, new USSDController.CallbackInvoke() {
                    @Override
                    public void responseInvoke(String message) {
                        Log.d("APP",message);
                        result.append("\n-\n" + message);
                        // first option list - select option 1
                        ussdController.send("1",new USSDController.CallbackMessage(){
                            @Override
                            public void responseMessage(String message) {
                                Log.d("APP",message);
                                result.append("\n-\n" + message);
                                // second option list - select option 1
                                ussdController.send("1",new USSDController.CallbackMessage(){
                                    @Override
                                    public void responseMessage(String message) {
                                        Log.d("APP",message);
                                        result.append("\n-\n" + message);
                                        getActivity().stopService(svc);
                                        Log.d("APP","STOP OVERLAY DIALOG");
                                        Log.d("APP","successful");
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void over(String message) {
                        Log.d("APP",message);
                        result.append("\n-\n" + message);
                        getActivity().stopService(svc);
                        Log.d("APP","STOP OVERLAY DIALOG");
                    }
                });
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USSDController.verifyAccesibilityAccess(getActivity());
            }
        });

        return view;
    }

    private PermissionService.Callback callback = new PermissionService.Callback() {
        @Override
        public void onRefuse(ArrayList<String> RefusePermissions) {
            Toast.makeText(getContext(),
                    getString(R.string.refuse_permissions),
                    Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        @Override
        public void onFinally() {
            // pass
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(getActivity())) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getActivity().getPackageName()));
                    startActivity(intent);
                }
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        callback.handler(permissions, grantResults);
    }


    // Which phone network is it on SIM ONE
    public String simCardNetwork(){
        TelephonyManager tManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);

        String sim1 = tManager.getNetworkOperatorName();

        Log.d("SIM_ONE","SIM ONE "+sim1);
        return  sim1;
    }
}

