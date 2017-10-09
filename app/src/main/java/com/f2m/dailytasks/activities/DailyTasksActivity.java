package com.f2m.dailytasks.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.f2m.common.base.BaseActivity;
import com.f2m.common.managers.AppManager;
import com.f2m.common.managers.SharedPrefManager;
import com.f2m.common.utils.CommonUltil;
import com.f2m.dailytasks.R;
import com.f2m.dailytasks.fragments.FragmentsManager;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import static com.f2m.common.managers.SharedPrefManager.SHARED_PREF_KEY_SAVED_USER_AVATAR;

public class DailyTasksActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = CommonUltil.buildTag(DailyTasksActivity.class);

    private FragmentManager mFragmentManager = null;
    private Fragment mFragment = null;



    private ImageView imgvBgHeader;
    private ImageView imgvUserAvatar;
    private ImageView imgvEditProfile;

    private SharedPrefManager mSharedPrefManager = AppManager.getInstance().getSharedPrefManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_tasks_main);
        setupDrawer();

        mFragmentManager = getSupportFragmentManager();
        mFragment = FragmentsManager.getInstance().getFragment(FragmentsManager.FrType.FRG_LIST_TASK);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.daily_tasks_content, mFragment);
        transaction.commit();
    }



    private void setupNavigationHeader() {
        Log.d(TAG, "setupDrawer");

        Picasso.with(this)
                .load(R.drawable.bg_nav_drawer)
                .into(imgvBgHeader);

        loadUserAvatar();
    }

    private void loadUserAvatar() {
        Log.d(TAG, "loadUserAvatar");

        String avatarPath = mSharedPrefManager.getString(SHARED_PREF_KEY_SAVED_USER_AVATAR, null);
        Log.d(TAG, "avatarPath >> " + avatarPath);
        if (avatarPath == null) {
            Picasso.with(this)
                    .load(R.drawable.ic_user_empty)
                    .into(imgvUserAvatar);
        } else {
            Picasso.with(this)
                    .load(new File(avatarPath))
                    .into(imgvUserAvatar);
        }
    }

    private void setupDrawer() {
        Log.d(TAG, "setupDrawer");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddTask);
//        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.TRANSPARENT);

        final NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setItemIconTintList(null);

        imgvBgHeader = (ImageView) nav.getHeaderView(0).findViewById(R.id.imgvBgHeader);
        imgvUserAvatar = (ImageView) nav.getHeaderView(0).findViewById(R.id.imgvUserAvatar);
        imgvUserAvatar.setOnClickListener(this);
        imgvEditProfile = (ImageView) nav.getHeaderView(0).findViewById(R.id.imgvEditProfile);
        imgvEditProfile.setOnClickListener(this);
        final FrameLayout content_container = (FrameLayout) findViewById(R.id.daily_tasks_content);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                float moveFactor = (nav.getWidth() * slideOffset);
                content_container.setTranslationX(moveFactor);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupNavigationHeader();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_daily_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_AddTask) {
            startActivity(new Intent(this, NewTaskActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_period_day) {

        } else if (id == R.id.nav_period_week) {

        } else if (id == R.id.nav_period_month) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
//            case R.id.fabAddTask:
//                startActivity(new Intent(this, NewTaskActivity.class));
//                break;

            case R.id.imgvUserAvatar:
                chooseAndCropAvatar();
                break;
        }
    }

    private void chooseAndCropAvatar() {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1,1)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                .setOutputCompressQuality(100)
                .start(this);

//        // start cropping activity for pre-acquired image saved on the device
//                CropImage.activity(imageUri)
//                        .start(this);
//
//        // for fragment (DO NOT use `getActivity()`)
//                CropImage.activity()
//                        .start(getContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.d(TAG, "resultUri >> " + resultUri.toString());

                String savedPath = saveAvatar(resultUri);
                Log.d(TAG, "savedPath >> " + savedPath);

                if (savedPath!= null) {
                    File savedFile = new File(savedPath);
                    if (savedFile != null && savedFile.exists()) {
                        Picasso.with(this)
                                .load(savedFile)
                                .into(imgvUserAvatar);
                    } else {
                        Log.d(TAG, "savedFile is not existed");
                    }
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private String saveAvatar(Uri uriCroppedImage) {
        File image = new File(uriCroppedImage.getPath());

        if (image.exists() && image.isFile()) {

            String imagePath = image.getPath();
            String dataLocation = AppManager.getInstance().getAppDataLocation();
            String destPath = dataLocation + "/avatars/"
                    + System.currentTimeMillis()
                    + imagePath.substring(imagePath.lastIndexOf("."));
            File destFile = new File(destPath);

            try {
                CommonUltil.copyFile(image, destFile);
                SharedPrefManager spref = AppManager.getInstance().getSharedPrefManager();
                spref.putString(SHARED_PREF_KEY_SAVED_USER_AVATAR, destPath);
                return destPath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
