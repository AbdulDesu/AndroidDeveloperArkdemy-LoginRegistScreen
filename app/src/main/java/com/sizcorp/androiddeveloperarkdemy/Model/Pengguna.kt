package com.sizcorp.androiddeveloperarkdemy.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Pengguna (val uid: String, val email: String) : Parcelable{
    constructor() : this("", "")
}