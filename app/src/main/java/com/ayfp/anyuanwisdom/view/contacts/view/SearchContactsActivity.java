package com.ayfp.anyuanwisdom.view.contacts.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.ToastUtils;
import com.ayfp.anyuanwisdom.view.contacts.adapter.SearchContactAdpater;
import com.ayfp.anyuanwisdom.view.contacts.bean.Person;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/18  16:42.
 * @description:
 */

public class SearchContactsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.et_search)
    EditText mEditSearch;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private SearchContactAdpater mAdpater;
    private List<Person> mList = new ArrayList<>();
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_search_contacts;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("搜索");
        mEditSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = mEditSearch.getText().toString().trim();
                    if (searchText.length() == 0) {
                        ToastUtils.showToast("搜索关键字不能为空");
                        return true;
                    }
                    search(searchText);
                    return true;
                }
                return false;
            }
        });
        initList();
    }
    private void initList(){
        mAdpater = new SearchContactAdpater(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdpater);
        mAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SearchContactsActivity.this, UserDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userbean",mList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void search(String key){
        showProgress();
        RetrofitService.getApi().searchUser(RetrofitService.TOKEN, Preferences.getUserName(),key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<List<Person>>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<List<Person>>>() {

                    @Override
                    public void loadSuccess(AppResultData<List<Person>> data) {
                        dismissProgress();
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            mList.clear();
                            if (data.getResult().size() > 0){
                                mList.addAll(data.getResult());
                                mAdpater.notifyDataSetChanged();
                            }else {
                                ToastUtils.showToast("没有找到相关用户");
                            }

                        }
                    }
                });
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_back) void back(){
        finish();
    }
}
