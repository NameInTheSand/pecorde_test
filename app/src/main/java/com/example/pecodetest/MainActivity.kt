package com.example.pecodetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.pecodetest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val fragments: ArrayList<Fragment> = arrayListOf(
        FragmentNotification(1),
        FragmentNotification(2), FragmentNotification
            (3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = MyViewPagerAdapter(this, fragments)
        binding.myVp2.adapter = adapter
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
            binding.myVp2.currentItem = oldPosition-1
        }
        binding.myVp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val newCounter = binding.myVp2.currentItem +1
                binding.counter.text = newCounter.toString()
            }
        })
    }
}