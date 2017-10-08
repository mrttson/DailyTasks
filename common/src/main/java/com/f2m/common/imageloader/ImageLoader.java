package com.f2m.common.imageloader;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.f2m.common.utils.CommonUltil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by sev_user on 10/27/2016.
 */

public class ImageLoader {

    public static final String SUFFIX_FILE_NAME = ".dat";

    private static final String TAG = CommonUltil.buildTag(ImageLoader.class);

    private static ImageLoader mInstance = new ImageLoader();

    private ImageLoader() {}

    public static ImageLoader getInstance() {
        return mInstance;
    }

    public String getEncodedFileName(String url) {
        String name = null;
        try {
            name = URLEncoder.encode(url, "UTF-8") + SUFFIX_FILE_NAME;
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "Unbelievable =]] UTF-8 is not a valid charset");
        }
        return name;
    }

    public void showImage(final Context context, final String url, final ImageView imgv) {

        Picasso.with(context)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imgv, new Callback() {
                    @Override
                    public void onSuccess() { }

                    @Override
                    public void onError() {
                        Log.d(TAG, "Could not find image on disk cache. Try to download image");
                        Picasso.with(context)
                                .load(url)
                                .into(imgv, new Callback() {
                                    @Override
                                    public void onSuccess() { }

                                    @Override
                                    public void onError() {
                                        Log.v(TAG,"Could not download image with url: " + url);

                                    }
                                });
                    }
                });
    }

////    Don't delete this two functions,
////    these functions use to download file and save to disk with picasso

//    public static void downloadImage(Context ctx, String url){
//        Picasso.with(ctx)
//                .load("http://blog.concretesolutions.com.br/wp-content/uploads/2015/04/Android1.png")
//                .into(getTarget(url));
//    }
//
//    //target to save
//    private static Target getTarget(final String url){
//        Target target = new Target(){
//            @Override
//            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url);
//                        try {
//                            file.createNewFile();
//                            FileOutputStream ostream = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
//                            ostream.flush();
//                            ostream.close();
//                        } catch (IOException e) {
//                            Log.e("IOException", e.getLocalizedMessage());
//                        }
//                    }
//                }).start();
//            }
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) { }
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) { }
//        };
//        return target;
//    }
}
