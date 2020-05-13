package com.yudahendriawan.ProjectTugasAkhir;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
//import com.mapbox.mapboxandroiddemo.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.yudahendriawan.ProjectTugasAkhir.api.ApiClient;
import com.yudahendriawan.ProjectTugasAkhir.api.ApiInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

/**
 * Use Mapbox Java Services to request directions from the Mapbox Directions API and show the
 * route with a LineLayer.
 */
public class MainActivity extends AppCompatActivity {

    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private MapView mapView;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;
    private Point origin;
    private Point destination;

    ProgressDialog progressDialog;

    private List<Point> pointList;
    //private List<Node> nodes = new ArrayList();
    private String routeURL;

    String routePointList = "";
    String origin_ = "";
    String destination_ = "";

    Button proses, show;
    EditText inputSource, inputDest;

    int vertices = 31;

    Graph graph; /*= new Graph(vertices);*/
    Graph g = new Graph();
    DepthFirstSearch dfs; /*= new DepthFirstSearch();*/
    boolean repeat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main);

        proses = findViewById(R.id.proses);
        show = findViewById(R.id.show);
//        inputDest = findViewById(R.id.edt_dest);
//        inputSource = findViewById(R.id.edt_source);

        AutoCompleteTextView acSource = findViewById(R.id.autocomplete_source);
        AutoCompleteTextView acDest = findViewById(R.id.autocomplete_dest);
        String[] acWisata = getResources().getStringArray(R.array.places_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, acWisata);
        acSource.setAdapter(adapter);
        acDest.setAdapter(adapter);


        graph = new Graph(vertices, this);
        dfs = new DepthFirstSearch();
        Places place = new Places();



        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graph.addEdgeDB();
                //  graph.getPlacesData();
                Toast.makeText(v.getContext(), "Get Data from DB", Toast.LENGTH_SHORT).show();
//                String[] acWisata = new String[place.getType().length()];
//                for(int i = 0; i<place.getType().length();i++){
//                    acWisata[i] = place.getType();
//                }

            }
        });


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graph.adjacencyList != null) {
//                    int getSource = Integer.parseInt(inputDest.getText().toString());
//                    int getDest = Integer.parseInt(inputDest.getText().toString());

                    dfs.printAllPaths(graph, 1, 5);
                    weightedProduct();
                    showMap(savedInstanceState);
                } else {
                    Toast.makeText(MainActivity.this, "Input Source n Dest", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * Add the route and marker sources to the map
     */
    private void initSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));

        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[]{
                Feature.fromGeometry(Point.fromLngLat(origin.longitude(), origin.latitude())),
                Feature.fromGeometry(Point.fromLngLat(destination.longitude(), destination.latitude()))}));
        loadedMapStyle.addSource(iconGeoJsonSource);
    }

    /**
     * Add the route and marker icon layers to the map
     */
    private void initLayers(@NonNull Style loadedMapStyle) {
        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);

        // Add the LineLayer to the map. This layer will display the directions route.
        routeLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.parseColor("#009688"))
        );
        loadedMapStyle.addLayer(routeLayer);

        // Add the red marker icon image to the map
