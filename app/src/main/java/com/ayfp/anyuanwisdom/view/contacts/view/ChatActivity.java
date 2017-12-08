package com.ayfp.anyuanwisdom.view.contacts.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.nim.session.SessionHelper;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.main.OnlineStateChangeObserver;
import com.netease.nim.uikit.api.model.user.UserInfoObserver;
import com.netease.nim.uikit.business.session.activity.BaseMessageActivity;
import com.netease.nim.uikit.business.session.constant.Extras;
import com.netease.nim.uikit.business.session.fragment.MessageFragment;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 建池 on 2017/12/7.
 */

public class ChatActivity extends BaseMessageActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    private UserInfoObserver uinfoObserver;
    private boolean isResume = false;
    public static void start(Context context, String cId,String username) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, cId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, SessionHelper.getP2pCustomization());
        intent.setClass(context, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTextTitle.setText(getIntent().getStringExtra("username"));
        displayOnlineState();
        registerObservers(true);
        registerOnlineStateChangeListener(true);
    }
    private void displayOnlineState() {
        if (!NimUIKitImpl.enableOnlineState()) {
            return;
        }
        String detailContent = NimUIKitImpl.getOnlineStateContentProvider().getDetailDisplay(sessionId);
        setSubTitle(detailContent);
    }
    private void registerOnlineStateChangeListener(boolean register) {
        if (!NimUIKitImpl.enableOnlineState()) {
            return;
        }
        NimUIKitImpl.getOnlineStateChangeObservable().registerOnlineStateChangeListeners(onlineStateChangeObserver, register);
    }
    private void registerObservers(boolean register) {
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(commandObserver, register);
     //   NimUIKit.getContactChangedObservable().registerObserver(friendDataChangedObserver, register);
    }
    OnlineStateChangeObserver onlineStateChangeObserver = new OnlineStateChangeObserver() {
        @Override
        public void onlineStateChange(Set<String> accounts) {
            // 更新 toolbar
            if (accounts.contains(sessionId)) {
                // 按照交互来展示
                displayOnlineState();
            }
        }
    };

    /**
     * 命令消息接收观察者
     */
    Observer<CustomNotification> commandObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            if (!sessionId.equals(message.getSessionId()) || message.getSessionType() != SessionTypeEnum.P2P) {
                return;
            }
            showCommandMessage(message);
        }
    };


    protected void showCommandMessage(CustomNotification message) {
        if (!isResume) {
            return;
        }

        String content = message.getContent();
        try {
            JSONObject json = JSON.parseObject(content);
            int id = json.getIntValue("id");
            if (id == 1) {
                // 正在输入
                ToastUtils.showToast("对方正在输入...");
            } else {
            }

        } catch (Exception e) {

        }
    }
    private void registerUserInfoObserver() {
        if (uinfoObserver == null) {
            uinfoObserver = new UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    if (accounts.contains(sessionId)) {
                  //      requestBuddyInfo();
                    }
                }
            };
        }
        NimUIKit.getUserInfoObservable().registerObserver(uinfoObserver, true);
    }

    private void unregisterUserInfoObserver() {
        if (uinfoObserver != null) {
            NimUIKit.getUserInfoObservable().registerObserver(uinfoObserver, false);
        }
    }



    @Override
    protected MessageFragment fragment() {
        Bundle arguments = getIntent().getExtras();
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.P2P);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.message_fragment_container);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initToolBar() {

    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
