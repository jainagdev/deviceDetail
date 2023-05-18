package com.sechk.worker.addressbook.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.jai.deviceid.R
import com.jai.deviceid.model.DetailData


class DetailAdapter(val context: Context?,
                    data: ArrayList<DetailData>
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    var details = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.detail_adapter_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {


        return details.size


    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var detail=details.get(position)
       holder.header_tv.text=detail.name
       holder.value_tv.text=detail.value
        holder.share_iv.tag=position
        holder.copy_iv.tag=position
        holder.copy_iv.setOnClickListener {
            var tag=it.tag as Int
            Toast.makeText(context,"${details.get(tag).name} Copied to Clipboard",Toast.LENGTH_LONG).show()
            val clipboard = context!!.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", details.get(tag).value)
            clipboard.setPrimaryClip(clip)
        }
        holder.share_iv.setOnClickListener {
            var tag=it.tag as Int
           // Toast.makeText(context,"${details.get(tag).name} Copied to Clipboard",Toast.LENGTH_LONG).show()

            /*Create an ACTION_SEND Intent*/
            var intent =  Intent(Intent.ACTION_SEND)
            /*This will be the actual content you wish you share.*/
            var shareBody = "Your ${details.get(tag).name} is : \n ${details.get(tag).value}"
            /*The type of the content is text, obviously.*/
            intent.type="text/plain"
            /*Applying information Subject and Body.*/
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, details.get(tag).name)
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            /*Fire!*/
            context!!.startActivity(Intent.createChooser(intent, "Share ${details.get(tag).name}"))
        }


    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {

      var value_tv=view.findViewById<TextView>(R.id.value_tv)
      var header_tv=view.findViewById<TextView>(R.id.header_tv)
      var share_iv=view.findViewById<ImageView>(R.id.share_iv)
      var copy_iv=view.findViewById<ImageView>(R.id.copy_iv)




    }
}