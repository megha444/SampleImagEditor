package com.example.sampleimageditor

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.Camera
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.ByteArrayOutputStream
import java.io.IOException

//@Suppress("DEPRECATION")
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var iv_image_preview: ImageView
    private lateinit var ib_add_image_from_camera: ImageButton
    private lateinit var ib_add_image_from_gallery: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_image_preview=findViewById(R.id.iv_image_preview)
        ib_add_image_from_camera=findViewById(R.id.ib_add_image_from_camera)
        ib_add_image_from_gallery=findViewById(R.id.ib_add_image_from_gallery)

        ib_add_image_from_camera.setOnClickListener(this)
        ib_add_image_from_gallery.setOnClickListener(this)

    }


    //onClick starts
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ib_add_image_from_camera->{

                Dexter.withActivity(this).withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                            if(p0!!.areAllPermissionsGranted()) {
                                val captureImageIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                captureImageIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                                captureImageIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                                captureImageIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                                startActivityForResult(captureImageIntent, CAMERA)

                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            p0: MutableList<PermissionRequest>?,
                            p1: PermissionToken?
                        ) {
                            showPermissionRationale()
                        }

                    }).onSameThread().check()

            } //camera button ends
            R.id.ib_add_image_from_gallery->{

                Dexter.withActivity(this).withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                            if(report.areAllPermissionsGranted()){
                                val galleryIntent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                                startActivityForResult(galleryIntent, GALLERY)
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                            token: PermissionToken?
                        ) {
                            showPermissionRationale()
                        }
                    }).onSameThread().check()

            }//gallery ends
        } //when ends
    }
    //onClick ends

    private fun showPermissionRationale() {
        AlertDialog.Builder(this)
            .setMessage("Please grant Storage and Camera permissions for the application to edit those photos")
            .setPositiveButton("Go to Settings") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, GALLERY)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            if(requestCode== CAMERA){
                val imageCaptured: Bitmap= data!!.extras!!.get("data") as Bitmap
                iv_image_preview.setImageBitmap(imageCaptured)
                val cameraintent=Intent(this, EditScreen::class.java)

                val stream = ByteArrayOutputStream()
                imageCaptured.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()

                cameraintent.putExtra("cameraImage", byteArray)
                startActivity(cameraintent)
            }else if(requestCode== GALLERY){
                if(data!=null){
                    val contentUri=data.data
                    try{
                        @Suppress("DEPRECATION")
                        val selectedImageBitmap= MediaStore.Images.Media.getBitmap(this.contentResolver, contentUri)
                        iv_image_preview.setImageBitmap(selectedImageBitmap)

                        val galleryintent=Intent(this, EditScreen::class.java)
                        val stream = ByteArrayOutputStream()
                        selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val byteArray = stream.toByteArray()
                        galleryintent.putExtra("cameraImage", byteArray)
                        startActivity(galleryintent)

                    }catch (e: IOException){e.printStackTrace()
                        Toast.makeText(this, "Gallery Intent Failed", Toast.LENGTH_SHORT).show()}
                }
            }
        }
    }


    companion object{
        private const val CAMERA=1
        private const val GALLERY=2
    }

}