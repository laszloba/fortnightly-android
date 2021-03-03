package hu.laszloba.fortnightly.data.database

import androidx.room.PrimaryKey

abstract class BaseDataModel {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
