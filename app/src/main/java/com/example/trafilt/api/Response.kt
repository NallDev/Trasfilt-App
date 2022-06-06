package com.example.trafilt.api

import com.google.gson.annotations.SerializedName

data class SellTrashItem(

	@field:SerializedName("sell_id")
	val idSellTrash: Int,

	@field:SerializedName("sell_adress")
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