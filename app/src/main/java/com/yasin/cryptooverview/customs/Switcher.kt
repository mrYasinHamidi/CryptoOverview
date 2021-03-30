package com.yasin.cryptooverview.customs

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
import com.yasin.cryptooverview.R
import com.yasin.cryptooverview.databinding.SwitcherBinding
import com.yasin.cryptooverview.listeners.SwitcherItemsClickListener

class Switcher : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context?, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private val root: View = inflate(context, R.layout.switcher, this)
    private val listeners = arrayListOf<SwitcherItemsClickListener>()

    init {
        setOnClicks()
    }

    fun addSwitcherItemClickListener(listener: SwitcherItemsClickListener) {
        listeners.add(listener)
    }

    private fun setOnClicks() {
        root.findViewById<TextView>(R.id.switcher_1h).setOnClickListener {
            for (i in listeners)
                i.on1hClick()
        }
        root.findViewById<TextView>(R.id.switcher_2h).setOnClickListener {
            for (i in listeners)
                i.on2hClick()
        }
        root.findViewById<TextView>(R.id.switcher_4h).setOnClickListener {
            for (i in listeners)
                i.on4hClick()
        }
        root.findViewById<TextView>(R.id.switcher_8h).setOnClickListener {
            for (i in listeners)
                i.on8hClick()
        }
        root.findViewById<TextView>(R.id.switcher_1d).setOnClickListener {
            for (i in listeners)
                i.on1dClick()
        }
        root.findViewById<TextView>(R.id.switcher_1w).setOnClickListener {
            for (i in listeners)
                i.on1wClick()
        }
    }
}
