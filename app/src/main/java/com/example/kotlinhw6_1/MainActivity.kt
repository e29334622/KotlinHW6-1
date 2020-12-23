package com.example.kotlinhw6_1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val REQUEST_PERMISSIONS = 1
    //權限要求結果
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.isEmpty()) return
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                for (result in grantResults)
                    if (result != PackageManager.PERMISSION_GRANTED)
                        finish()  //若使用者拒絕給予權限則關閉APP
                    else{  //連接MapFragment物件
                            val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                            map.getMapAsync(this)
                        }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //檢查使用者是否已授權定位權限
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            //檢查使用者是否已授權定位權限
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS)
        else{  //連接MapFragment物件
            val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            map.getMapAsync(this)
        }
    }
    override fun onMapReady(map: GoogleMap) {
        //檢查使用者是否已授權定位權限
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return
        //顯示目前位置與目前位置的按鈕
        map.isMyLocationEnabled = true
        //建立MarkerOptions物件
        val marker = MarkerOptions()
        marker.position(LatLng(25.033611, 121.565000))
        marker.title("台北101")
        marker.draggable(true)
        map.addMarker( marker)
        marker.position(LatLng(25.047924, 121.517081))
        marker.title("台北車站")
        marker.draggable(true)
        map.addMarker( marker)
        //加入PolylineOptions到googleMap
        val polylineOpt = PolylineOptions()
        polylineOpt.add(LatLng(25.033611, 121.565000))
        polylineOpt.add(LatLng(25.032728, 121.565137))
        polylineOpt.add(LatLng(25.047924, 121.517081))
        polylineOpt.color(Color.BLUE)
        val polyline = map.addPolyline(polylineOpt)
        polyline.width = 10f
        //移動鏡頭
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.034, 121.545), 13f))

    }
}




