package com.dalitsob.boxsquad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;

/**
 * Created by dbanda on 12/23/15.
 */
public class Messenger {

    private static final String EXTRA_PROTOCOL_VERSION = "com.facebook.orca.extra.PROTOCOL_VERSION";
    private static final String EXTRA_APP_ID = "com.facebook.orca.extra.APPLICATION_ID";
    private static final int PROTOCOL_VERSION = 20150314;
    private static final String YOUR_APP_ID = String.valueOf(R.string.facebook_app_id);
    private static final int SHARE_TO_MESSENGER_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;

    public  static  void send(Uri uri, Activity activity){
        String mimeType = "image/jpeg";

        // contentUri points to the content being shared to Messenger
        ShareToMessengerParams shareToMessengerParams =
                ShareToMessengerParams.

                        newBuilder(uri, mimeType)
                        .build();

        // Sharing from an Activity
        MessengerUtils.shareToMessenger(
                activity,
                REQUEST_CODE_SHARE_TO_MESSENGER,
                shareToMessengerParams);
    }


    public static void sendIntent(Uri contentUri, Activity activity){
        String mimeType = "image/*";


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setPackage("com.facebook.orca");
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        intent.putExtra(EXTRA_PROTOCOL_VERSION, PROTOCOL_VERSION);
        intent.putExtra(EXTRA_APP_ID, YOUR_APP_ID);

        activity.startActivityForResult(intent, SHARE_TO_MESSENGER_REQUEST_CODE);
    }


}
