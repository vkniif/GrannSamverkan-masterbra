package kniif.grannsamverkan;




import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;


public class MainActivity extends AppCompatActivity {
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMapView = (MapView) findViewById(R.id.mapView);
        ArcGISMap map = new ArcGISMap(Basemap.Type.STREETS, 60.674880, 17.141273, 16);
        mMapView.setMap(map);

        //Behöver en locationmanager för att hitta vår
        //GPS provider m.m.
        LocationManager locationManager;
        //Hämta enhetens gps
        String svcName = Context.LOCATION_SERVICE;
        //Hämta provider
        locationManager = (LocationManager) getSystemService(svcName);
        String provider = LocationManager.GPS_PROVIDER;
        //här kommer felhantering
        //sätt upp själva positions lyssnaren
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            //För att appen ska fråga telefonen om tillåtelse
            // att använda GPS:en när den installeras
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET }
                        ,10
                );
            }
            return;
        }
        locationManager.requestLocationUpdates(provider, 0, 0, new
                LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                    }
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle
                            extras) {
                    }
                    @Override
                    public void onProviderEnabled(String provider) {
                    }
                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                });
        //Hämta senste longitud och latitud

        Location loc = locationManager.getLastKnownLocation(provider);
        //metod som visar vår nya position
        updateWithNewLocation(loc);

    }
    //metoden updateWithNewLocation för att skriva ut vår position
    private void updateWithNewLocation(Location loc){
        //Hämta TextView från layouten
        TextView myLocationText;
        myLocationText = (TextView)findViewById(R.id.myLocationText);
        //Skapa texten till vår TextView
        String latlongText = "No location found";
        //om vi fått någon position ska detta skrivas
        if(loc != null){
            //hämta latitud och longitud
            double lat = loc.getLatitude();
            double lng = loc.getLongitude();
            latlongText = "Lat: "+ lat + " Long: "+ lng;
        }//här slutar if-satsen
        //sätt texten för TextView i layouten
        myLocationText.setText("Your current position is: \n"+ latlongText);
    }

    @Override
    protected void onPause(){
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.resume();
    }
    //definiera din position
    SpatialReference wgs84 = SpatialReferences.getWgs84();
    Point pos1Loc = new Point(	60.674880,17.141273,wgs84);
    //SimpleMarkerSymbol myMarker = new SimpleMarkerSymbol(new Color.RED, 10, SimpleMarkerSymbol.Style.CIRCLE);
    //create graphics
    //Graphic posGraphic1 = new Graphic(pos1Loc, myMarker);


    public void ActReport (View view){
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);}

}
