package com.example.alertauanl

import java.io.Serializable

class marcadores(var latitud:Double, var longitud:Double, var titulo:String, var comentario:String):
    Serializable {
    constructor():this(0.0,0.0,"" ,"")
}