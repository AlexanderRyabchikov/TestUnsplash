package com.unsplash.client.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

import butterknife.ButterKnife
import butterknife.Unbinder
import rx.subscriptions.CompositeSubscription

abstract class AbstractFragment : Fragment() {

    protected var compositeSubscription = CompositeSubscription()

    private var unbinder: Unbinder? = null

    protected lateinit var navController: NavController

    @get:LayoutRes
    protected abstract val layoutRes: Int


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbinder = ButterKnife.bind(this, view)

        navController = Navigation.findNavController(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (unbinder != null) {
            unbinder!!.unbind()
        }
    }

    override fun onDestroy() {
        compositeSubscription.unsubscribe()
        super.onDestroy()
    }


    @SuppressLint("ResourceType")
    internal fun showMessage(@IdRes resId: Int) {

        showMessage(getString(resId))

    }

    internal fun showMessage(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}
