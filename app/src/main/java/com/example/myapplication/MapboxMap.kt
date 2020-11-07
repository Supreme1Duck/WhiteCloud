package com.example.myapplication

import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.core.constants.Constants.PRECISION_6
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.BitmapUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapboxMap : AppCompatActivity(), OnMapReadyCallback, MapboxMap.OnMapLongClickListener,
    Callback<DirectionsResponse> {
    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap
    private lateinit var currentRoute: DirectionsRoute
    private lateinit var client: MapboxDirections
    private lateinit var origin: Point
    private lateinit var destination: Point
    private lateinit var list_latitude: ArrayList<Double>
    private lateinit var list_longitude: ArrayList<Double>
    private val ROUTE_LAYER_ID = "route-layer-id"
    private val ROUTE_SOURCE_ID = "route-source-id"
    private val ICON_LAYER_ID = "icon-layer-id"
    private val ICON_SOURCE_ID = "icon-source-id"
    private val RED_PIN_ICON_ID = "red-pin-icon-id"
    private lateinit var geocoder: Geocoder
    private var list_features = ArrayList<Feature>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mapbox.getInstance(this, getString(R.string.access_token))

        setContentView(R.layout.map_activity)
        mapView = findViewById(R.id.mapview)
        geocoder = Geocoder(this)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap ->
            this.mapboxMap = mapboxMap
            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                list_latitude = intent.getSerializableExtra("latitude") as ArrayList<Double>
                list_longitude = intent.getSerializableExtra("longitude") as ArrayList<Double>
                origin = Point.fromLngLat(list_latitude[0], list_longitude[0])
                destination = Point.fromLngLat(
                    list_latitude[list_latitude.size - 1],
                    list_longitude[list_longitude.size - 1]
                )
                list_features.add(
                    Feature.fromGeometry(
                        Point.fromLngLat(
                            origin.longitude(),
                            origin.latitude()
                        )
                    )
                )
                list_features.add(
                    Feature.fromGeometry(
                        Point.fromLngLat(
                            destination.longitude(),
                            destination.latitude()
                        )
                    )
                )

                initLayers(it)

                val camera = com.mapbox.mapboxsdk.camera.CameraPosition
                    .Builder()
                    .target(LatLng(destination.latitude(), destination.longitude()))
                    .zoom(18.0)
                    .tilt(13.0)
                    .build()
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera), 1000)

                getRoute(origin)

                initSource(it)
            }
        }
    }

    private fun initSource(@NonNull loadedMapStyle: Style) {
        loadedMapStyle.addSource(GeoJsonSource(ROUTE_SOURCE_ID))
        val iconGeoJsonSource = GeoJsonSource(
            ICON_SOURCE_ID, FeatureCollection.fromFeatures(
                list_features
            )
        )
        loadedMapStyle.addSource(iconGeoJsonSource)
    }

    private fun getRoute(origin: Point) {

        val builder = MapboxDirections.builder()
            .origin(origin)
            .destination(destination)

        for (i in 1 until list_longitude.size - 1) {
            builder.addWaypoint(Point.fromLngLat(list_latitude.get(i), list_longitude.get(i)))
            list_features.add(
                Feature.fromGeometry(
                    Point.fromLngLat(
                        list_latitude.get(i),
                        list_longitude.get(i)
                    )
                )
            )
        }

        client = builder
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(DirectionsCriteria.PROFILE_DRIVING)
            .accessToken(getString(R.string.access_token))
            .build()

        client.enqueueCall(this)
    }

    private fun initLayers(@NonNull loadedMapStyle: Style) {
        var routeLayer = LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID)
        routeLayer.setProperties(
            lineCap(Property.LINE_CAP_ROUND),
            lineJoin(Property.LINE_JOIN_ROUND),
            lineWidth(5f),
            lineColor(Color.parseColor("#009688"))
        )
        loadedMapStyle.addLayer(routeLayer)

        BitmapUtils.getBitmapFromDrawable(
            resources.getDrawable(R.drawable.red_marker)
        )?.let {
            loadedMapStyle.addImage(
                RED_PIN_ICON_ID,
                it
            )
        }

        loadedMapStyle.addLayer(
            SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                iconImage(RED_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(arrayOf(0f, -9f))
            )
        )
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    public override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        client.cancelCall()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
    }


    override fun onResponse(
        call: Call<DirectionsResponse>,
        response: Response<DirectionsResponse>
    ) {
        if (response.body() == null) {
            Toast.makeText(this, "No routes, make sure about Access Token", Toast.LENGTH_SHORT)
                .show()
            return
        } else if (response.body()!!.routes().size < 1) {
            Toast.makeText(this, "No routes found", Toast.LENGTH_SHORT).show()
            return
        }
        currentRoute = response.body()!!.routes().get(0)

        Toast.makeText(
            this, String.format(
                "line",
                currentRoute.distance()
            ), Toast.LENGTH_SHORT
        ).show()

        if (mapboxMap != null) {
            mapboxMap.getStyle {
                val source: GeoJsonSource? = it.getSourceAs(ROUTE_SOURCE_ID)
                if (source != null) {
                    source.setGeoJson(
                        LineString.fromPolyline(
                            currentRoute.geometry()!!,
                            PRECISION_6
                        )
                    )
                }
            }
        }
    }

    override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onMapLongClick(point: LatLng): Boolean {
        TODO("Not yet implemented")
    }
}