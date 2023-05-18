package com.jai.deviceid.view.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jai.deviceid.R
import com.jai.deviceid.model.DetailData
import com.sechk.worker.addressbook.adapter.DetailAdapter
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {


    var devide_id=""
    var list=ArrayList<DetailData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

        setContentView(R.layout.activity_home)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        createModelList()

        detail_recycler_view.layoutManager=LinearLayoutManager(this@HomeActivity)
        detail_recycler_view.adapter=DetailAdapter(this@HomeActivity,list)



       // Log.e("DISPLAY",DISPLAY)
        //Log.e("SERIAL",SERIAL)
        //Log.e("DEVICE",DEVICE)
        // title=resources.getString(R.string.app_name)



    }


    private fun getGsfAndroidId(context: Context): String? {
        val URI = Uri.parse("content://com.google.android.gsf.gservices")
        val ID_KEY = "android_id"
        val params = arrayOf(ID_KEY)
        val c = context.getContentResolver().query(URI, null, null, params, null)
        if (c != null && (!c.moveToFirst() || c.getColumnCount() < 2)) {
            if (!c.isClosed()) c.close()
            return null
        }
        return try {
            if (c != null) {
                val result = java.lang.Long.toHexString(c.getString(1).toLong())
                if (!c.isClosed()) c.close()
                result
            } else {
                null
            }
        } catch (e: NumberFormatException) {
            if (!c!!.isClosed()) c!!.close()
            null
        }
    }

    fun shareText(text:String)
    {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.type = "text/plain"

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
    fun copyText(text: String)
    {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(baseContext,"Detail Copied",Toast.LENGTH_LONG).show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            android.R.id.home ->
            {
               finish()
            return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun createModelList()
    {
        devide_id= Settings.Secure.getString(baseContext.getContentResolver(),
            Settings.Secure.ANDROID_ID)
        val deviceName = Build.MODEL
        val deviceMan = Build.MANUFACTURER
        val HARDWARE = Build.HARDWARE
        val FINGERPRINT = Build.FINGERPRINT
        val DISPLAY = Build.DISPLAY
        val SERIAL = Build.BOARD
        val DEVICE = Build.DEVICE

     /*   Log.e("Android Device ID 1",devide_id)

        Log.e("Manufacturer 2",deviceMan)
        Log.e("Device Build  3",FINGERPRINT)
        Log.e("Model 4",deviceName)
        Log.e("Google Services Framework ID 5",getGsfAndroidId(this@HomeActivity)!!)
        Log.e("HARDWARE 6",HARDWARE)*/

        list.add(DetailData("Android Device ID",devide_id))
        list.add(DetailData("Manufacturer",deviceMan))
        list.add(DetailData("Device Build",FINGERPRINT))
        list.add(DetailData("Model",deviceName))
        list.add(DetailData("Google Services Framework ID",getGsfAndroidId(this@HomeActivity)!!))
        list.add(DetailData("HARDWARE",HARDWARE))

    }


}