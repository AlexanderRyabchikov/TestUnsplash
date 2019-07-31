package com.unsplash.client.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

public abstract class AbstractFragment extends Fragment {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Nullable
    private Unbinder unbinder;

    NavController navController;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        navController = Navigation.findNavController(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        compositeSubscription.unsubscribe();
        super.onDestroy();
    }

    @LayoutRes
    protected abstract int getLayoutRes();


    @SuppressLint("ResourceType")
    void showMessage(@IdRes int resId) {

        showMessage(getString(resId));

    }

    void showMessage(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }
}
