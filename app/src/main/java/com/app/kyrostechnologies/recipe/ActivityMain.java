package com.app.kyrostechnologies.recipe;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.kyrostechnologies.recipe.data.Constant;
import com.app.kyrostechnologies.recipe.data.Tools;
import com.app.kyrostechnologies.recipe.data.URLSConstant;
import com.app.kyrostechnologies.recipe.fragment.CategoryFragment;
import com.app.kyrostechnologies.recipe.fragment.ExploreFragment;
import com.app.kyrostechnologies.recipe.fragment.FavoritesFragment;
import com.app.kyrostechnologies.recipe.fragment.SettingFragment;
import com.app.kyrostechnologies.recipe.pojocovergoogle.Cover;
import com.app.kyrostechnologies.recipe.pojocovergoogle.CoverPhoto;
import com.app.kyrostechnologies.recipe.pojocovergoogle.GoogleGetCoverPic;
import com.app.kyrostechnologies.recipe.sharedpref.PreferenceManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMain extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 12;
    private static final String TAG = ActivityMain.class.getSimpleName();
    private static final int REQUEST_CHECK_SETTINGS = 12;
    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 17;
    private Toolbar toolbar;
    public ActionBar actionBar;
    private NavigationView navigationView;
    private ImageView nav_header_imageview;
    private TextView click_here_to_login,nav_header_name,nav_header_email,logout_text;
    private LinearLayout nav_header_linearlayout;
    private GoogleApiClient mGoogleApiClient;
    private PreferenceManager store;
    private AlertDialog dialogSignIn;
    private LoginButton login_button_facebook;
    private CallbackManager callbackManager;
    private int selectedlogin=0;
    private  DrawerLayout drawer;
    private  ActionBarDrawerToggle toggle;
    private  View headerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        setContentView(R.layout.activity_main);
      store=PreferenceManager.getStore(ActivityMain.this);
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestIdToken("351480250968-1hdq4nhar6413ov1ka8vhsmgqthga5kd.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        displayLocationSettingsRequest(this);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(ActivityMain.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(ActivityMain.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityMain.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)&&ActivityCompat.shouldShowRequestPermissionRationale(ActivityMain.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(ActivityMain.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerOpened(View drawerView) {
                hideKeyboard();
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
         headerview = navigationView.getHeaderView(0);

        nav_header_imageview = (ImageView) headerview.findViewById(R.id.nav_header_imageview);
        click_here_to_login=(TextView)headerview.findViewById(R.id.click_here_to_login);
        nav_header_name=(TextView)headerview.findViewById(R.id.nav_header_name);
        nav_header_email=(TextView)headerview.findViewById(R.id.nav_header_email);
        logout_text=(TextView)headerview.findViewById(R.id.logout_text);
        nav_header_linearlayout=(LinearLayout)headerview.findViewById(R.id.nav_header_linearlayout);
        click_here_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLogin();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                updateDrawerCounter();
                displayFragment(menuItem.getItemId(), menuItem.getTitle().toString());
                drawer.closeDrawers();
                return true;
            }
        });

        // set initial view
        displayFragment(R.id.nav_explore, getString(R.string.title_nav_explore));

        // for system bar in lollipop
        Tools.systemBarLolipop(this);
    }



    private void DialogLogin() {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this,R.style.ThemeSphinx);
        LayoutInflater inflater= this.getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_sign_in,null);
        dialogBuilder.setView(view);
        final SignInButton sign_in_button_google=(SignInButton)view.findViewById(R.id.sign_in_button_google);
        login_button_facebook=(LoginButton)view.findViewById(R.id.login_button_facebook);
        login_button_facebook.setReadPermissions("email");
        login_button_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sign_in_button_google!=null&&click_here_to_login!=null&&nav_header_name!=null
                        &&nav_header_email!=null&&logout_text!=null){
                    click_here_to_login.setVisibility(View.GONE);
                    nav_header_name.setVisibility(View.VISIBLE);
                    nav_header_email.setVisibility(View.VISIBLE);
                    try{
                        logout_text.setVisibility(View.VISIBLE);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                dialogSignIn.dismiss();
                selectedlogin=1;
            }
        });
        login_button_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"Facebook Register Call back success",Toast.LENGTH_SHORT).show();
                GraphRequest request=GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginFacebookResponse",response.toString());
                                Log.v("Loginfacebookobject",object.toString());
                                try {
                                    String id=object.getString("id");
                                    String name=object.getString("name");
                                    String email=object.getString("email");
                                    JSONObject picture=object.getJSONObject("picture");
                                    JSONObject data=picture.getJSONObject("data");
                                    boolean is_silhouette=data.getBoolean("is_silhouette");
                                    String url=data.getString("url");
                                    JSONObject cover=object.getJSONObject("cover");
                                    String coverid=cover.getString("id");
                                    String offset_y=cover.getString("offset_y");
                                    String source=cover.getString("source");
                                    store.putIsSuccess("IsSuccess");
                                    store.putDisplayName(name);
                                    store.putEmail(email);
                                    store.putId(id);
                                    store.putProfilePic(url);
                                    store.putServerAuthCode(null);
                                    store.putCoverPicture(source);
                                    store.putwhichone("facebook");

                                    updateUI(true);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                Bundle parameters=new Bundle();
                parameters.putString("fields","id,name,email,picture,cover");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(),"Facebook Register Call back Cancelled",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(),"Facebook Register Call back Error : "+exception.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        sign_in_button_google.setSize(SignInButton.SIZE_STANDARD);
        sign_in_button_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedlogin=2;
                signIn();
            }
        });
        dialogSignIn=dialogBuilder.create();
        View v=getWindow().getDecorView();
        v.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSignIn.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSignIn.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogSignIn.show();
        dialogSignIn.setCancelable(true);
        dialogSignIn.setCanceledOnTouchOutside(true);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(selectedlogin==1){
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }else if(selectedlogin==2){
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
                String value= result.getStatus().toString();
                Log.d("values",value);

            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialogSignIn!=null&&dialogSignIn.isShowing()){
            dialogSignIn.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialogSignIn!=null&&dialogSignIn.isShowing()){
            dialogSignIn.dismiss();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                        Toast.makeText(getApplicationContext(),"Please allow location permission ",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            if(dialogSignIn!=null){
                dialogSignIn.dismiss();
            }

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String DisplayName=acct.getDisplayName();
            String Email=acct.getEmail();
            String Id=acct.getId();
            Log.v("IDGoogle",Id);
            Uri ProfilePic=acct.getPhotoUrl();
            String ServerAuthCode=acct.getServerAuthCode();
            store.putIsSuccess("IsSuccess");
            store.putDisplayName(DisplayName);
            store.putEmail(Email);
            store.putId(Id);
            store.putProfilePic(ProfilePic.toString());
            store.putServerAuthCode(ServerAuthCode);
            store.putwhichone("google");
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
            if(dialogSignIn!=null){
                dialogSignIn.dismiss();
            }
        }
    }

    private void updateUI(boolean b) {
        if(b){
            String IsSuccess=store.getIsSuccess();
            if(IsSuccess.equals("IsSuccess")){
                click_here_to_login.setVisibility(View.GONE);
                nav_header_name.setVisibility(View.VISIBLE);
                nav_header_email.setVisibility(View.VISIBLE);
                logout_text.setVisibility(View.VISIBLE);
                String displname=store.getDisplayName();
                String picture=store.getProfilePic();
                String  email=store.getEmail();
                String coverpic=store.getCoverPicture();
                String whichone=store.getwhichone();
                String id=store.getId();
                if(whichone.equals("google")){
                    getgooglecoverpic(id);
                }else if(whichone.equals("facebook")){
                    if(coverpic!=null){
                        Picasso.with(ActivityMain.this).load(coverpic).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                Drawable oi=new BitmapDrawable(bitmap);
                                nav_header_linearlayout.setBackground(oi);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
                    }
                }

                if(displname!=null){
                    nav_header_name.setText(displname);
                }if(email!=null){
                    nav_header_email.setText(email);
                }if(picture!=null){
                    Picasso.with(ActivityMain.this).load(picture).resize(80,80).into(nav_header_imageview) ;
//
                }
                logout_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
  Toast.makeText(getApplicationContext(),"You have Logged Out",Toast.LENGTH_SHORT).show();
                        nav_header_linearlayout.setBackground(null);
                        store.clear();
                        click_here_to_login.setVisibility(View.VISIBLE);
                        nav_header_name.setVisibility(View.GONE);
                        nav_header_email.setVisibility(View.GONE);
                        logout_text.setVisibility(View.GONE);
                        nav_header_imageview.setImageResource(R.drawable.logo_black);
                        try{
                            LoginManager.getInstance().logOut();
                        }catch (Exception e){
                            e.printStackTrace();

                        }

                        try{
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            // ...



                                        }
                                    });
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                });
            }



    }

    }

    private void getgooglecoverpic(String id) {
        GooglepojoInter apiservice=ApiClient.getgoogleRetrofit().create(GooglepojoInter.class);
        Call<GoogleGetCoverPic>call=apiservice.getCoverPic(id, URLSConstant.GoogleKey);
        call.enqueue(new Callback<GoogleGetCoverPic>() {
            @Override
            public void onResponse(Call<GoogleGetCoverPic> call, Response<GoogleGetCoverPic> response) {
                Log.d("CoverResponse",response.body().toString());
                GoogleGetCoverPic mainobj=response.body();
                Cover cover=mainobj.getCover();
                CoverPhoto photo=cover.getCoverPhoto();
                String coverurl=photo.getUrl();
                Log.d("coverurl",coverurl);

                if(coverurl!=null){
                    store.putCoverPicture(coverurl);
                    Picasso.with(ActivityMain.this).load(coverurl).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Drawable oi=new BitmapDrawable(bitmap);
                            nav_header_linearlayout.setBackground(oi);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            Log.e("Error "," in coverpic bitmap error");

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

                }


            }

            @Override
            public void onFailure(Call<GoogleGetCoverPic> call, Throwable t) {
                    Log.e("Error in Cover pic",t.toString());
            }
        });
    }

    private void displayFragment(int id, String title) {
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.nav_explore:
                fragment = new ExploreFragment();
                break;
            case R.id.nav_category:
                fragment = new CategoryFragment();
                break;
            case R.id.nav_favorites:
                fragment = new FavoritesFragment();
                break;
            case R.id.nav_setting:
                fragment = new SettingFragment();
                break;
            case R.id.nav_nearby:
                fragment=new NearByRestaurantFragment();
                break;
        }

        fragment.setArguments(bundle);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_content, fragment);
            fragmentTransaction.commit();
        }
    }
    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(ActivityMain.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    private void updateDrawerCounter(){
        setMenuStdCounter(R.id.nav_favorites, Constant.getItemFavorites(this).size());
    }

    //set counter in drawer
    private void setMenuStdCounter(@IdRes int itemId, int count) {
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView();
        view.setText(count > 0 ? String.valueOf(count) : null);
    }

    @Override
    protected void onResume() {
        updateDrawerCounter();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            doExitApp();
        }
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private long exitTime = 0;
    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Failed","Connection Failed");
        Toast.makeText(getApplicationContext(),"Connection Failed ",Toast.LENGTH_SHORT).show();
    }
}
