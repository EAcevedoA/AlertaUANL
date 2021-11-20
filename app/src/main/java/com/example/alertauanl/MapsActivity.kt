package com.example.alertauanl

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.alertauanl.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.firebase.firestore.FirebaseFirestore

//numeros telefonicos
private const val POLICIA_SANICO = "tel:5555555555"
private const val BOMBEROS_SANICO = "tel:6666666666"
private const val POLICIA_MONTERREY = "tel:7777777777"
private const val BOMBEROS_MONTERREY = "tel:8888888888"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var map: GoogleMap //mapa

    companion object{ //companion object para la peticion de la localizacion
        const val REQUEST_CODE_LOCATION = 0
    }

    private lateinit var binding: ActivityMapsBinding

    var db = FirebaseFirestore.getInstance() //instancia de la base de datos
    var TAG = "MapsActivity"
    var arMarcadores = arrayListOf<marcadores>() //array para los datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) { //cargar el mapa
        map = googleMap
        zoomCU()
        createMarkerCampus()
        createMarkerMedicina()
        createMarkerMederos()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
    }

    fun createMarkerCampus(){ //crear marcadores de ciudad universitaria a partir de la  base de datos
        db.collection("campus").addSnapshotListener{ result, e ->
            if(e!=null){
                Log.w(TAG, "Listen failed", e)
                return@addSnapshotListener
            }
            arMarcadores.clear()
            arMarcadores.addAll(result!!.toObjects(marcadores::class.java))
            for(localizacion in arMarcadores){
                val geoPosition = LatLng(localizacion.latitud, localizacion.longitud)
                map.addMarker(MarkerOptions().position(geoPosition).title(localizacion.titulo)
                    .snippet(localizacion.comentario))
            }
        }
    }

    fun createMarkerMedicina(){ //crear marcadores de campus de medicina a partir de la  base de datos
        db.collection("medicina").addSnapshotListener{ result, e ->
            if(e!=null){
                Log.w(TAG, "Listen failed", e)
                return@addSnapshotListener
            }
            arMarcadores.clear()
            arMarcadores.addAll(result!!.toObjects(marcadores::class.java))
            for(localizacion in arMarcadores){
                val geoPosition = LatLng(localizacion.latitud, localizacion.longitud)
                map.addMarker(MarkerOptions().position(geoPosition).title(localizacion.titulo)
                    .snippet(localizacion.comentario)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
            }
        }
    }

    fun createMarkerMederos(){ //crear marcadores de campus de mederos a partir de la  base de datos
        db.collection("mederos").addSnapshotListener{ result, e ->
            if(e!=null){
                Log.w(TAG, "Listen failed", e)
                return@addSnapshotListener
            }
            arMarcadores.clear()
            arMarcadores.addAll(result!!.toObjects(marcadores::class.java))
            for(localizacion in arMarcadores){
                val geoPosition = LatLng(localizacion.latitud, localizacion.longitud)
                map.addMarker(MarkerOptions().position(geoPosition).title(localizacion.titulo)
                    .snippet(localizacion.comentario)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))
            }
        }
    }

    private fun isLocationPermissionGranted() = //revisar si esta permitida la ubicacion
        ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){ //permitir la ubicacion
        if(!::map.isInitialized) return
        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled = true //ignorar bug de android studio, tambien en los demas
        }else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission(){ //solicitar permiso de ubicacion
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(//Comprueba los permisos pero marca error por un bug de android studio
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true //bug
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }else->{}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled = false //bug
            Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean { //al presionar para mostrar tu ubicacion
        Toast.makeText(this, "Mostrando tu ubicación...", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) { //al presionar tu ubicacion
        Toast.makeText(this, "Estas en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //menu de opciones

        when(item.itemId){
            R.id.campusCU -> zoomCU()
            R.id.campusMedi -> zoomMedi()
            R.id.campusMed -> zoomMed()
            R.id.policiaSN -> permisosPoliciaSN()
            R.id.bomberosSN -> permisosBomberosSN()
            R.id.policiaMedi -> permisosPoliciaMON()
            R.id.bomberosMedi -> permisosBomberosMON()
            R.id.policiaMed -> permisosPoliciaMON()
            R.id.bomberosMed -> permisosBomberosMON()
        }
        return super.onOptionsItemSelected(item)
    }

    fun permisosPoliciaSN() { //verificar permiso para llamadas y llamar a la policia
        val intent1 = Intent(Intent.ACTION_CALL, Uri.parse(POLICIA_SANICO))
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE)

        if (permission == PackageManager.PERMISSION_GRANTED) {
            val miIntent = intent1
            startActivity(miIntent)
        }
        else{
            Toast.makeText(this, "No hay permiso para llamada", Toast.LENGTH_SHORT).
            show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),
            123)
        }
    }

    fun permisosBomberosSN() {//verificar permiso para llamadas y llamar a los bomberos
        val intent1 = Intent(Intent.ACTION_CALL, Uri.parse(BOMBEROS_SANICO))
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE)

        if (permission == PackageManager.PERMISSION_GRANTED) {
            val miIntent = intent1
            startActivity(miIntent)
        }
        else{
            Toast.makeText(this, "No hay permiso para llamada", Toast.LENGTH_SHORT).
            show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),
                123)
        }
    }

    fun permisosPoliciaMON() {//verificar permiso para llamadas y llamar a la policia
        val intent1 = Intent(Intent.ACTION_CALL, Uri.parse(POLICIA_MONTERREY))
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE)

        if (permission == PackageManager.PERMISSION_GRANTED) {
            val miIntent = intent1
            startActivity(miIntent)
        }
        else{
            Toast.makeText(this, "No hay permiso para llamada", Toast.LENGTH_SHORT).
            show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),
                123)
        }
    }

    fun permisosBomberosMON() {//verificar permiso para llamadas y llamar a los bomberos
        val intent1 = Intent(Intent.ACTION_CALL, Uri.parse(BOMBEROS_MONTERREY))
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE)

        if (permission == PackageManager.PERMISSION_GRANTED) {
            val miIntent = intent1
            startActivity(miIntent)
        }
        else{
            Toast.makeText(this, "No hay permiso para llamada", Toast.LENGTH_SHORT).
            show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),
                123)
        }
    }

    fun zoomCU(){//zoom a ciudad universitaria
        val fime = LatLng(25.72492236727511, -100.3134397035457)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(fime, 16f),
            2000,
            null
        )
        Toast.makeText(this, "Mostrando campus de Ciudad Universitaria",
            Toast.LENGTH_SHORT).show()
    }

    fun zoomMedi(){//zoom al campus de medicina
        val medi = LatLng(25.689196, -100.347982)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(medi, 16f),
            2000,
            null
        )
        Toast.makeText(this, "Mostrando campus de Medicina",
            Toast.LENGTH_SHORT).show()
    }

    fun zoomMed(){//zoom al campus de mederos
        val med = LatLng(25.613107, -100.279229)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(med, 16f),
            2000,
            null
        )
        Toast.makeText(this, "Mostrando campus de Mederos",
            Toast.LENGTH_SHORT).show()
    }
}