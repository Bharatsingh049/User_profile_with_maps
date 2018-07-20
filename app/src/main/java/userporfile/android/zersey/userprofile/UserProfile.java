package userporfile.android.zersey.userprofile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
private CircleImageView imgview;
private TextView Interior,about,Address,FirstName,LastName,profession,subProfession,profile_text1,profile_text2;
private HorizontalScrollView HScrollview;
private ImageView profileImage;


private RelativeLayout layout,Main_Layout,Fragment_layout;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Main_Layout=(RelativeLayout)findViewById(R.id.Main_layout);
        Fragment_layout=(RelativeLayout)findViewById(R.id.fragment_layout);
        layout=(RelativeLayout)findViewById(R.id.Grey_layout);
        about=(TextView)findViewById(R.id.about);
        Address=(TextView)findViewById(R.id.Address);
        FirstName=(TextView)findViewById(R.id.FirstName);
        LastName=(TextView)findViewById(R.id.LastName);
        profession=(TextView)findViewById(R.id.profession);
        profession.setVisibility(View.INVISIBLE);
        subProfession=(TextView)findViewById(R.id.subProfession);
        subProfession.setVisibility(View.INVISIBLE);
        FirstName.setVisibility(View.INVISIBLE);
        LastName.setVisibility(View.INVISIBLE);
        imgview=(CircleImageView)findViewById(R.id.profile_image);
        //profileImage=(ImageView)findViewById(R.id.profileImage);
