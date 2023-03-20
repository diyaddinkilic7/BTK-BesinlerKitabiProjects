package com.diyaddinkilic.besinlerkitabi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Besin(
    @SerializedName("isim")
    @ColumnInfo(name = "isim")
    val besinIsim: String?,
    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")
    val besinKalori: String?,
    @ColumnInfo(name = "karbohidrat")
    @SerializedName("karbonhidrat")
    val besinKarbohidrat: String?,
    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val besinProtein: String?,
    @SerializedName("yag")
    @ColumnInfo(name = "yag")
    val besinYag: String?,
    @SerializedName("gorsel")
    @ColumnInfo(name = "gorsel")
    val besinGorsel: String?,

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
)
