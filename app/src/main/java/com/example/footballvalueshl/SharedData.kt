package com.example.footballvalueshl

class SharedData {

    companion object{
        var players :MutableList<PlayingObject?> = ArrayList()
        var clubs :MutableList<PlayingObject?> = ArrayList()
        var countries :MutableList<PlayingObject?> = ArrayList()
        val instance = SharedData()

    }



}