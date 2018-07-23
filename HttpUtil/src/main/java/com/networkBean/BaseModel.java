package com.networkBean;

/**
 * author : taowang
 * date :2018/7/23
 * description:
 **/
public class BaseModel<T> {
    /**
     * 0成功
     * 1失败
     * -1异常
     */
    private int state;
    public static int STATE_SUCCESS = 0;
    public static int STATE_SUCCESS_NO_DATA = 1;
    public static int STATE_EXCEPTION = -1;
    private String stateInfo;
    private T resInfo;
    public T getResInfo() {
        return resInfo;
    }

    public void setResInfo(T resInfo) {
        this.resInfo = resInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public boolean dataCanUse() {
        if (state == 200 && resInfo != null) {
            return true;
        }
        return false;
    }
}
