package com.example.statussaver.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MediaAdapter(var fm: FragmentManager, var data: List<String>, var fragments: List<Fragment>) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position]
    }
}