//        profileImage.setVisibility(View.INVISIBLE);
        Interior=(TextView)findViewById(R.id.Interior_text);
        HScrollview=(HorizontalScrollView)findViewById(R.id.Hscroll_view);
        HScrollview.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);
        profile_text1=(TextView)findViewById(R.id.profile_text1);
        profile_text2=(TextView)findViewById(R.id.profile_text2);
        profile_text1.setVisibility(View.INVISIBLE);
        profile_text2.setVisibility(View.INVISIBLE);
       // Main_Layout.setOnTouchListener((View.OnTouchListener) new SimpleOnGestureListener());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Fragment_layout.setVisibility(View.INVISIBLE);
        mapFragment.getMapAsync(this);


        Main_Layout.setOnTouchListener(new OnSwipeTouchListener(UserProfile.this) {
            public void onSwipeTop() {
                Toast.makeText(UserProfile.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                onswiperight();
                Toast.makeText(UserProfile.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                onswipeleft();
                Toast.makeText(UserProfile.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(UserProfile.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                layout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInDown)
                        .duration(2000)
                        .repeat(0)
                        .playOn(layout);
            }
        }, 100);

        final Handler handler23 = new Handler();
        handler23.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                HScrollview.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInDown)
                        .duration(2000)
                        .repeat(0)
                        .playOn(HScrollview);
            }
        }, 100);
        final Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                imgview.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomIn)
                        .duration(2000)
                        .repeat(0)
                        .playOn(imgview);
            }
        }, 100);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                FirstName.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(2000)
                        .repeat(0)
                        .playOn(FirstName);
            }
        }, 100);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                LastName.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(2000)
                        .repeat(0)
                        .playOn(LastName);
            }
        }, 700);
        final Handler handler12 = new Handler();
        handler12.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                profession.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(2000)
                        .repeat(0)
                        .playOn(profession);
            }
        }, 100);
        final Handler handler22 = new Handler();
        handler22.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                subProfession.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(2000)
                        .repeat(0)
                        .playOn(subProfession);
            }
        }, 100);

        Interior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  onswiperight();
            }

        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           onswipeleft();

            }
        });

        Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // Intent intent=new Intent(UserProfile.this,MapsActivity.class);
                //startActivity(intent);
            }
        });
    }




    public void onswipeleft(){

        Interior.setFocusable(true);
        Interior.requestFocus();
        if(imgview.getVisibility()==View.VISIBLE){
            HScrollview.setSmoothScrollingEnabled(true);
            HScrollview.scrollTo((int)HScrollview.getScrollX() + 100, (int)HScrollview.getScrollY());
            YoYo.with(Techniques.ZoomOut)
                    .duration(2000)
                    .repeat(0)
                    .playOn(imgview);
            //imgview.setVisibility(View.INVISIBLE);
            final Handler handler43 = new Handler();
            handler43.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    imgview.setVisibility(View.INVISIBLE);
                }
            }, 2000);
            YoYo.with(Techniques.SlideOutRight)
                    .duration(2000)
                    .repeat(0)
                    .playOn(LastName);
            //LastName.setVisibility(View.INVISIBLE);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    YoYo.with(Techniques.SlideOutRight)
                            .duration(2000)
                            .repeat(0)
                            .playOn(FirstName);
                    //FirstName.setVisibility(View.INVISIBLE);
                }
            }, 250);
            final Handler handler112 = new Handler();
            handler112.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms

                    YoYo.with(Techniques.SlideOutRight)
                            .duration(2000)
                            .repeat(0)
                            .playOn(profession);
                    //profession.setVisibility(View.INVISIBLE);
                }
            }, 100);
            final Handler handler122 = new Handler();
            handler122.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    //subProfession.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideOutRight)
                            .duration(2000)
                            .repeat(0)
                            .playOn(subProfession);
                    //subProfession.setVisibility(View.INVISIBLE);
                }
            }, 100);
        }
        final Handler handler44 = new Handler();
        handler44.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Fragment_layout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(2000)
                        .repeat(0)
                        .playOn(Fragment_layout);
            }
        }, 100);
        final Handler handler13 = new Handler();
        handler13.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                profile_text1.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(2000)
                        .repeat(0)
                        .playOn(profile_text1);
            }
        }, 100);
        final Handler handler14 = new Handler();
        handler14.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                profile_text2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(2000)
                        .repeat(0)
                        .playOn(profile_text2);
            }
        }, 100);

    }




    public void onswiperight(){

        about.setFocusable(true);
        about.requestFocus();
        if(Fragment_layout.getVisibility() ==View.VISIBLE){
            HScrollview.setSmoothScrollingEnabled(true);
            HScrollview.scrollTo((int)HScrollview.getScrollX() - 100, (int)HScrollview.getScrollY());
            final Handler handler44 = new Handler();
            handler44.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms

                    YoYo.with(Techniques.SlideOutLeft)
                            .duration(2000)
                            .repeat(0)
                            .playOn(Fragment_layout);
                    //profileImage.setVisibility(View.INVISIBLE);
                }
            }, 0);
            final Handler handler43 = new Handler();
            handler43.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    Fragment_layout.setVisibility(View.INVISIBLE);
                }
            }, 2000);
            final Handler handler13 = new Handler();
            handler13.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms

                    YoYo.with(Techniques.SlideOutRight)
                            .duration(2000)
                            .repeat(0)
                            .playOn(profile_text1);
                   // profile_text1.setVisibility(View.INVISIBLE);
                }
            }, 100);
            final Handler handler14 = new Handler();
            handler14.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms

                    YoYo.with(Techniques.SlideOutRight)
                            .duration(2000)
                            .repeat(0)
                            .playOn(profile_text2);
                    //profile_text2.setVisibility(View.INVISIBLE);
                }
            }, 100);




        }
        imgview.setVisibility(View.VISIBLE);

        //profession.setVisibility(View.VISIBLE);
        // subProfession.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn)
                .duration(2000)
                .repeat(0)
                .playOn(imgview);
        FirstName.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(2000)
                .repeat(0)
                .playOn(FirstName);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                LastName.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(2000)
                        .repeat(0)
                        .playOn(LastName);
            }
        }, 500);
        final Handler handler112 = new Handler();
        handler112.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                profession.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(2000)
                        .repeat(0)
                        .playOn(profession);
            }
        }, 100);
        final Handler handler122 = new Handler();
        handler122.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                subProfession.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight)
                        .duration(2000)
                        .repeat(0)
                        .playOn(subProfession);
            }
        }, 100);
    }






    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap = googleMap;
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    */
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, (com.google.android.gms.location.LocationListener) UserProfile.this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
//Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getApplicationContext(),
                    Locale.getDefault());
            try {
                List<android.location.Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    (com.google.android.gms.location.LocationListener) this);
        }

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

}
