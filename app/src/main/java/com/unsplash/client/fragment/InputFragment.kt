package com.unsplash.client.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.EditText

import com.unsplash.client.R

import butterknife.BindView

class InputFragment : AbstractFragment() {

    private val TIME_WAIT = 1500L

    @BindView(R.id.exit)
    lateinit var exitButton: Button

    @BindView(R.id.nextFragment)
    lateinit var searchButton: Button

    @BindView(R.id.input_search_data)
    lateinit var editTextSearch: EditText

    private var clickTimePrev: Long = 0

    override val layoutRes: Int
        get() = R.layout.fragment_input

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        exitButton.setOnClickListener { onClickExit(it) }
        searchButton.setOnClickListener { onClickSearch(it) }

    }

    @Synchronized
    internal fun onClickExit(view: View) {
        val current = SystemClock.elapsedRealtime()

        if (current - clickTimePrev > TIME_WAIT) {

            showToast()
            clickTimePrev = SystemClock.elapsedRealtime()

        } else {

            clickTimePrev = 0
            activity?.finish()

        }
    }

    @SuppressLint("ResourceType")
    internal fun onClickSearch(view: View) {

        val searchText = editTextSearch.text.toString()

        if (searchText.isEmpty()) {
            showMessage(R.string.text_empty_message)
            return
        }

        val bundle = Bundle()
        bundle.putCharSequence(KEY_DATA, searchText)

        navController.navigate(R.id.action_inputFragment_to_resultFragment, bundle)
    }

    @SuppressLint("ResourceType")
    private fun showToast() {

        showMessage(R.string.message_exit)

    }

    companion object {
        const val KEY_DATA = "data"
    }

}
