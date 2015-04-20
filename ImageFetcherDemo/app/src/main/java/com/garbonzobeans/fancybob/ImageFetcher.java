package com.garbonzobeans.fancybob;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bfriedman on 4/13/15.
 */
public class ImageFetcher extends AsyncTask<String,Bitmap,Bitmap>{

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL("http://www.sharewallpapers.org/d/446459-1/2008+You+Dont+Mess+With+The+Zohan+018.jpg");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setDoInput(true);
            huc.connect();
            InputStream is = huc.getInputStream();

            Bitmap b = BitmapFactory.decodeStream(is);
            return b;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
