package com.unsplash.client.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.afollestad.materialdialogs.MaterialDialog
import com.unsplash.client.R
import com.unsplash.client.fragment.injectors.PhotoInjector
import com.unsplash.client.network.Api.Api
import com.unsplash.client.network.response.ApiError

import net.idik.lib.slimadapter.SlimAdapter
import net.idik.lib.slimadapter.SlimAdapterEx

import java.net.UnknownHostException

import butterknife.BindView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ResultFragment : AbstractFragment() {

    private val SPAN_COUNT = 1

    private lateinit var loadingDialog: MaterialDialog

    @BindView(R.id.back)
    lateinit var backButton: Button

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    private var adapter = SlimAdapter.create()

    override val layoutRes: Int
        get() = R.layout.fragment_result

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        backButton.setOnClickListener { onClickBackButton(it) }

        initLoadingDialog()
        initRecyclerView()
        initAdapter()

        arguments?.getString(InputFragment.KEY_DATA)?.let { loadData(it) }
    }

    @SuppressLint("ResourceType")
    private fun loadData(query: String) {

        compositeSubscription.add(Api
                .getPhotos(1, query)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe{showLoading()}
                ?.doOnEach { hideLoading() }
                ?.subscribe({ photoModels ->

                    if (photoModels.isEmpty()) {
                        showMessage(R.string.not_found_message)
                        onClickBackButton(null)
                    }

                    adapter.updateData(photoModels)
                }, this::handleError))

    }

    private fun initAdapter() {
        adapter = SlimAdapter.create()
                .register(R.layout.item_photo, PhotoInjector())
                .attachTo(recyclerView)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
    }

    private fun onClickBackButton(view: View?) {
        navController.navigate(R.id.action_resultFragment_to_inputFragment)
    }

    private fun showLoading() {
        if (loadingDialog.isShowing) {
            return
        }

        loadingDialog.show()
    }

    private fun hideLoading() {
        if (!loadingDialog.isShowing) {
            return
        }

        loadingDialog.dismiss()
    }

    private fun initLoadingDialog() {
        loadingDialog = MaterialDialog.Builder(context!!)
                .progress(true, 0)
                .backgroundColorRes(R.color.dialog_bg)
                .titleColorRes(R.color.dialog_caption)
                .contentColorRes(R.color.dialog_text)
                .title(R.string.dialog_loading_title)
                .content(R.string.dialog_loading_content)
                .cancelable(false)
                .build()
    }

    @SuppressLint("ResourceType")
    private fun handleError(throwable: Throwable) {

        when (throwable) {
            is ApiError -> {

                when (throwable.code) {
                    404 -> {
                        showMessage(R.string.dialog_server_is_not_available)
                        return
                    }
                    401 -> {
                        showMessage(R.string.dialog_not_authorized)
                        return
                    }
                }

                val message = throwable.description
                showMessage(message)
            }
            is UnknownHostException -> showMessage(R.string.common_no_internet_connection)
            else -> throwable.message?.let { showMessage(it) }
        }

    }
}