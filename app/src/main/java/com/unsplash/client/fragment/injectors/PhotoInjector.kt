package com.unsplash.client.fragment.injectors

import android.view.View
import android.widget.ImageView

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.unsplash.client.GlideApp
import com.unsplash.client.R
import com.unsplash.client.data.model.PhotoModel

import net.idik.lib.slimadapter.SlimInjector
import net.idik.lib.slimadapter.viewinjector.IViewInjector

class PhotoInjector : SlimInjector<PhotoModel> {

    override fun onInject(data: PhotoModel, injector: IViewInjector<*>) {

        injector
                .with<View>(R.id.image) {

                    GlideApp.with(it.context)
                            .load(data.urlsPhoto?.regular)
                            .apply(RequestOptions().centerCrop())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(it as ImageView)
                }
                .text(R.id.description, data.description)


    }
}
