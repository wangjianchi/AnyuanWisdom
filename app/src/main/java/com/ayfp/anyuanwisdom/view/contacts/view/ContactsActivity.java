package com.ayfp.anyuanwisdom.view.contacts.view;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author:: wangjianchi
 * @time: 2017/11/23  16:40.
 * @description:
 */

public class ContactsActivity extends BaseActivity {
    @BindView(R.id.tv_recent_contacts)
    TextView textRecentContacts;
    @BindView(R.id.tv_contacts)
    TextView textContacts;
    @BindView(R.id.layout_contact_btn)
    View mViewBtn;
    @BindView(R.id.iv_unread)
    ImageView mImageUnread;
    private ContactsFragment contactsFragment;
    private RecentContactsFragment mRecentContactsFragment;
    private FragmentTransaction transaction;

    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initViews() {
        recentContacts();
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.tv_recent_contacts) void recentContacts(){
        clickContacts(false);
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        if (contactsFragment != null){
            transaction.hide(contactsFragment);
        }
        if (mRecentContactsFragment == null){
            mRecentContactsFragment = new RecentContactsFragment();
            transaction.add(R.id.layout_container,mRecentContactsFragment);
        }else {
            transaction.show(mRecentContactsFragment);
        }
        transaction.commit();
    }
    @OnClick(R.id.tv_contacts) void contacts(){
        clickContacts(true);
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        if (mRecentContactsFragment != null){
            transaction.hide(mRecentContactsFragment);
        }
        if (contactsFragment == null){
            contactsFragment = new ContactsFragment();
            transaction.add(R.id.layout_container,contactsFragment);
        }else {
            transaction.show(contactsFragment);
        }
        transaction.commit();
    }

    private void clickContacts(boolean contacts){
        if (contacts){
            mViewBtn.setBackgroundResource(R.mipmap.bg_contact_btn);
            textRecentContacts.setTextColor(getResources().getColor(R.color.white));
            textContacts.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else {
            mViewBtn.setBackgroundResource(R.mipmap.bg_contact_btn_left);
            textRecentContacts.setTextColor(getResources().getColor(R.color.colorPrimary));
            textContacts.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnClick(R.id.iv_back) void back(){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int unreadNumber = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        if (unreadNumber > 0){
            mImageUnread.setVisibility(View.GONE);
        }else {
            mImageUnread.setVisibility(View.GONE);
        }
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);    }

    @Override
    protected void onPause() {
        super.onPause();
        // 退出聊天界面或离开最近联系人列表界面，建议放在onPause中
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
    }
}
