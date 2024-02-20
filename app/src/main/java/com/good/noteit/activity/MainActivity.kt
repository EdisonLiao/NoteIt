package com.good.noteit.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.good.noteit.R
import com.good.noteit.adapter.SectionsPagerAdapter
import com.good.noteit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListener()

    }

    private fun initView() {

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)

    }

    private fun initListener() {
        binding.toolbar.ibSearch.setOnClickListener(this)
        binding.floatingActionButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_search -> {

            }
            R.id.floatingActionButton -> {
                startActivity(Intent(this, EditActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999 && resultCode == Activity.RESULT_OK) {
            // TODO: do something in here if in-app updates success
        } else {
            // TODO: do something in here if in-app updates failure
        }
    }
}
