package com.app.kyrostechnologies.recipe;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.kyrostechnologies.recipe.adapter.ZomatoListAdapter;
import com.app.kyrostechnologies.recipe.pojozomato.NearbyRestaurant;
import com.app.kyrostechnologies.recipe.pojozomato.Popularity;
import com.app.kyrostechnologies.recipe.pojozomato.ZomatoApi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Thirunavukkarasu on 02-03-2017.
 */

public class NearByRestaurantFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private static final String TAG = "NearbyRestaurant";
    private View view;
    private SearchView searchView;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private android.location.Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 10000;
    private static int FASTEST_INTERVAL = 5000;
    private static int DISPLACEMENT = 10;
    private double Latitude = 0;
    private double Longitude = 0;
    private RecyclerView recyclerView_zomatolist;
    private ZomatoListAdapter adapter;
    private TextView city_showing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.near_by_restaurant, null);
        recyclerView_zomatolist=(RecyclerView)view.findViewById(R.id.recyclerView_zomatolist);
        city_showing=(TextView)view.findViewById(R.id.city_showing);


        setHasOptionsMenu(true);
        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
        }

        return view;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {
        int resulCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getContext());
        if (resulCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resulCode)) {
                GooglePlayServicesUtil.getErrorDialog(resulCode, getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopLocationUpdates();
    }

    private void getZomatoRestaurants(double Latitude,double Longitude) {
        GooglepojoInter apiservice = ApiClient.getZomatoretrofit().create(GooglepojoInter.class);

        Call<ZomatoApi> call = apiservice.getZomatoRes(String.valueOf(Latitude),String.valueOf(Longitude) );
        call.enqueue(new Callback<ZomatoApi>() {
            @Override
            public void onResponse(Call<ZomatoApi> call, Response<ZomatoApi> response) {
                Log.d("Zomatoresponse", response.body().toString());
                Toast.makeText(getContext(), "Successfully received zomato response", Toast.LENGTH_SHORT).show();
                Popularity popularity=response.body().getPopularity();
                String subzone=popularity.getSubzone();
                if(subzone!=null){
                    String value="Showing Restaurants from "+subzone+".";
                    city_showing.setText(value);
                }else {
                    city_showing.setVisibility(View.GONE);
                }
                int subzone_id=popularity.getSubzoneId();
                String city=popularity.getCity();
                List<NearbyRestaurant> nearbyRestaurant=response.body().getNearbyRestaurants();
                adapter=new ZomatoListAdapter(nearbyRestaurant,getContext());
                RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
                recyclerView_zomatolist.setLayoutManager(manager);
                recyclerView_zomatolist.setItemAnimator(new DefaultItemAnimator());
                recyclerView_zomatolist.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<ZomatoApi> call, Throwable t) {
                Log.e("Error in Zomato api", t.toString());
                Toast.makeText(getContext(), "Error in zomato : " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return true;
            }
        });
        // Detect SearchView icon clicks
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Detect SearchView close
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        searchView.onActionViewCollapsed();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        if(mRequestingLocationUpdates){
            startLocationUpdates();
        }
    }

    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            Latitude = latitude;
            Longitude = longitude;
            String locationtoast = String.valueOf(latitude) + " / " + String.valueOf(longitude);
            Toast.makeText(getContext(), "Location : " + locationtoast, Toast.LENGTH_SHORT).show();
            getZomatoRestaurants(Latitude,Longitude);



        } else {
            Toast.makeText(getContext(), "Could not get Location.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, (LocationListener) this);
    }
    protected void stopLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, (LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("Connection: ","suspended and failed  to get location");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.e("Connection: ","failed to get location"+connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation=location;
        displayLocation();
    }
}