//        loadedMapStyle.addImage(RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
//                getResources().getDrawable(R.drawable.red_marker1)));

        // Add the red marker icon SymbolLayer to the map
        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                iconImage(RED_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(new Float[]{0f, -9f})));
    }

    /**
     * Make a request to the Mapbox Directions API. Once successful, pass the route to the
     * route layer.
     *
     * @param mapboxMap   the Mapbox map object that the route will be drawn on
     * @param origin      the starting point of the route
     * @param destination the desired finish point of the route
     */
    private void getRoute(final MapboxMap mapboxMap, Point origin, List<Point> wayPoints, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .waypoints(wayPoints) //to add point between origin and destination
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(getString(R.string.access_token))
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @SuppressLint("StringFormatInvalid")
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                // You can get the generic HTTP info about the response
                Timber.d("Response code: " + response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }

                // Get the directions route
                currentRoute = response.body().routes().get(0);

                // Make a toast which displays the route's distance
                Toast.makeText(MainActivity.this, String.format(
                        getString(R.string.directions_activity_toast_message),
                        currentRoute.distance()), Toast.LENGTH_SHORT).show();

                if (mapboxMap != null) {
                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

                            // Retrieve and update the source designated for showing the directions route
                            GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);

                            // Create a LineString with the directions route's geometry and
                            // reset the GeoJSON source for the route LineLayer source
                            if (source != null) {
                                source.setGeoJson(LineString.fromPolyline(currentRoute.geometry(), PRECISION_6));
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(MainActivity.this, "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //  mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the Directions API request
        if (client != null) {
            client.cancelCall();
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    public void weightedProduct() {
        if (dfs.getTemp().size() != 0) {

            //akan digunakan utk menampung criteria
            Double[][] data = new Double[dfs.getTemp().size()][3];
            Log.d("tempSize", String.valueOf(dfs.getTemp().size()));
//        for(int i = 0 ; i <data.length; i ++){
//
//        }

//        if(dfs.getTemp().size() != 0) {
            // System.out.println("\n{Jarak, Jumlah Wisata, Kepadatan}");
            for (int i = 0; i < dfs.getTemp().size(); i++) {
                //   System.out.print("[C" + (i + 1) + "] {");
                for (int j = 0; j < 3; j++) {
                    data[i][j] = dfs.getTemp().get(i).get(j);
//                System.out.print(+data[i][j]);
//                if (j == 2) {
//                    System.out.print("");
//                } else {
//                    System.out.print(",");
//                }
                }
                // System.out.println("}");
            }

            //menyimpan data dalam bentuk arrayList
            ArrayList<Criteria> listCriteria = new ArrayList<>();
            for (Double[] dataKu : data) {
                Criteria criteria = new Criteria();
                criteria.setJarak(dataKu[0]);
                criteria.setWisata(dataKu[1]);
                criteria.setKepadatan(dataKu[2]);

                listCriteria.add(criteria);
            }

            //inisisasi bobot pada setiap kriteria
            int bobotJarak = 5;
            int bobotWisata = 3;
            int bobotKepadatan = 2;
            double totalBobot = bobotJarak + bobotKepadatan + bobotWisata;

            //Penormalan Bobot
            double bobotNormalJarak = bobotJarak / totalBobot;
            double bobotNormalWisata = bobotWisata / totalBobot;
            double bobotNormalKepadatan = bobotKepadatan / totalBobot;

            //inisiasi array utk menyimpan vektor S
            //memangkatkan setiap kriteria dengan bobot
            //dengan pangkat bobot + utk kriteria benefit (wisata) dan - utk kriteria cost(kepadatan,jarak).
            double[] vektorS = new double[listCriteria.size()];
            for (int i = 0; i < listCriteria.size(); i++) {
                vektorS[i] = Math.pow(listCriteria.get(i).getJarak(), -bobotNormalJarak)
                        * Math.pow(listCriteria.get(i).getWisata(), bobotNormalWisata)
                        * Math.pow(listCriteria.get(i).getKepadatan(), -bobotNormalKepadatan);

            }

            //menjumlahkan vektor S dan print vektor S
            double sigmaVektorS = 0;
            for (int k = 0; k < vektorS.length; k++) {
                sigmaVektorS = sigmaVektorS + vektorS[k];
            }

            //hitung vektor V pada setiap alternatif
            Double[] vektorV = new Double[listCriteria.size()];
            for (int i = 0; i < listCriteria.size(); i++) {
                vektorV[i] = vektorS[i] / sigmaVektorS;
            }

            //mengcopy vektor V utk membandingkan
            Double[] vektorVSortDesc = Arrays.copyOf(vektorV, vektorV.length);

            System.out.println("\nSorting Vektor V by Descending Sort");
            Arrays.sort(vektorVSortDesc, Collections.reverseOrder());

            //mencari peringkat pertama
            int rank1 = 0;
            // int rank2 = 0;
            // int rank3 = 0;
            for (int i = 0; i < vektorVSortDesc.length; i++) {
                if (vektorVSortDesc[0].equals(vektorV[i]))
                    rank1 = i;
            }

            Double[] pathResult = new Double[dfs.getTemp().get(rank1).size()];

            //copy element of arraylist in index rank1 to array 1 dim.
            for (int i = 0; i < pathResult.length; i++) {
                pathResult[i] = dfs.getTemp().get(rank1).get(i);
            }

            //to get just path, not include criteria
            Double[] pathResultFix = Arrays.copyOfRange(pathResult, 3, pathResult.length);

            //change double to int
            int[] pathResultFixInt = new int[pathResultFix.length];
            for (int i = 0; i < pathResultFixInt.length; i++) {
                pathResultFixInt[i] = pathResultFix[i].intValue();
            }

            //log cat in debug list point
            String storePointList = "[";

            for (int i = 0; i < pathResultFixInt.length; i++) {
                storePointList = storePointList + pathResultFixInt[i];
                if (i != pathResultFixInt.length - 1) {
                    storePointList = storePointList + ",";
                } else {
                    storePointList = storePointList + "]";
                }
            }

            Log.d("PointList", storePointList);


            for (int i = 0; i < pathResultFixInt.length; i++) {
                for (int j = 0; j < graph.getLatLong().length; j++) {
                    if (i > 0 && i < pathResultFixInt.length - 1) {
                        if (String.valueOf(pathResultFixInt[i]).equals(graph.getLatLong()[j])) {
                            routePointList = routePointList + graph.getLatLong()[j + 1];
                            if (i != pathResultFixInt.length - 2) {
                                routePointList = routePointList + "/";
                            }
                        }
                    }
                    if (i == 0) {
                        if (String.valueOf(pathResultFixInt[i]).equals(graph.getLatLong()[j]))
                            origin_ = origin_ + graph.getLatLong()[j + 1];
                    }
                    if (i == pathResultFixInt.length - 1) {
                        if (String.valueOf(pathResultFixInt[i]).equals(graph.getLatLong()[j]))
                            destination_ = destination_ + graph.getLatLong()[j + 1];
                    }

                }
            }


            // for(String s : Arrays.asList(origin_.split(","))){
            Double latitudeOrigin = Double.valueOf(origin_.split(",")[0]);
            Double longitudeOrigin = Double.valueOf(origin_.split(",")[1]);
            origin = Point.fromLngLat(longitudeOrigin, latitudeOrigin);
            //  }

            //  for(String s : Arrays.asList(destination_.split(","))){
            Double latitudeDest = Double.valueOf(destination_.split(",")[0]);
            Double longitudeDest = Double.valueOf(destination_.split(",")[1]);
            destination = Point.fromLngLat(longitudeDest, latitudeDest);
            // }


            // Set the origin location to the Alhambra landmark in Granada, Spain.
            // origin = Point.fromLngLat(112.803655, -7.294455);

            // Set the destination location to the Plaza del Triunfo in Granada, Spain.
            //  destination = Point.fromLngLat(112.805451, -7.276591);

            //klennteng sangar agung, pantai ria kenjeran
            routeURL = "-7.247226, 112.802257/-7.249400, 112.800501";

            pointList = new ArrayList<>();
            for (String s : Arrays.asList(routePointList.split("/"))) {
                Double latitude = Double.valueOf(s.split(",")[0]);
                Double longitude = Double.valueOf(s.split(",")[1]);
                pointList.add(Point.fromLngLat(longitude, latitude));
            }


        } else {
//            Toast.makeText(MainActivity.this, "Check Internet Conn..", Toast.LENGTH_SHORT).show();

        }
    }

    public void showMap(Bundle savedInstanceState) {
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onStart();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        if (dfs.getTemp().size() != 0) {
                            initSource(style);

                            initLayers(style);

                            // Get the directions route from the Mapbox Directions API
                            getRoute(mapboxMap, origin, pointList, destination);

                        }

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.295, 112.802))
                                .title("Taman Harmoni"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.290, 112.796))
                                .title("Sakinah Supermarket"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.272280, 112.759526))
                                .title("Museum dan Pusat Kajian Etnografi UNAIR"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.250636, 112.753804))
                                .title("Museum WR. Soeptratman"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.311799, 112.782312))
                                .title("Museum Teknoform Undika"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.265257, 112.758042))
                                .title("Museum Pendidikan Kedokteran UNAIR Sby"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.247226, 112.802257))
                                .title("Klenteng Sanggar Agung"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.254267, 112.801853))
                                .title("Atlantis Land"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.249400, 112.800501))
                                .title("Pantai Ria Kenjeran"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.294329, 112.761751))
                                .title("Taman Flora"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.297431, 112.760045))
                                .title("Pasar Bunga Bratang"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.312360, 112.788902))
                                .title("Kebun Bibit Wonorejo"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.251590, 112.754599))
                                .title("Taman Mundu"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.318214, 112.784242))
                                .title("Taman Kunang-kunang"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.275934, 112.802721))
                                .title("Food Festival Pakuwon City"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(-7.276591, 112.805451))
                                .title("East Cost Surabaya"));
                    }
                });
            }
        });
    }
}

