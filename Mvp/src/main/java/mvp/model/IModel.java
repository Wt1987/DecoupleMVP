package mvp.model;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public interface IModel {

    /**
     * 数据为空处理
     * @return
     */
    boolean isNull();


    /**
     * 验证错误处理
     *
     */
    boolean isAuthError();

    /**
     * 业务处理错误处理
     * @return
     */
    boolean isBussessError();


    /**
     * 返回错误信息
     * @return
     */
    String getErrorMsg();
}
