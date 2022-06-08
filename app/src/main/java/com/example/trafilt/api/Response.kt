package com.example.trafilt.api

import com.google.gson.annotations.SerializedName

data class Register(
	@field:SerializedName("user_email")
	val email: String,

	@field:SerializedName("user_name")
	val name: Int,

	@field:SerializedName("user_password")
	val password: String
)

data class Login(

	@field:SerializedName("data")
	val data: List<DataUser>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataUser(
	@field:SerializedName("user_email")
	val userEmail: String,

	@field:SerializedName("user_password")
	val userPassword: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("user_name")
	val userName: String,

	@field:SerializedName("user_firstname")
	val userFirstname: String,

	@field:SerializedName("user_address")
	val userAddress: String,

	@field:SerializedName("user_lastname")
	val userLastname: String,

	@field:SerializedName("user_date")
	val userDate: String,

	@field:SerializedName("user_city")
	val userCity: String,

	@field:SerializedName("user_photo")
	val userPhoto: String,

	@field:SerializedName("user_number")
	val userNumber: String
)

data class SellTrashItem(
	@field:SerializedName("sell_id")
	val idSellTrash: Int,

	@field:SerializedName("sell_address")
	val addressSellTrash: String,

	@field:SerializedName("sell_name")
	val nameSellTrash: String,

	@field:SerializedName("latitude")
	val latSellTrash: String,

	@field:SerializedName("longitude")
	val longSellTrash: String
)

data class PickUpItem(
	@field:SerializedName("id_pickup")
	val idPickUp: Int,

	@field:SerializedName("pickup_name")
	val namePickUp: String,

	@field:SerializedName("pickup_city")
	val cityPickUp: String,

	@field:SerializedName("pickup_nomor")
	val phonePickUp: String
)