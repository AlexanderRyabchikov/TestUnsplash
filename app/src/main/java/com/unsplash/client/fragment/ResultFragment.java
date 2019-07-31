package com.unsplash.client.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.unsplash.client.R;
import com.unsplash.client.fragment.injectors.PhotoInjector;
import com.unsplash.client.network.Api.Api;
import com.unsplash.client.network.response.ApiError;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimAdapterEx;

import java.net.UnknownHostException;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ResultFragment extends AbstractFragment {

    private static final int SPAN_COUNT = 1;

    private MaterialDialog loadingDialog;

    @BindView(R.id.back)
    Button backButton;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @NonNull
    private SlimAdapterEx adapter = SlimAdapter.create();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        backButton.setOnClickListener(this::onClickBackButton);

        initLoadingDialog();
        initRecyclerView();
        initAdapter();

        loadData(getArguments().getString(InputFragment.KEY_DATA));
    }

    @SuppressLint("ResourceType")
    private void loadData(String query) {

        compositeSubscription.add(Api
                .getPhotos(1, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showLoading)
                .doOnEach(notification -> hideLoading())
                .subscribe(photoModels -> {

                    if(photoModels.isEmpty()){
                        showMessage(R.string.not_found_message);
                        onClickBackButton(null);
                    }

                    adapter.updateData(photoModels);
                }, this::handleError));

    }

    private void initAdapter() {
        adapter = SlimAdapter.create()
                .register(R.layout.item_photo, new PhotoInjector())
                .attachTo(recyclerView);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_result;
    }

    public void onClickBackButton(View view) {
        navController.navigate(R.id.action_resultFragment_to_inputFragment);
    }

    public void showLoading() {
        if (loadingDialog.isShowing()) {
            return;
        }

        loadingDialog.show();
    }

    public void hideLoading() {
        if (!loadingDialog.isShowing()) {
            return;
        }

        loadingDialog.dismiss();
    }

    private void initLoadingDialog() {
        loadingDialog = new MaterialDialog
                .Builder(getContext())
                .progress(true, 0)
                .backgroundColorRes(R.color.dialog_bg)
                .titleColorRes(R.color.dialog_caption)
                .contentColorRes(R.color.dialog_text)
                .title(R.string.dialog_loading_title)
                .content(R.string.dialog_loading_content)
                .cancelable(false)
                .build();
    }

    @SuppressLint("ResourceType")
    private void handleError(Throwable throwable){

        if (throwable instanceof ApiError) {
            ApiError apiError = (ApiError) throwable;

            switch (apiError.getCode()) {
                case 404:
                    showMessage(R.string.dialog_server_is_not_available);
                    return;
                case 401:
                    showMessage(R.string.dialog_not_authorized);
                    return;
            }

            String message = apiError.getDescription();
            showMessage(message);
        } else if (throwable instanceof UnknownHostException) {
            showMessage(R.string.common_no_internet_connection);
        } else {
            showMessage(throwable.getMessage());
        }

    }
}