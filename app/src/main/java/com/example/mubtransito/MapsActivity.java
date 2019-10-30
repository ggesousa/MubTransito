package com.example.mubtransito;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mubtransito.ui.Permissoes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    final int REQUEST_CODE= 1;

    private GoogleMap mMap;
    private String[] permitir = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION

    };
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        //validar permissoes
        Permissoes permissoes = new Permissoes();
        permissoes.validarPermissoes(permitir,this, REQUEST_CODE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);





        //objeto responsavel por gerenciar a localização
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d("Localização:", "onLocationChanged" + location.toString());
                // Adicionando a localização do usuario
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();
                mMap.clear(); //limpa o marcador antes de adicionar um novo
                LatLng localUsuario = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(localUsuario).title("Local:"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localUsuario, 15));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    //endereço ( "R. Genário de Oliveira, 153 - Lagoa Seca, Juazeiro do Norte - CE, 63040-260, Brasil")
                    String stringEndereço = "R. Genário de Oliveira, 153 - Lagoa Seca, Juazeiro do Norte - CE, 63040-260, Brasil";
                    //List<Address> listEndereco =  geocoder.getFromLocation(latitude, longitude, 1); //Utilizada para pegar a lat e lon  e transformar em endereço
                    List<Address> listEndereco = geocoder.getFromLocationName(stringEndereço, 1); // utilizada para pegar o endereço e transformar em lat e lon
                    if (listEndereco != null && listEndereco.size() > 0){
                        Address endereco = listEndereco.get(0);
                        Double lat = location.getLatitude();
                        Double lon = location.getLongitude();

                        mMap.clear(); //limpa o marcador antes de adicionar um novo
                        LatLng local = new LatLng(lat, lon);
                        mMap.addMarker(new MarkerOptions().position(local).title("Local:" + endereco.getAddressLine(0)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 18));
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                        Log.d("local","onLocationChanged: " + endereco.getAddressLine(0));
                        Toast.makeText(getBaseContext(), "Endereço: "  +endereco.getAddressLine(0), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000000, //tempo para atualização as notificações de localização
                    500,
                    locationListener );
        }

        /* */

        //Adicionando a marcação d juazeiro
        LatLng juazeiro = new LatLng(-7.23718, -39.3222);
        mMap.addMarker(new MarkerOptions()
                .position(juazeiro)
                .title("Juazeiro do Norte")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))); //alterar a cor do marcador
        mMap.moveCamera(CameraUpdateFactory.newLatLng(juazeiro));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(juazeiro, 10));
        //Mudo de exibição do mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        //Adicionando evento de click no mapa
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //variavel para clicação do toast
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                //Exibindo as coordenadas por toast curto
                Toast.makeText(getBaseContext(), "OnClick lat: "  +latitude+  "Long: "  +longitude, Toast.LENGTH_LONG).show();


                LatLng juazeiro = new LatLng(-7.23718, -39.3222);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Local")
                        .snippet("Descrição:")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))); //alterar a cor do marcador
            }
        });



        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //variavel para clicação do toast
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;
                //Exibindo as coordenadas por toast longo
                Toast.makeText(getBaseContext(), "OnLongClick lat: "  +latitude+  "Long: "  +longitude, Toast.LENGTH_LONG).show();


                LatLng juazeiro = new LatLng(-7.23718, -39.3222);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Local")
                        .snippet("Descrição:")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            }
        });

        /*  //Desenhando formas no mapa
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(juazeiro);
        circleOptions.radius(500);
        circleOptions.fillColor(Color.RED);
        mMap.addCircle(circleOptions); */

        //Desenhando formas no mapa
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(new LatLng(-7.30551, -39.3025));
        polygonOptions.add(new LatLng(-7.22956, -39.4088));
        polygonOptions.add(new LatLng(-7.25711, -39.1458));
        polygonOptions.strokeColor(Color.GREEN);
        polygonOptions.strokeWidth(10);
        polygonOptions.fillColor(Color.argb(128, 255, 153, 0));
        mMap.addPolygon(polygonOptions);


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0, //tempo para atualização as notificações de localização
                    0,
                    locationListener );
        }

        for (int permissoesresultado : grantResults){
            if(permissoesresultado == PackageManager.PERMISSION_DENIED){
                //Alerta
                alertaValidarPermissao();
            }else if (permissoesresultado == PackageManager.PERMISSION_GRANTED){
                //localização do usuario

            }
        }
    }

    private void alertaValidarPermissao (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as pemissões!");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
