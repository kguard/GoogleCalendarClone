package com.kguard.googlecalendarclone

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.snackbar.Snackbar
import com.kguard.gitlogin.base.BaseActivity
import com.kguard.googlecalendarclone.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.CalendarDay

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        binding.calendarView.topbarVisible = false
        binding.calendarView.isDynamicHeightEnabled = true
        binding.calendarView.selectedDate = CalendarDay.today()
        binding.mainToolbar.titleText.text=Utils.getTitleText(CalendarDay.today())
        binding.calendarView.setOnDateChangedListener{ _, date,_ ->
            binding.mainToolbar.titleText.text= Utils.getTitleText(date)

        }
        binding.calendarView.setOnMonthChangedListener{ _, date->
            binding.calendarView.selectedDate= date
            binding.mainToolbar.titleText.text= Utils.getTitleText(date)
        }

        binding.mainToolbar.titleButton.setOnClickListener{
            when (binding.calendarView.visibility)
            {
                View.GONE -> {
                    binding.mainToolbar.titleArrow.animate().rotation(-180f).start()
                    binding.calendarView.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,R.anim.translate_to_bottom))
                    binding.calendarView.visibility = View.VISIBLE
                }
                View.VISIBLE -> {
                    binding.mainToolbar.titleArrow.animate().rotation(0f).start()
                    binding.calendarView.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,R.anim.translate_to_top))
                    binding.calendarView.visibility = View.GONE
                }

            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                binding.navigationView.visibility=View.VISIBLE
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.menu_search -> Toast.makeText(this, "search", Toast.LENGTH_SHORT).show()
            R.id.menu_profile -> Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show()
            R.id.menu_today -> {
                binding.calendarView.currentDate = CalendarDay.today()
                binding.calendarView.selectedDate = CalendarDay.today()
                binding.mainToolbar.titleText.text= Utils.getTitleText(CalendarDay.today())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> binding.drawerLayout.closeDrawer(GravityCompat.START)
            binding.calendarView.visibility == View.VISIBLE -> {
                binding.mainToolbar.titleArrow.animate().rotation(0f).start()
                binding.calendarView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.translate_to_top))
                binding.calendarView.visibility = View.GONE
            }
        }
        super.onBackPressed()
    }
}