package com.example.pecodetest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.pecodetest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val fragments: ArrayList<Fragment> = arrayListOf(
        FragmentNotification(1)
    )
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = MyViewPagerAdapter(this, fragments)
        binding.myVp2.adapter = adapter
        if(intent.extras?.isEmpty == false){
            id = intent.extras?.get("ID").toString().toInt()
            fragments.clear()
            for (i in 1..id){
                fragments.add(FragmentNotification(i))
            }
            binding.myVp2.adapter?.notifyDataSetChanged()
            binding.myVp2.currentItem =id
            val newCounter = binding.myVp2.currentItem + 1
            binding.counter.text = newCounter.toString()
        }
        attachListeners()
    }

    private fun attachListeners() {
        binding.plusBtn.setOnClickListener {
            fragments.add(FragmentNotification(fragments.size + 1))
            binding.myVp2.adapter?.notifyDataSetChanged()
        }
        binding.minusBtn.setOnClickListener {
            val oldPosition = binding.myVp2.currentItem
            val myFragment =
                supportFragmentManager.findFragmentByTag("f" + binding.myVp2.currentItem)
            fragments.remove(myFragment)
            binding.myVp2.adapter?.notifyDataSetChanged()
            binding.myVp2.currentItem = oldPosition - 1
            NotificationManagerCompat.from(this).cancel(oldPosition + 1);
        }
        binding.myVp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val newCounter = binding.myVp2.currentItem + 1
                binding.counter.text = newCounter.toString()
                if (newCounter == 1) {
                    binding.minusBtn.visibility = View.GONE
                } else binding.minusBtn.visibility = View.VISIBLE
            }
        })
    }
}