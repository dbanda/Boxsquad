package com.dalitsob.boxsquad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dalitso on 12/28/2015.
 */
@SuppressLint("ParcelCreator")
public class AddressResultReceiver extends ResultReceiver {

//        public static final Parcelable.Creator<ResultReceiver> CREATOR = new Creator<ResultReceiver>() {

        public final Activity activity;
        public AddressResultReceiver(Activity act,Handler handler) {
            super(handler);
            activity = act;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            String mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);

            Context context = activity.getApplicationContext();
            CharSequence text = "Hello toast!" + mAddressOutput;
            int duration = Toast.LENGTH_SHORT;

            //Toast toast = Toast.makeText(context, text, duration);
            //toast.show();

            TextView streetText = (TextView) activity.findViewById(R.id.street);
            streetText.setText(mAddressOutput);
//
//   displayAddressOutput();

            // Show a toast message if an address was found.
//            if (resultCode == Constants.SUCCESS_RESULT) {
//                showToast(getString(R.string.address_found));
//            }

        }

}
