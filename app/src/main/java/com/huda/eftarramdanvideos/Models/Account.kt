package com.huda.eftarramdanvideos.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Account(

    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String? = "name",
    @SerializedName("email") val email: String? = "email",
    @SerializedName("score") val score: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeInt(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Account> {
        override fun createFromParcel(parcel: Parcel): Account {
            return Account(parcel)
        }

        override fun newArray(size: Int): Array<Account?> {
            return arrayOfNulls(size)
        }
    }
}