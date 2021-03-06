package mvp.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import base.butterKnife.KnifeCommand;
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

    protected boolean isUseMvp = true;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        if(isUseMvp){
            mActivityDelegate = new ActivityDelegateImpl<>(this);
            mActivityDelegate.onCreate();
        }

        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUiCommand(null);
        }
        initSaveInstanceState(savedInstanceState);
        initData();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isUseMvp){
            mActivityDelegate.onDestroy();
            mPresenter = null;
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(isUseMvp){
            mActivityDelegate.onResume();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(isUseMvp){
            mActivityDelegate.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isUseMvp){
            mActivityDelegate.onStop();
        }

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
     * 获取布局id
     *
     * @return
     */
    public abstract int getLayoutId();
    protected abstract void initData();
}
