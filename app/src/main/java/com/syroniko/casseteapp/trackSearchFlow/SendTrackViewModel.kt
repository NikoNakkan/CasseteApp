package com.syroniko.casseteapp.trackSearchFlow

import android.app.Application
import androidx.hilt.Assisted
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.FieldValue
import com.syroniko.casseteapp.mainClasses.Cassette
import com.syroniko.casseteapp.mainClasses.User
import com.syroniko.casseteapp.spotifyClasses.SpotifyTrack
import com.syroniko.casseteapp.firebase.Auth
import com.syroniko.casseteapp.firebase.CassetteDB
import com.syroniko.casseteapp.firebase.UserDB
import javax.inject.Inject

class SendTrackViewModel @Inject constructor(
    application: Application,
    @Assisted savedStateHandle: SavedStateHandle
): AndroidViewModel(application) {

    lateinit var track: SpotifyTrack
    lateinit var user: User
    private val queue = Volley.newRequestQueue(application)
    private val uid = Auth.getUid()
    private val restrictedReceivers = arrayListOf(uid)


    fun getGenre(callback: (String?) -> Unit){
        track.getGenreFromArtists(queue, user.spotifyToken, callback)
    }


    fun sendCassette(comment: String, callback: () -> Unit){
        UserDB.getPossibleReceivers(track.genre!!, restrictedReceivers) { possibleReceivers ->
            val cassette = Cassette(
                uid,
                track,
                comment,
                track.genre,
                possibleReceivers,
                restrictedReceivers)

            user.songsSent++
            CassetteDB.insert(cassette)
            if(uid != null) {
                UserDB.update(uid, hashMapOf(Pair("songsSent", FieldValue.increment(1))))
            }

            callback()
        }
    }
}