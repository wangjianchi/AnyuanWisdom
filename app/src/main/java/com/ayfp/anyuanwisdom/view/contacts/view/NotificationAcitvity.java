package com.ayfp.anyuanwisdom.view.contacts.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ayfp.anyuanwisdom.R;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;

/**
 * @author:: wangjianchi
 * @time: 2017/12/21  14:50.
 * @description:
 */

public class NotificationAcitvity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        onParseIntent();
    }

    private void onParseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)){
            ArrayList<IMMessage> messages = (ArrayList<IMMessage>) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            if (messages == null || messages.size() >= 1) {
                if (messages.size() > 1){
                    Intent it = new Intent(NotificationAcitvity.this,ContactsActivity.class);
                    startActivity(it);
                }else {
                    IMMessage message = messages.get(messages.size()-1);
                    String   sessionId = message.getSessionId();
                    ChatActivity.start(NotificationAcitvity.this,sessionId);
                }
                finish();
            }else {
                finish();
            }
        }
    }
}
