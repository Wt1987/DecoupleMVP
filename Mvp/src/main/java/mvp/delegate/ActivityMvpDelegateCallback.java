package mvp.delegate;

import mvp.iview.IBaseView;
import mvp.present.IBasePresenter;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public interface ActivityMvpDelegateCallback <P extends IBasePresenter, V extends IBaseView>
        extends BaseDelegateCallback<P, V> {

}