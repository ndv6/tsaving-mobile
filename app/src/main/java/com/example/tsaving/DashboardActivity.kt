package com.example.tsaving

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardFragment : androidx.fragment.app.Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        civ_dashboard.setOnClickListener{

        }

        super.onViewCreated(view, savedInstanceState)
    }
}