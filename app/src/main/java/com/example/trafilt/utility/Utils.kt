package com.example.trafilt.utility

import android.content.Context
import android.os.Environment
import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.DiffUtil
import com.example.trafilt.api.PickUpItem
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun lightStatusBar(window: Window, isLight : Boolean = true){
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightStatusBars = isLight
}

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

class MyDiffUtil(
    private val oldList: List<PickUpItem>,
    private val newList: List<PickUpItem>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idPickUp == newList[newItemPosition].idPickUp
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].idPickUp != newList[newItemPosition].idPickUp -> {
                false
            }
            oldList[oldItemPosition].namePickUp != newList[newItemPosition].namePickUp -> {
                false
            }
            oldList[oldItemPosition].cityPickUp != newList[newItemPosition].cityPickUp -> {
                false
            }
            oldList[oldItemPosition].phonePickUp != newList[newItemPosition].phonePickUp -> {
                false
            }
            else -> true
        }
    }
}