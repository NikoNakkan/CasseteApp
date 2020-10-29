package com.syroniko.casseteapp.ChatAndMessages

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syroniko.casseteapp.MainClasses.toast
import com.syroniko.casseteapp.R
import com.syroniko.casseteapp.firebase.Auth
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChatAdapter @Inject constructor(
    @ApplicationContext val context: Context
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    var messages: ArrayList<Message> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    lateinit var imageUrl: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MESSAGE_RIGHT) {
            val view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false)
            ViewHolder(view)
        }
        else {
            val view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showMessage.text = messages[position].text
        holder.timestampTv.visibility = View.INVISIBLE

        val sdf = SimpleDateFormat("hh:mm dd/MM/yyyy")
        val netDate = Date(messages[position].timestamp)
        holder.timestampTv.text = sdf.format(netDate)

        holder.messageSeen.text = if (messages[position].read){
            "Seen"
        }
        else{
            "Sent"
        }


//        Chat chat =mChat.get(position);
//        holder.show_message.setText(chat.message);
//        holder.timestampTv.setVisibility(View.INVISIBLE);
//        if(imageUrl.equals("default")){
//         //   holder.userImage.setImageResource(R.mipmap.ic_launcher);
//        }
//        else{
//      //      Glide.with(mContext).load(imageUrl).into(holder.userImage);
//        }
//        if(position==mChat.size()-1){
////            Log.d("cmoon",String.valueOf(chat.isSeen()));
////            if(chat.isSeen()){
////                holder.messageSeen.setText("Seen");
////            }
////            else{
////                holder.messageSeen.setText("Delivered");
////
////
////            }
////        }
//        else{
//            holder.messageSeen.setVisibility(View.INVISIBLE);
//        }
//        String time= chat.timestamp;
//        Calendar cal=Calendar.getInstance(Locale.ENGLISH);
//        cal.setTimeInMillis(Long.parseLong(time));
//        String dateTime= DateFormat.format("hh:mm",cal).toString();
//        holder.timestampTv.setText(dateTime);
    }

    override fun getItemCount() = messages.size

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val showMessage: TextView = itemView.findViewById(R.id.chat_item_textView_bubble)
        val userImage: ImageView = itemView.findViewById(R.id.profile_image_chat_activity)
        val messageSeen: TextView = itemView.findViewById(R.id.message_seen_text)
        val timestampTv: TextView = itemView.findViewById(R.id.timestamptextviewchat)
//        val messageSeenTv: TextView = itemView.findViewById(R.id.message_read_text_view)

        override fun onClick(v: View) {
            if (timestampTv.visibility == View.INVISIBLE) {
                timestampTv.visibility = View.VISIBLE
                messageSeen.visibility = View.VISIBLE
            }
            else {
                timestampTv.visibility = View.INVISIBLE
                messageSeen.visibility = View.INVISIBLE
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val uid = Auth.getUid()
        return if(messages[position].senderId == uid){
            Log.d(ChatAdapter::class.simpleName, "RIGHT")
            MESSAGE_RIGHT;
        }
        else{
            Log.d(ChatAdapter::class.simpleName, uid ?: "LEFT")
            MESSAGE_LEFT;
        }
    }

    companion object {
        const val MESSAGE_RIGHT = 0
        const val MESSAGE_LEFT = 1
    }

}