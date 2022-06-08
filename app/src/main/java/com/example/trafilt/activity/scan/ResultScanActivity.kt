package com.example.trafilt.activity.scan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.trafilt.R
import com.example.trafilt.databinding.ActivityResultScanBinding
import com.example.trafilt.ml.TrashModel
import com.example.trafilt.utility.lightStatusBar
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class ResultScanActivity : AppCompatActivity() {
    private var _binding: ActivityResultScanBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResultScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lightStatusBar(window)

        val imageResult = intent.getStringExtra("Image")
        val result = BitmapFactory.decodeFile(imageResult)
        binding.ivImageScan.setImageBitmap(result)

        outputGenerator(result)
    }

    private fun outputGenerator(bitmap: Bitmap) {
        val labels =
            application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val model = TrashModel.newInstance(this)

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resized)
        val tbuffer = tensorImage

//            var tbuffer = TensorImage.fromBitmap(resized)
        val byteBuffer = tbuffer.buffer

        Log.e("shape", tbuffer.buffer.toString())

// Creates inputs for reference.
        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        Log.e("shape", outputFeature0.floatArray.toString())
        val max = getMax(outputFeature0.floatArray)
//            Log.e("shape", max.toString())
        Log.e("shape", inputFeature0.buffer.toString())

        binding.tvJenisSampah.text = labels[max]

        getResult()
        model.close()
    }

    private fun getResult() {
        val results = binding.tvJenisSampah.text.toString()
        val regex = Regex("[^\\w]")
        val result = regex.replace(results, "")


        when (result) {
            "Baterai" -> {
                binding.tvKategoriPembuangan.text = getString(R.string.b3)
                binding.tvRecommendation.text = getString(R.string.baterai_recommendation)
            }

            "Biologis" -> {
                binding.tvKategoriPembuangan.text = getString(R.string.organik)
                binding.tvRecommendation.text = getString(R.string.biologis_recommendation)
            }

            "Kaca" -> {
                binding.tvKategoriPembuangan.text = getString(R.string.anorganik)
                binding.tvRecommendation.text = getString(R.string.kaca_recommendation)
            }

            "Kain" -> {
                binding.tvKategoriPembuangan.text = getString(R.string.anorganik)
                binding.tvRecommendation.text = getString(R.string.kain_recommendation)
            }

            "Kardus" -> {
                binding.tvKategoriPembuangan.text = getString(R.string.anorganik)
                binding.tvRecommendation.text = getString(R.string.kardus_recommendation)
            }

            "Kertas" -> {
                binding.tvKategoriPembuangan.text = getString(R.string.anorganik)
                binding.tvRecommendation.text = getString(R.string.kertas_recommendation)
            }

            "Metal" -> {
                binding.tvKategoriPembuangan.text = getString(R.string.anorganik)
                binding.tvRecommendation.text = getString(R.string.metal_recommendation)
            }

            "Plastik" -> {
                binding.tvKategoriPembuangan.text = getString(R.string.anorganik)
                binding.tvRecommendation.text = getString(R.string.plastik_recommendation)
            }

            "Sepatu" -> {
                binding.tvKategoriPembuangan.text = getString(R.string.anorganik)
                binding.tvRecommendation.text = getString(R.string.sepatu_recommendation)
            }
        }
    }

    private fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f

        for (i in 0..8) {
            if (arr[i] > min) {
                min = arr[i]
                ind = i
            }
        }
        return ind
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}