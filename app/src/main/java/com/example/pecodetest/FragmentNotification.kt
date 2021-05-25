package com.example.pecodetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pecodetest.databinding.FragmentNotificationBinding

class FragmentNotification(private val number: Int) : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater)
        binding.pushButton.setOnClickListener {
            Toast.makeText(requireActivity(), "$number", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}
