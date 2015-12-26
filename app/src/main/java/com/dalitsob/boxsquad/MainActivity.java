package com.dalitsob.boxsquad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Profile profile = (Profile)intent.getParcelableExtra("profile");
        final AccessToken accessToken = (AccessToken) intent.getParcelableExtra("token");

        class DownloadImageTask extends AsyncTask<Uri, Void, Bitmap> {
             /** The system calls this to perform work in a worker thread and
             * delivers it the parameters given to AsyncTask.execute() */
             @Override
             protected Bitmap doInBackground(Uri... uri) {
                //InputStream in = (InputStream) new URL(uri.toString()).getContent();
                Bitmap bitmap = null;
                try {
                    URL imageURL = new URL(uri[0].toString());
                    bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

                    //bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), uri[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return bitmap;
             }


            /** The system calls this to perform work in the UI thread and delivers
             * the result from doInBackground() */
            protected void onPostExecute(Bitmap result) {
                imageView.setImageBitmap(result);
            }
        }

        final Uri imageUri = profile.getProfilePictureUri(800, 800);
        new DownloadImageTask().execute(imageUri);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Welcome: " + profile.getName());

//        GraphRequest request = GraphRequest.newMeRequest(
//                accessToken,
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        // Application code
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link");
//        request.setParameters(parameters);
//        request.executeAsync();


        Button skypeBtn = (Button) findViewById(R.id.skypeBtn);
        Button messengerBtn = (Button) findViewById(R.id.messengerBtn);

        skypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uri uri = new Uri.Builder().path("skype:skype.test.user.1?call").build();
                Skype.initiateSkypeUri(v.getContext(), "skype:skype.test.user.1?call");
            }
        });


        messengerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Messenger.send(imageUri, MainActivity.this);
            }
        });
    }

}
