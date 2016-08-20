package com.unam.diplomado.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public abstract class MapsActivityAMO extends FragmentActivity
{
    protected GoogleMap mMap;
    protected GoogleApiClient api_client;

    protected final int LOOCATION_REQUEST = 12345;

    protected void currentLocation()
    {
        if(Build.VERSION.SDK_INT >= 23)
        {
            boolean permission_granted = ActivityCompat.checkSelfPermission(MapsActivityAMO.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;

            if(permission_granted)
            {
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                        || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
                {
                    Snackbar.make(this.findViewById(android.R.id.content),
                            "Intentar de nuevo", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Try Again", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(MapsActivityAMO.this, new String[]{
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION}, LOOCATION_REQUEST);
                                }
                            }).show();

                } else {
                    ActivityCompat.requestPermissions(MapsActivityAMO.this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, LOOCATION_REQUEST);
                }
            } else {
                showCurretLocation(LocationServices.FusedLocationApi.getLastLocation(api_client));
            }

        } else {
            showCurretLocation(LocationServices.FusedLocationApi.getLastLocation(api_client));
        }
    }
    protected void showCurretLocation(Location location)
    {
        if(location != null)
        {
            mMap.clear();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            LatLng latlng = new LatLng(latitude, longitude);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).draggable(true));
        }
    }

}
