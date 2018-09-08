package com.example.ananthgarg.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    TextView textView1, textView2, textView3, textView4, textView5;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            Log.i("Location",location.toString());
            String lon, lat, alt, acc;
            lat =Double.toString(location.getLatitude());
            lon =Double.toString(location.getLongitude());
            alt =Double.toString(location.getAltitude());
            acc =Double.toString((double)location.getAccuracy());
            Geocoder gecoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try

            {
                List<Address> listaddress = gecoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String address = "Could not find address (:";

                if (listaddress != null && listaddress.size() > 0)

                {
                    address = "Address : " + "\n";
                    if (listaddress.get(0).getThoroughfare() != null) {
                        address += listaddress.get(0).getThoroughfare() + "\n";
                    }
                    if (listaddress.get(0).getLocality() != null) {
                        address += listaddress.get(0).getLocality() + " ";
                    }
                    if (listaddress.get(0).getPostalCode() != null) {
                        address += listaddress.get(0).getPostalCode() + " ";
                    }


                    if (listaddress.get(0).getAdminArea() != null) {
                        address += listaddress.get(0).getAdminArea();
                    }
                    Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                    Log.i("Adddress info", address);
                    textView1.setText("Latitude: " + lat);
                    textView2.setText("Longitude: " + lon);
                    textView3.setText("Altitude: " + alt);
                    textView4.setText("Accuracy: " + acc);
                    textView5.setText(address);
                }
            } catch(
            Exception e)

            {
                e.printStackTrace();
            }

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

        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(lastKnownLocation!=null)
                {
updateLocationInfo(lastKnownLocation);
                }

            }
        }
    }


    public void updateLocationInfo(Location location)
    {
        Log.i("Location",location.toString());
        String lon, lat, alt, acc;
        lat = Double.toString(location.getLatitude());
        lon = Double.toString(location.getLongitude());
        alt = Double.toString(location.getAltitude());
        acc = Double.toString((double) location.getAccuracy());
        Geocoder gecoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listaddress = gecoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = "Could not find address (:";

            if (listaddress != null && listaddress.size() > 0)

            {
                address = "Address : "+"\n";
                if (listaddress.get(0).getThoroughfare() != null) {
                    address += listaddress.get(0).getThoroughfare() + "\n";
                }
                if (listaddress.get(0).getLocality() != null) {
                    address += listaddress.get(0).getLocality() + " ";
                }
                if (listaddress.get(0).getPostalCode() != null) {
                    address += listaddress.get(0).getPostalCode() + " ";
                }


                if (listaddress.get(0).getAdminArea() != null) {
                    address += listaddress.get(0).getAdminArea();
                }
                Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                Log.i("Adddress info", address);
                textView1.setText("Latitude: " + lat);
                textView2.setText("Longitude: " + lon);
                textView3.setText("Altitude: " + alt);
                textView4.setText("Accuracy: " + acc);
                if(address.equals(""))
                {
                    address = "No address";
                }
                textView5.setText(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}