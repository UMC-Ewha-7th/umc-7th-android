package com.example.simpleproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomAdapter(
    private val items: ArrayList<Profile>,
    private val context: Context
) : BaseAdapter() {
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.item_profile, parent, false)
        } else {
            view = convertView
        }

        view?.findViewById<TextView>(R.id.profile_tv1)?.text = items[position].name
        view?.findViewById<TextView>(R.id.profile_tv2)?.text = items[position].age

        return view!!
    }

}