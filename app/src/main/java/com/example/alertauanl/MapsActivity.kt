package com.example.alertauanl

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var map: GoogleMap
    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }
    private lateinit var binding: ActivityMapsBinding

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
        createMarker()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
    }

    private fun createMarker(){ //crear marcadores
        val fime = LatLng(25.72492236727511, -100.3134397035457)
        val mFime = MarkerOptions().position(fime).title("Poste de emergencia FIME")
            .snippet("Frente a FIME en intersección con FIC")
        val caseta1 = LatLng(25.725825, -100.316610)
        val mCaseta1 = MarkerOptions().position(caseta1).title("Caseta de prevención y protección")
            .snippet("Entre el gimnasio de FCQ y FCFM")
        val emergencia = LatLng(25.727991, -100.315296)
        val mEmergencia = MarkerOptions().position(emergencia).title("Poste de emergencia")
            .snippet("Dentro de la cancha de futbol")
        val emergencia1 = LatLng(25.728475, -100.313325)
        val mEmergencia1 = MarkerOptions().position(emergencia1)
            .title("Poste de emergencia Gaspar Mas")
            .snippet("Frente al estadio Gaspar Mas")
        val fod = LatLng(25.726998, -100.312563)
        val mFod = MarkerOptions().position(fod)
            .title("Poste de emergencia FOD")
            .snippet("Entre las canchas de FIME y FOD")
        val ffyl = LatLng(25.727063, -100.309782)
        val mFfyl= MarkerOptions().position(ffyl)
            .title("Poste de emergencia FFYL")
            .snippet("Frente a FFYL en intersección con FACPYA")
        val facpya = LatLng(25.727752, -100.307808)
        val mFacpya= MarkerOptions().position(facpya)
            .title("Poste de emergencia FACPYA")
            .snippet("Frente a FACPYA, detrás de Av. Universidad")
        val emergencia2 = LatLng(25.726848, -100.308423)
        val mEmergencia2= MarkerOptions().position(emergencia2)
            .title("Poste de emergencia")
            .snippet("En el cruce para estudiantes frente a FACPYA")
        val rectoria = LatLng(25.724859, -100.311028)
        val mRectoria= MarkerOptions().position(rectoria)
            .title("Poste de emergencia Rectoria")
            .snippet("Entre el departamento de becas y Banorte")
        val farq = LatLng(25.725129, -100.311358)
        val mFarq= MarkerOptions().position(farq)
            .title("Poste de emergencia FARQ")
            .snippet("Detras de FARQ, entre el departamento de Escolar")
        val lUniversitaria = LatLng(25.723640, -100.310848)
        val mLibreria= MarkerOptions().position(lUniversitaria)
            .title("Poste de emergencia Libreria Universitaria")
            .snippet("Frente a la Libreria Univesitaria")
        val eEstacionamiento = LatLng(25.723445, -100.309883)
        val mEmergenciaEstacionamiento= MarkerOptions().position(eEstacionamiento)
            .title("Poste de emergencia Estacionamiento")
            .snippet("Junto al estacionamiento del Estadio Universitario")
        val fcfm = LatLng(25.725933, -100.314806)
        val mFcfm= MarkerOptions().position(fcfm)
            .title("Poste de emergencia FCFM")
            .snippet("Frente a FCFM a la izquierda de FIME")
        val fcb = LatLng(25.723769, -100.316248)
        val mFcb= MarkerOptions().position(fcb)
            .title("Poste de emergencia FCB")
            .snippet("Entre FCB y el estacionamiento de Ciudad Universitaria")
        val pVigilancia = LatLng(25.722995, -100.315289)
        val mVigilancia= MarkerOptions().position(pVigilancia)
            .title("Punto de Vigilancia")
            .snippet("Dentro del estacionamiento del Estadio Universitario")
        val fic = LatLng(25.723803, -100.313402)
        val mFic= MarkerOptions().position(fic)
            .title("Poste de emergencia FIC")
            .snippet("Entre FIC y el Estadio Universitario")
        val eu = LatLng(25.724489, -100.309180)
        val mEu= MarkerOptions().position(eu)
            .title("Poste de emergencia Estadio Universitario")
            .snippet("A la salida del metro Universidad a lado de Rectoria")
        val caseta2 = LatLng(25.723693, -100.309062)
        val mCaseta2= MarkerOptions().position(caseta2)
            .title("Caseta de prevención y protección")
            .snippet("En la entrada de Ciudad Universitaria")
        map.addMarker(mFime)
        map.addMarker(mCaseta1)
        map.addMarker(mEmergencia)
        map.addMarker(mEmergencia1)
        map.addMarker(mFod)
        map.addMarker(mFfyl)
        map.addMarker(mFacpya)
        map.addMarker(mEmergencia2)
        map.addMarker(mRectoria)
        map.addMarker(mFarq)
        map.addMarker(mLibreria)
        map.addMarker(mEmergenciaEstacionamiento)
        map.addMarker(mFcfm)
        map.addMarker(mFcb)
        map.addMarker(mVigilancia)
        map.addMarker(mFic)
        map.addMarker(mEu)
        map.addMarker(mCaseta2)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(fime, 16f),
            2000,
            null
        )
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }else->{}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled = false
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.campusCU -> zoomCU()
            R.id.campusMedi -> zoomMedi()
            R.id.campusMed -> zoomMed()
            R.id.policiaSN -> permisosPoliciaSN()
            R.id.bomberosSN -> permisosBomberosSN()
        }
        return super.onOptionsItemSelected(item)
    }

    fun permisosPoliciaSN() {
        val intent1 = Intent(Intent.ACTION_CALL, Uri.parse("tel:5555555555"))
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

    fun permisosBomberosSN() {
        val intent1 = Intent(Intent.ACTION_CALL, Uri.parse("tel:6666666666"))
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

    fun zoomCU(){
        val fime = LatLng(25.72492236727511, -100.3134397035457)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(fime, 16f),
            2000,
            null
        )
        Toast.makeText(this, "Mostrando campus de Ciudad Universitaria",
            Toast.LENGTH_SHORT).show()
    }

    fun zoomMedi(){
        val medi = LatLng(25.689196, -100.347982)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(medi, 16f),
            2000,
            null
        )
        Toast.makeText(this, "Mostrando campus de Medicina",
            Toast.LENGTH_SHORT).show()
    }

    fun zoomMed(){
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