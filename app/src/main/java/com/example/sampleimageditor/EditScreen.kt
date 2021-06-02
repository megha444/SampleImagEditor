package com.example.sampleimageditor

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class EditScreen : AppCompatActivity(), View.OnClickListener {

    private lateinit var iv_edit_image: ImageView
    private lateinit var btn_edit_undo: ImageButton
    private lateinit var btn_edit_rotate: ImageButton
    private lateinit var btn_edit_crop: ImageButton
    private lateinit var btn_edit_save: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_screen)


        iv_edit_image = findViewById(R.id.iv_edit_image)
        btn_edit_undo = findViewById(R.id.btn_edit_undo)
        btn_edit_rotate = findViewById(R.id.btn_edit_rotate)
        btn_edit_crop = findViewById(R.id.btn_edit_crop)
        btn_edit_save = findViewById(R.id.btn_edit_save)


        val byteArrayCamera = intent.getByteArrayExtra("cameraImage")
        if (byteArrayCamera != null) {
            val bmpC = BitmapFactory.decodeByteArray(byteArrayCamera, 0, byteArrayCamera!!.size)
            iv_edit_image.setImageBitmap(bmpC)
        }

        val byteArrayGallery = intent.getByteArrayExtra("galleryImage")
        if (byteArrayGallery != null) {
            val bmpG = BitmapFactory.decodeByteArray(byteArrayGallery, 0, byteArrayGallery!!.size)
            iv_edit_image.setImageBitmap(bmpG)
        }

        btn_edit_crop.setOnClickListener(this)
    }

   override fun onClick(v: View?){
        when(v!!.id){
            R.id.btn_edit_crop->{

            }
        }
    }
}

// private void performCrop(Uri picUri) {
// try {
// Intent cropIntent = new Intent("com.android.camera.action.CROP");
// // indicate image type and Uri
// cropIntent.setDataAndType(picUri, "image/*");
// // set crop properties here
// cropIntent.putExtra("crop", true);
// // indicate aspect of desired crop
// cropIntent.putExtra("aspectX", 1);
// cropIntent.putExtra("aspectY", 1);
// // indicate output X and Y
// cropIntent.putExtra("outputX", 128);
// cropIntent.putExtra("outputY", 128);
// // retrieve data on return
// cropIntent.putExtra("return-data", true);
// // start the activity - we handle returning in onActivityResult
// startActivityForResult(cropIntent, PIC_CROP);
// }
// // respond to users whose devices do not support the crop action
// catch (ActivityNotFoundException anfe) {
// // display an error message
// String errorMessage = "Whoops - your device doesn't support the crop action!";
// Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
// toast.show();
// }
// }
//
// declare:
//
// final int PIC_CROP = 1;
//
// at top.
//
// In onActivity result method, writ following code:
//
// @Override
// protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// super.onActivityResult(requestCode, resultCode, data);
//
// if (requestCode == PIC_CROP) {
// if (data != null) {
// // get the returned data
// Bundle extras = data.getExtras();
// // get the cropped bitmap
// Bitmap selectedBitmap = extras.getParcelable("data");
//
// imgView.setImageBitmap(selectedBitmap);
// }
// }
// }
//
//
// */