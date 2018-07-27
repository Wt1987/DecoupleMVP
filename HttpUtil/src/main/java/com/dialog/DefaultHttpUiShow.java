package com.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class DefaultHttpUiShow {

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity, String msg) {


        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        final HttpDialogUtils httpDialogUtils = new HttpDialogUtils();
        httpDialogUtils.showProgress(activityWeakReference.get(),msg);

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Activity context;
                        if ((context = activityWeakReference.get()) != null
                                && !context.isFinishing()) {
                            httpDialogUtils.dismissProgress();
                        }
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity) {
        return applyProgressBar(activity, "");
    }
}
