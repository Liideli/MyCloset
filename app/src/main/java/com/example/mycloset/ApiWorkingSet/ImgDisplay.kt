package com.example.mycloset.ApiWorkingSet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

//used for make the request for showing thee image
class ImgDisplay {
    companion object {
        @Composable
        fun DisplayPicture(url: String){
            var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }
            LaunchedEffect(Dispatchers.IO){
                //request
                bitmap= showImg(url)?.asImageBitmap()
            }
            //style update
            bitmap?.let { bitmap ->
                Image(modifier = Modifier.size(300.dp)
                    .clip(RoundedCornerShape(16.dp)), bitmap = bitmap, contentDescription = "downloaded picture" )
            }
        }

        //request
        private suspend fun showImg(url:String): Bitmap?= withContext(Dispatchers.IO) {
            var bitmap: Bitmap?=null
            try {
                val myUrl = URL(url)
                val myConn = myUrl.openConnection() as HttpsURLConnection
                var inStream: InputStream = myConn.inputStream
                bitmap= BitmapFactory.decodeStream(inStream)
                myConn.disconnect()
                Log.i("DBG", "Immagine scaricata con successo")
            } catch (e: Exception) {
                Log.e("FYI", "Network thresd error:$e")
            }
            bitmap
        }
    }
}
