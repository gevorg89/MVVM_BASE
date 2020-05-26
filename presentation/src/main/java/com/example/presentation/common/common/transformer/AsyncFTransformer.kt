package com.example.presentation.common.common.transformer

import com.example.domain.common.transformer.FTransformer
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher

/**
 * Created by Dr.jacky on 10/14/2018.
 */
class AsyncFTransformer<T> : FTransformer<T>() {

    override fun apply(upstream: Flowable<T>): Publisher<T> =
            upstream.subscribeOn(Schedulers.io())
}