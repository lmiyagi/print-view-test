package br.com.leonardomiyagi.printviewtest

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shareImageButton.setOnClickListener {
            shareImage()
        }
    }

    private fun shareImage() {
        val icon = getScreenshot(someCardView)
        val share = Intent(Intent.ACTION_SEND)
        val imagePath = "${externalCacheDir?.path}/someCardView.jpg"

        share.type = "image/jpeg"
        val bytes = ByteArrayOutputStream()

        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val f = File(imagePath)
        try {
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }

        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(imagePath))
        startActivity(Intent.createChooser(share, "Share Image"))
    }

    private fun getScreenshot(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false

        return bitmap
    }
}
