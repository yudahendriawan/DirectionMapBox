package com.yudahendriawan.ProjectTugasAkhir;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
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
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.yudahendriawan.ProjectTugasAkhir.model.Criteria;
import com.yudahendriawan.ProjectTugasAkhir.model.Places;
import com.yudahendriawan.ProjectTugasAkhir.resultmap.ResultMapActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
 * Use Mapbox Java Services to request directions from the Mapbox Directions API and setPriority the
 * route with a LineLayer.
 */
public class MainActivity extends AppCompatActivity implements /*OnMapReadyCallback,*/
        PermissionsListener {

    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;
    private Point origin;
    private Point destination;
    private PermissionsManager permissionsManager;

    //ProgressDialog progressDialog;
    Spinner spinner1, spinner2, spinner3;

    private List<Point> pointList;
    //private List<Node> nodes = new ArrayList();
    private String routeURL;

    String routePointList = "";
    String origin_ = "";
    String destination_ = "";
    //public static Button getDataFromDB;
    public static Button setPriority;
    EditText inputSource, inputDest;
    public static FloatingActionButton getRoutes;
    FloatingActionButton switchBtn;
    FloatingActionButton getCurrentLocation;
    FloatingActionButton getListWisata;
    Button clearBtn;

    int vertices = 31;
    int getSource = 1000;
    int getDest = 1000;

    ProgressBar progressBar;

    Graph graph;
    Graph g = new Graph();
    DepthFirstSearch dfs; /*= new DepthFirstSearch();*/
    boolean repeat = false;

    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    EditText inputBobotJarak, inputBobotWisata, inputBobotKepadatan;

    int spinnerSelected1 = 0;
    int spinnerSelected2 = 0;
    int spinnerSelected3 = 0;


    int bobotJarak;
    int bobotWisata;
    int bobotKepadatan;

    AutoCompleteTextView acSource;
    AutoCompleteTextView acDest;

    public static ProgressBar cobaProgressBar;

    TextView randomPrior;

    int[] pathResultFixInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //binding
        // getDataFromDB = findViewById(R.id.proses);
        setPriority = findViewById(R.id.show);
        getRoutes = findViewById(R.id.show_arrow);
        clearBtn = findViewById(R.id.clearBtn);
        switchBtn = findViewById(R.id.switchSourceDest);
        getCurrentLocation = findViewById(R.id.current_location);
        mapView = findViewById(R.id.mapView);
        progressBar = findViewById(R.id.progress_loader);
        //getListWisata = findViewById(R.id);
        getListWisata = findViewById(R.id.fab_listwisata);

        getRoutes.setVisibility(View.INVISIBLE);


        getRoutes.setVisibility(View.INVISIBLE);
        setPriority.setVisibility(View.INVISIBLE);

        acSource = findViewById(R.id.autocomplete_source);
        acDest = findViewById(R.id.autocomplete_dest);
        String[] acWisata = getResources().getStringArray(R.array.places_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, acWisata);
        acSource.setAdapter(adapter);
        acDest.setAdapter(adapter);

        graph = new Graph(vertices, this);
        dfs = new DepthFirstSearch();

        showMapStandard();

        setPriority.setOnClickListener(v -> dialogForm());
        getListWisata.setVisibility(View.GONE);

        graph.addEdgeDB();
        Toast.makeText(this, "Get Data from DB", Toast.LENGTH_SHORT).show();

//        getDataFromDB.setOnClickListener(v -> {
//            graph.addEdgeDB();
//            Toast.makeText(v.getContext(), "Get Data from DB", Toast.LENGTH_SHORT).show();
//            setPriority.setVisibility(View.VISIBLE);
//            getRoutes.setVisibility(View.VISIBLE);
//        });


        getRoutes.setOnClickListener(v -> {
            if (graph.adjacencyList != null && bobotJarak != 0 && bobotWisata != 0 && bobotKepadatan != 0) {

                //mengambil source and destination dari inputan
                for (int i = 0; i < graph.getWisataSourceDest().length; i++) {
                    if (acSource.getText().toString().equals(graph.getWisataSourceDest()[i])) {
                        getSource = Integer.parseInt(graph.getWisataSourceDest()[i - 1]);
                        Log.d("getSource", String.valueOf(getSource));
                    }
                    if (acDest.getText().toString().equals(graph.getWisataSourceDest()[i])) {
                        getDest = Integer.parseInt((graph.getWisataSourceDest()[i - 1]));
                        Log.d("getDest", String.valueOf(getDest));
                    }
                }

                //untuk proses kedua
                if (getSource != 1000 && getDest != 1000) {
                    getRoutes.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    dfs.getTemp().clear();
                    dfs.printAllPaths(graph, getSource, getDest);
                    weightedProduct();
                    showMap(savedInstanceState);
                } else {
                    Toast.makeText(MainActivity.this, "Fill Source & Destination", Toast.LENGTH_SHORT);
                }
            } else {
                Toast.makeText(MainActivity.this, "Priority hasn't been set", Toast.LENGTH_LONG).show();
            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!acSource.getText().toString().isEmpty() && !acDest.getText().toString().isEmpty()) {
                    String source = acSource.getText().toString();
                    String destination = acDest.getText().toString();
                    acDest.setText(source);
                    acSource.setText(destination);
                } else {
                    Toast.makeText(v.getContext(), "Fill Source & Destination", Toast.LENGTH_SHORT).show();
                }

            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acDest.setText(null);
                acSource.setText(null);
                Toast.makeText(v.getContext(), "Clear success", Toast.LENGTH_SHORT).show();
                acSource.setFocusable(true);
                showMapStandard();
                getListWisata.setVisibility(View.GONE);
            }
        });

        getListWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultMapActivity.class);
                intent.putExtra("pathResultFix", pathResultFixInt);
                startActivity(intent);
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

                getRoutes.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                getListWisata.setVisibility(View.VISIBLE);

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
                getRoutes.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        showMapStandard();
        getListWisata.setVisibility(View.GONE);

        boolean click = true;
        while (click) {
            if (bobotJarak != 0) {
                getRoutes.performClick();
            }
            click = false;
        }

        // mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showMapStandard();

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

            origin_ = "";
            destination_ = "";
            routePointList = "";

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
                }

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
            pathResultFixInt = new int[pathResultFix.length];
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

            Double latitudeOrigin;
            Double longitudeOrigin;
            // for(String s : Arrays.asList(origin_.split(","))){
            latitudeOrigin = Double.valueOf(origin_.split(",")[0]);
            longitudeOrigin = Double.valueOf(origin_.split(",")[1]);
            origin = Point.fromLngLat(longitudeOrigin, latitudeOrigin);
            //  }

            Double latitudeDest;
            Double longitudeDest;
            //  for(String s : Arrays.asList(destination_.split(","))){
            latitudeDest = Double.valueOf(destination_.split(",")[0]);
            longitudeDest = Double.valueOf(destination_.split(",")[1]);
            destination = Point.fromLngLat(longitudeDest, latitudeDest);
            // }


            //klennteng sangar agung, pantai ria kenjeran
            routeURL = "-7.247226, 112.802257/-7.249400, 112.800501";

            pointList = new ArrayList<>();
            Double latitude;
            Double longitude;
            for (String s : Arrays.asList(routePointList.split("/"))) {
                latitude = Double.valueOf(s.split(",")[0]);
                longitude = Double.valueOf(s.split(",")[1]);
                pointList.add(Point.fromLngLat(longitude, latitude));
            }


        } else {
//            Toast.makeText(MainActivity.this, "Check Internet Conn..", Toast.LENGTH_SHORT).setPriority();

        }
    }

    public void showMap(Bundle savedInstanceState) {

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
                        pointList.clear();
                        Log.d("pointList clear", pointList.toString());

                        //to hide compass

                        getCurrentLocation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // enableLocationComponent(style);
                            }
                        });

                        mapboxMap.getUiSettings().setCompassEnabled(false);

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

    public void kosong() {
        inputBobotKepadatan.setText("");
        inputBobotWisata.setText("");
        inputBobotJarak.setText("");
    }

    public void dialogForm() {

        dialog = new AlertDialog.Builder(MainActivity.this);
        inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_bobot, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        spinner1 = dialogView.findViewById(R.id.spinnerPriority1);
        spinner2 = dialogView.findViewById(R.id.spinnerPriority2);
        spinner3 = dialogView.findViewById(R.id.spinnerPriority3);
        randomPrior = dialogView.findViewById(R.id.randomPriority);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(dialogView.getContext(), R.array.bobot1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(dialogView.getContext(), R.array.bobot2, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(dialogView.getContext(), R.array.bobot3, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        spinner1.setSelection(spinnerSelected1);
        spinner2.setSelection(spinnerSelected2);
        spinner3.setSelection(spinnerSelected3);

        randomPrior.setOnClickListener(v -> {
            boolean check = true;

            while (check) {
                int randomSpinner1 = (int) (Math.random() * 3 + 1);
                int randomSpinner2 = (int) (Math.random() * 3 + 1);
                int randomSpinnner3 = (int) (Math.random() * 3 + 1);
                Log.d("1-2-3 = ", randomSpinner1 + "," + randomSpinner2 + ", " + randomSpinnner3);
                if (randomSpinner1 != randomSpinner2 && randomSpinner1 != randomSpinnner3 && randomSpinner2 != randomSpinnner3) {
                    spinner1.setSelection(randomSpinner1);
                    spinner2.setSelection(randomSpinner2);
                    spinner3.setSelection(randomSpinnner3);
                    check = false;
                }
            }
        });



        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String text = parent.getItemAtPosition(position).toString();
                int item = parent.getSelectedItemPosition();
                if (item == 1) {
                    bobotJarak = 50;
                    spinnerSelected1 = 1;
                } else if (item == 2) {
                    bobotWisata = 50;
                    spinnerSelected1 = 2;
                } else if (item == 3) {
                    bobotKepadatan = 50;
                    spinnerSelected1 = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int item = parent.getSelectedItemPosition();
                if (item == 1) {
                    bobotJarak = 10;
                    spinnerSelected2 = 1;
                } else if (item == 2) {
                    bobotWisata = 10;
                    spinnerSelected2 = 2;
                } else if (item == 3) {
                    bobotKepadatan = 10;
                    spinnerSelected2 = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int item = parent.getSelectedItemPosition();
                if (item == 1) {
                    bobotJarak = 1;
                    spinnerSelected3 = 1;
                } else if (item == 2) {
                    bobotWisata = 1;
                    spinnerSelected3 = 2;
                } else if (item == 3) {
                    bobotKepadatan = 1;
                    spinnerSelected3 = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.setPositiveButton("OK", (dialog, which) -> {
            if (spinner1.getSelectedItem().toString().equals(spinner2.getSelectedItem().toString())) {
                Toast.makeText(dialogView.getContext(), "Priority tidak boleh sama", Toast.LENGTH_SHORT).show();
                dialogForm();
            } else if (spinner2.getSelectedItem().toString().equals(spinner3.getSelectedItem().toString())) {
                Toast.makeText(dialogView.getContext(), "Priority tidak boleh sama", Toast.LENGTH_SHORT).show();
                dialogForm();
            } else if (spinner1.getSelectedItem().toString().equals(spinner3.getSelectedItem().toString())) {
                Toast.makeText(dialogView.getContext(), "Priority tidak boleh sama", Toast.LENGTH_SHORT).show();
                dialogForm();
            } else if (spinner1.getSelectedItem().equals("Choose Priority-1") || spinner2.getSelectedItem().equals("Choose Priority-2")
                    || spinner3.getSelectedItem().equals("Choose Priority-3")) {
                Toast.makeText(dialogView.getContext(), "Priority harus diisi", Toast.LENGTH_SHORT).show();
                dialogForm();
            } else {
                String bobot = "Jarak : " + bobotJarak + ", Wisata : " + bobotWisata + ", Kepadatan : " + bobotKepadatan;
                getRoutes.setVisibility(View.VISIBLE);
                Log.d("bobot", bobot);


                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        dialog.show();


    }

    void showMapStandard() {
        //mapView = findViewById(R.id.mapView);
        mapView.onStart();
        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                getCurrentLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        enableLocationComponent(style);
                    }
                });


                mapboxMap.getUiSettings().setCompassEnabled(false);
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
        }));
    }

    public void getRoutes() {
        if (bobotJarak != 0 && bobotWisata != 0 && bobotKepadatan != 0) {

            //mengambil source and destination dari inputan
            for (int i = 0; i < graph.getWisataSourceDest().length; i++) {
                if (acSource.getText().toString().equals(graph.getWisataSourceDest()[i])) {
                    getSource = Integer.parseInt(graph.getWisataSourceDest()[i - 1]);
                    Log.d("getSource", String.valueOf(getSource));
                }
                if (acDest.getText().toString().equals(graph.getWisataSourceDest()[i])) {
                    getDest = Integer.parseInt((graph.getWisataSourceDest()[i - 1]));
                    Log.d("getDest", String.valueOf(getDest));
                }
            }

            //untuk proses kedua
            if (getSource != 1000 && getDest != 1000) {
                dfs.getTemp().clear();
                dfs.printAllPaths(graph, getSource, getDest);
                weightedProduct();
                //   showMap(savedInstanceState);
            } else {
                Toast.makeText(MainActivity.this, "Fill Source & Destination", Toast.LENGTH_SHORT);
            }
        } else {
            Toast.makeText(MainActivity.this, "Input Bobot", Toast.LENGTH_SHORT);
            Toast.makeText(MainActivity.this, "Input Source n Dest", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        // Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            //  Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

// Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //String myString = savedInstanceState.getString("MyString");

        String getSourceSaved = savedInstanceState.getString("source");
        String getDestinationSaved = savedInstanceState.getString("destination");
        int getBobotJarakSaved = savedInstanceState.getInt("bobotJarak");
        int getBobotWisataSaved = savedInstanceState.getInt("bobotWisata");
        int getBobotKepadatanSaved = savedInstanceState.getInt("bobotKepadatan");


        bobotJarak = getBobotJarakSaved;
        bobotKepadatan = getBobotKepadatanSaved;
        bobotWisata = getBobotWisataSaved;


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

        String sourceSaved = acSource.getText().toString();
        String destinationSaved = acDest.getText().toString();

        outState.putString("source", sourceSaved);
        outState.putString("destination", destinationSaved);
        outState.putInt("bobotJarak", bobotJarak);
        outState.putInt("bobotKepadatan", bobotKepadatan);
        outState.putInt("bobotWisata", bobotWisata);

    }

//    @Override
//    public void onMapReady(@NonNull MapboxMap mapboxMap) {
//        MainActivity.this.mapboxMap = mapboxMap;
//
//        mapboxMap.setStyle(Style.MAPBOX_STREETS,
//                new Style.OnStyleLoaded() {
//                    @Override
//                    public void onStyleLoaded(@NonNull Style style) {
//                        enableLocationComponent(style);
//                    }
//                });
//    }
}

