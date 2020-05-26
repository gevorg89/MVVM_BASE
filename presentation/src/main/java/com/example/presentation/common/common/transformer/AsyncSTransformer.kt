package com.example.presentation.common.common.transformer

import com.example.domain.common.transformer.STransformer
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.schedulers.Schedulers

/**
 * Created by Dr.jacky on 10/18/2018.
 */
class AsyncSTransformer<T> : STransformer<T>() {

    override fun apply(upstream: Single<T>): SingleSource<T> = upstream.subscribeOn(Schedulers.io())
}