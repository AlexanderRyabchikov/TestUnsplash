package com.unsplash.client.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.unsplash.client.R;

import java.util.Objects;

import butterknife.BindView;

public class InputFragment extends AbstractFragment {

    @BindView(R.id.exit)
    Button exitButton;

    @BindView(R.id.nextFragment)
    Button searchButton;

    @BindView(R.id.input_search_data)
    EditText editTextSearch;

    private long clickTimePrev = 0;

    private static final long TIME_WAIT = 1500L;
    public static final String KEY_DATA = "data";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exitButton.setOnClickListener(this::onClickExit);
        searchButton.setOnClickListener(this::onClickSearch);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_input;
    }

    synchronized void onClickExit(View view) {
        long current = SystemClock.elapsedRealtime();

        if(current - clickTimePrev > TIME_WAIT) {

            showToast();
            clickTimePrev = SystemClock.elapsedRealtime();

        } else {

            clickTimePrev = 0;
            Objects.requireNonNull(getActivity()).finish();

        }
    }

    @SuppressLint("ResourceType")
    void onClickSearch(View view){

        String searchText = editTextSearch.getText().toString();

        if(searchText.isEmpty()) {
            showMessage(R.string.text_empty_message);
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_DATA, searchText);

        navController.navigate(R.id.action_inputFragment_to_resultFragment, bundle);
    }

    @SuppressLint("ResourceType")
    private void showToast(){

        showMessage(R.string.message_exit);

    }

}
