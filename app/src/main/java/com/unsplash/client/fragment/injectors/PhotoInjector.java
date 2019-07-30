package com.unsplash.client.fragment.injectors;


import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.unsplash.client.GlideApp;
import com.unsplash.client.R;
import com.unsplash.client.data.model.PhotoModel;

import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

public class PhotoInjector implements SlimInjector<PhotoModel> {

    @Override
    public void onInject(PhotoModel data, IViewInjector injector) {


        injector
                .with(R.id.image, view -> {

                    GlideApp.with(view.getContext())
                            .load(data.getUrlsPhoto().getRegular())
                            .apply(new RequestOptions().centerCrop())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into((ImageView)view); })
                .text(R.id.description, data.getDescription());


    }
}
