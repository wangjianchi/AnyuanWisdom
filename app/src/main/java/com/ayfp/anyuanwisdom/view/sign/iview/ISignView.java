package com.ayfp.anyuanwisdom.view.sign.iview;

import com.ayfp.anyuanwisdom.base.IBaseView;

/**
 * @author:: wangjianchi
 * @time: 2017/12/25  14:27.
 * @description:
 */

public interface ISignView extends IBaseView {
    void loadLocation(boolean sign,double latitude,double longitude);
    void showSignInView();
    void showSignOutView();
    void showNotSignView();
}
