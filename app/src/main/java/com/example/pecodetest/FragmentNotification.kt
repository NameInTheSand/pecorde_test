package com.example.pecodetest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.pecodetest.api.NotificationData
import com.example.pecodetest.api.PushNotification
import com.example.pecodetest.api.RetrofitInstance
import com.example.pecodetest.databinding.FragmentNotificationBinding
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.launch

const val TOPIC = "/topics/test"

class FragmentNotification(private val number: Int) : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val TAG = "MYTAG"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        binding.pushButton.setOnClickListener {
            PushNotification(
                NotificationData(
                    title = "You create a notification",
                    message = "Notification $number"
                ), TOPIC
            ).also {
                sendNotification(it)
            }

        }
        return binding.root
    }

    private fun sendNotification(notification: PushNotification) =
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d(TAG, "Response: ok")
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
}
