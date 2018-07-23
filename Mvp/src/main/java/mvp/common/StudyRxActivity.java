package mvp.common;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import base.eventBus.BusImpl;
import base.butterKnife.KnifeCommand;
import base.util.PermissionsResultListener;
import mvp.delegate.ActivityDelegate;
import mvp.delegate.ActivityDelegateImpl;
import mvp.delegate.ActivityMvpDelegateCallback;
import mvp.iview.IBaseView;
import mvp.present.IBasePresenter;

/**
 * author : taowang
 * date :2018/7/21
 * description:MVP基础activity类
 **/
public abstract class StudyRxActivity<P extends IBasePresenter, V extends IBaseView>
        extends RxAppCompatActivity
        implements ActivityMvpDelegateCallback<P, V>,IBaseView {

    protected Activity mContext;

    //ActivityDelegate 和activity具有一样的生命周期，可以控制是否attachView;
    private ActivityDelegate mActivityDelegate;
    //P层的操作类
    protected P mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        mActivityDelegate = new ActivityDelegateImpl<>(this);
        mActivityDelegate.onCreate();

        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUiCommand(null);
            bindEvent();
        }
        initSaveInstanceState(savedInstanceState);
        initData();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            BusImpl.getInstance().unregister(this);
        }
        mActivityDelegate.onDestroy();
        mPresenter = null;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            BusImpl.getInstance().register(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mActivityDelegate.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mActivityDelegate.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityDelegate.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getOptionsMenuId() > 0) {
            getMenuInflater().inflate(getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * @param rootView
     * 确定使用butternife绑定view
     */
    public void bindUiCommand(View rootView) {
        KnifeCommand.bind(this);
    }

    /**
     * 是否使用EventBus
     *
     * @return
     */
    public boolean useEventBus() {
        return false;
    }

    /**
     * 用于创建presenter
     * @return
     */
    protected abstract P InitPresenter();


    /**
     * 这个方法由MvpInternalDelegate
     * 调用 BaseDelegateCallback 来创建Presenter
     * @return
     */
    @Override
    public P createPresenter() {
        mPresenter = InitPresenter();
        return mPresenter;
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }


    /**
     * 初始化bundle数据
     *
     * @param savedInstanceState
     */
    public void initSaveInstanceState(Bundle savedInstanceState) {

    }





    public void showToast(String msg){

        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();

    }



    /**
     * 初始化菜单布局
     *
     * @return
     */
    public int getOptionsMenuId() {
        return 0;
    }

    /**
     * 使用rxbus时实现
     */
    public void bindEvent() {

    }
    /**
     * 获取布局id
     *
     * @return
     */
    public abstract int getLayoutId();
    protected abstract void initData();
}
