package com.syroniko.casseteapp.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class LocalCassette(
    @PrimaryKey val cassetteId: String = "",
    @ColumnInfo val trackName: String = "",
    @ColumnInfo val senderId: String = ""
): Parcelable