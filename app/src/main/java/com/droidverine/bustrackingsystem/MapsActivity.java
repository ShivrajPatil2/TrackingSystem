package com.droidverine.bustrackingsystem;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.drive.query.Query;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private String email;
    DatabaseReference locations;
    Double lat,lng;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locations= FirebaseDatabase.getInstance().getReference("Locations");
        if(getIntent()!=null) {
            email=getIntent().getStringExtra("email");
            lat=getIntent().getDoubleExtra("lat",0);
            lng=getIntent().getDoubleExtra("lng",0);

        }
        if(!TextUtils.isEmpty(email))
        {
            loadlocationforthisuser(email);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void loadlocationforthisuser(String email) {
        com.google.firebase.database.Query user_location=locations.orderByChild("email").equalTo(email);
        user_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Tracking tracking=postSnapshot.getValue(Tracking.class);
                    LatLng friendlocation =new LatLng(Double.parseDouble(tracking.getLat()),
                            Double.parseDouble(tracking.getLng()));
                    Location CurrentUser=new Location("");
                    CurrentUser.setLatitude(lat);
                    CurrentUser.setLongitude(lng);

                    Location friend=new Location("");
                    friend.setLatitude(Double.parseDouble(tracking.getLat()));
                    friend.setLongitude(Double.parseDouble(tracking.getLng()));
                    distance( CurrentUser,friend);
                    mMap.addMarker(new MarkerOptions().position(friendlocation).title(tracking.getEmail()
                    ).snippet(""+tracking.getEmail())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(friend.getLatitude(),friend.getLongitude()),12.0f));
                    LatLng current =new LatLng(lat,lng);
                    mMap.addMarker(new MarkerOptions().position(current).title("YOU"));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private double distance(Location currentUser, Location friend) {
        double theta=currentUser.getLongitude()- friend.getLongitude();
        double  dist=Math.sin(deg2rad(currentUser.getLatitude()))
                *Math.sin(deg2rad(friend.getLatitude()))
                *Math.cos(deg2rad(currentUser.getLatitude()))
                *Math.cos(deg2rad(friend.getLongitude())) *
                Math.cos(deg2rad(theta));
        dist=Math.acos(dist);
        dist=rad2deg(dist);
        dist=dist*60*1.1515;
        return (dist);
    }

    private double rad2deg(double rad) {
        return (rad*180 /Math.PI);

    }

    private double deg2rad(double deg) {
        return (deg*Math.PI/180.0);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
}
