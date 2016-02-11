package com.example.vontari.mapphotos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.app.ActivityCompat;

import java.util.List;


public class Maps extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public GoogleMap mMap;

    public Geocoder geocoder;
    public StringBuilder userAddress=new StringBuilder();
    public LatLng userCurrentLocationCoordinates=null;
    public double latitute=0,longitude=0;

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        geocoder = new Geocoder(this);
        final StringBuilder[] userAddress = {new StringBuilder()};
        // Add a marker in Sydney and move the camera
        final LocationManager userCurrentLocation = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        LocationListener userCurrentLocationListener = new LocationListener() {
            @Override

            public void onLocationChanged(Location location) {
                LatLng userCurrentLocationCorodinates = null;
                double latitute = 0, longitude = 0;
                if (ActivityCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //show message or ask permissions from the user.
                    return;
                }
                //Getting the current location of the user.

                latitute = userCurrentLocation
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        .getLatitude();
                longitude = userCurrentLocation
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        .getLongitude();
                userCurrentLocationCorodinates = new LatLng(latitute,longitude);
                //Getting the address of the user based on latitude and longitude.
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitute, longitude, 1);
                    Address address = addresses.get(0);
                    userAddress[0] =  new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        userAddress[0].append(address.getAddressLine(i)).append("\t");
                    }
                    userAddress[0].append(address.getCountryName()).append("\t");

                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                //Setting our image as the marker icon.
                mMap.addMarker(new MarkerOptions().position(userCurrentLocationCorodinates)
                        .title("Your current address.").snippet(userAddress[0].toString())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.person1)));
                //Setting the zoom level of the map.
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userCurrentLocationCorodinates, 7));




            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        LatLng userCurrentLocationCorodinates = null;
        double latitute = 0, longitude = 0;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //show message or ask permissions from the user.
            return;
        }
        //Getting the current location of the user.
        userCurrentLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, userCurrentLocationListener);
        latitute = userCurrentLocation
                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                .getLatitude();
        longitude = userCurrentLocation
                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                .getLongitude();
        userCurrentLocationCorodinates = new LatLng(latitute,longitude);
        //Getting the address of the user based on latitude and longitude.
        try {
            List<Address> addresses = geocoder.getFromLocation(latitute, longitude, 1);
            Address address = addresses.get(0);
            userAddress[0] =  new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                userAddress[0].append(address.getAddressLine(i)).append("\t");
            }
            userAddress[0].append(address.getCountryName()).append("\t");

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //Setting our image as the marker icon.
        mMap.addMarker(new MarkerOptions().position(userCurrentLocationCorodinates)
                .title("Your current address.").snippet(userAddress[0].toString())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.person1)));
        //Setting the zoom level of the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userCurrentLocationCorodinates, 7));


    }


}



