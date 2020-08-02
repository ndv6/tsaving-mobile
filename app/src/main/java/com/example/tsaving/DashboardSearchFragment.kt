package com.example.tsaving

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.tsaving.model.VirtualAccount
import kotlinx.android.synthetic.main.dashboard_search_fragment.*

class DashboardSearchFragment: Fragment(){
    lateinit var vaMap: Map<String, VirtualAccount>
    lateinit var sgsSet : MutableSet<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dashboard_search_fragment, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        val vaList = bundle?.getParcelableArrayList<VirtualAccount>("va_list") ?: listOf<VirtualAccount>()
        vaMap = vaList?.map { it.vaLabel to it }?.toMap() ?: mapOf<String, VirtualAccount>()

        sv_dashboard_search.setIconifiedByDefault(false)
        sv_dashboard_search.onActionViewExpanded()
        val inputManager =  activity?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(sv_dashboard_search, InputMethodManager.SHOW_IMPLICIT)
        loadSuggestion()
        Log.i("load sgs", sgsSet.toString())

        cv_dashboard_search_result.setOnClickListener{
            val intent = Intent(requireActivity(), VADetailsActivity::class.java)
            intent.putExtra("va_detail", vaMap[tv_dashboard_search_va_label.text.toString()])
            startActivity(intent)
        }

        sv_dashboard_search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(sub: String?): Boolean {
                if (vaMap.containsKey(sub)) {
                    tv_dashboard_search_not_found.text = ""
                    tv_dashboard_search_not_found.visibility = View.GONE
                    cv_dashboard_search_result.visibility = View.VISIBLE
                    tv_dashboard_search_va_num.text = vaMap[sub]?.vaNum ?: ""
                    tv_dashboard_search_va_balance.text = vaMap[sub]?.vaBalance.toString() ?: "0"
                    tv_dashboard_search_va_label.text = vaMap[sub]?.vaLabel ?: ""
                }else {
                    tv_dashboard_search_not_found.text = "Virtual Account not found"
                    tv_dashboard_search_not_found.visibility = View.VISIBLE
                    cv_dashboard_search_result.visibility = View.GONE
                }
                if (sub != null) {
                    saveSuggestion(sub)
                }
                Log.i("suggestion", sgsSet.toString())
                return false
            }

            override fun onQueryTextChange(chg: String?): Boolean {
                return true
            }
        })
    }

    fun getSPreference(): SharedPreferences = activity?.getSharedPreferences("tsaving_mobile", Context.MODE_PRIVATE) ?: PreferenceManager.getDefaultSharedPreferences(requireContext())

    fun loadSuggestion() {
        sgsSet = getSPreference()?.getStringSet("search_suggestion", mutableSetOf<String>()) as MutableSet<String> ?: mutableSetOf<String>()
    }

    fun saveSuggestion(query: String) {
        val editor = getSPreference().edit()
        if (!sgsSet.contains(query)) {
            sgsSet.add(query)
            editor.putStringSet("search_suggestion", sgsSet)
            editor.commit()
        }
    }
}

