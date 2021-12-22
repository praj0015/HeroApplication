package com.example.heroapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "HeroTable")
data class HeroModel(

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String? = null,

    @SerializedName("localized_name")
    @ColumnInfo(name = "localized_name")
    var localized_name : String? = null,

    @SerializedName("attack_type")
    @ColumnInfo(name = "attack_type")
    var attach_type : String? = null,

    @SerializedName("base_health")
    @ColumnInfo(name = "base_health")
    var base_health: String? = null,

    @SerializedName("primary_attr")
    @ColumnInfo(name="primary_attr")
    var primary_attr : String? = null,

    @SerializedName("img")
    @ColumnInfo(name="img")
    var img : String? = null
)
