package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard_drawer.*
import kotlinx.android.synthetic.main.nav_header_drawer.view.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity: AppCompatActivity() {
    lateinit var mDrawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_drawer)

        setSupportActionBar(tb_fragment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mDrawer = drawer_layout
        setupDrawerContent(nav_drawer_view)

        val headerView = nav_drawer_view.getHeaderView(0)
        headerView.tv_drawer_name.text = BaseApplication.custName
        headerView.tv_drawer_email.text = BaseApplication.custEmail


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.flContent, DashboardFragment()).commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mDrawer.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun selectDrawerItem(mi: MenuItem) {
        var fragment: Fragment = (DashboardFragment::class.java).newInstance()
        when (mi.itemId){
            R.id.nav_drawer_home -> {
                fragment = (DashboardFragment::class.java).newInstance()
            }
            R.id.nav_drawer_profile -> {
                fragment = (ProfileFragment::class.java).newInstance()
            }
            R.id.nav_drawer_create_va -> {
                fragment = (AddVaFragment::class.java).newInstance()
            }
            R.id.nav_drawer_history -> {
                fragment = (TransactionHistoryFragment::class.java).newInstance()
            }
            R.id.nav_drawer_logout -> {
                BaseApplication.token = ""
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit()
        mi.setChecked(true)
        setTitle(mi.title)
        mDrawer.closeDrawers()
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }
}
