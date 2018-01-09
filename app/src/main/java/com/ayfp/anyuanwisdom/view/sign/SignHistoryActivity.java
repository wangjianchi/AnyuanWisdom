package com.ayfp.anyuanwisdom.view.sign;

import android.app.DatePickerDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayfp.anyuanwisdom.R;
import com.ayfp.anyuanwisdom.base.BaseActivity;
import com.ayfp.anyuanwisdom.base.IBasePresenter;
import com.ayfp.anyuanwisdom.config.preferences.Preferences;
import com.ayfp.anyuanwisdom.retrofit.AppResultData;
import com.ayfp.anyuanwisdom.retrofit.BaseObserver;
import com.ayfp.anyuanwisdom.retrofit.RetrofitService;
import com.ayfp.anyuanwisdom.utils.GlideUtils;
import com.ayfp.anyuanwisdom.view.ImageBrowserActivity;
import com.ayfp.anyuanwisdom.view.sign.adapter.SignImageAdapter;
import com.ayfp.anyuanwisdom.view.sign.bean.SignStatusBean;
import com.ayfp.anyuanwisdom.view.sign.presenter.SignPresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:: wangjianchi
 * @time: 2017/12/29  10:12.
 * @description:
 */

public class SignHistoryActivity extends BaseActivity{
    @BindView(R.id.tv_date)
    TextView mTextDate;
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.tv_sign_in_time)
    TextView mTextSignInTime;
    @BindView(R.id.tv_sign_in_address)
    TextView mTextSignInAddress;
    @BindView(R.id.tv_sign_out_time)
    TextView mTextSignOutTime;
    @BindView(R.id.tv_sign_out_address)
    TextView mTextSignOutAddress;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_content)
    TextView mTextContent;
    @BindView(R.id.iv_map_image)
    ImageView mImageMap;
    @BindView(R.id.layout_empty)
    View mLayoutEmpty;
    @BindView(R.id.tv_sign_out_des)
    TextView mTextSignOutDes;
    private SignImageAdapter mAdpater;
    private int year;
    private int month;
    private int day;
    @Override
    public void loadComplete() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sign_history;
    }

    @Override
    protected void initViews() {
        mTextTitle.setText("历史轨迹");
        //初始化Calendar日历对象
        Calendar mycalendar=Calendar.getInstance();
        year=mycalendar.get(Calendar.YEAR);
        //获取Calendar对象中的年
        month=mycalendar.get(Calendar.MONTH)+1;
        //获取Calendar对象中的月
        day=mycalendar.get(Calendar.DAY_OF_MONTH);
        //获取这个月的第几天
        mTextDate.setText(year+"年-"+month+"月-"+day+"日");
        getSignHistory();
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.tv_date) void selectDate(){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int monthOfYear, int dayOfMonth) {
                year = y;
                month = monthOfYear+1;
                day = dayOfMonth;
                mTextDate.setText(year+"年-"+month+"月-"+day+"日");
                getSignHistory();
            }
        },year,month-1,day);
        dialog.show();
    }

    private void getSignHistory(){
        showProgress();
        String time = year+"-"+month+"-"+day;
        RetrofitService.getApi().getSignHistory(RetrofitService.TOKEN, Preferences.getUserName(),time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AppResultData<SignStatusBean>>bindToLife())
                .subscribe(new BaseObserver<AppResultData<SignStatusBean>>() {

                    @Override
                    public void loadSuccess(AppResultData<SignStatusBean> data) {
                        dismissProgress();
                        if (data.getStatus() == RetrofitService.SUCCESS){
                            showHistory(data.getResult());
                            mLayoutEmpty.setVisibility(View.GONE);
                        }else {
                            mLayoutEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void showHistory(final SignStatusBean bean){
        mTextSignInTime.setText(bean.getSign_in_time()+"签到");
        mTextSignInAddress.setText("签到地点："+bean.getSign_in_address());
        mTextSignOutTime.setText(bean.getSign_out_time()+"退签");
        mTextSignOutAddress.setText("退签地点："+bean.getSign_out_address());
        mTextContent.setText(bean.getContent());
        if (bean.getSign_status() == SignPresenter.SIGN_STATUS_IN){
            mTextSignOutDes.setVisibility(View.VISIBLE);
        }else {
            mTextSignOutDes.setVisibility(View.GONE);
            GlideUtils.loadImageView(bean.getLocate_path_url(),mImageMap);
            mImageMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageBrowserActivity.start(SignHistoryActivity.this,bean.getLocate_path_url());
                }
            });
        }

        final List<String> imageStr = new ArrayList<>();
        imageStr.addAll(bean.getSign_in_imgs());
        imageStr.addAll(bean.getSign_out_imgs());
        mAdpater = new SignImageAdapter(imageStr);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(mAdpater);
        mAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageBrowserActivity.start(SignHistoryActivity.this,imageStr.get(position));
            }
        });
    }
    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.iv_date_left) void dateSub(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
        mTextDate.setText(year+"年-"+month+"月-"+day+"日");
        getSignHistory();
    }
    @OnClick(R.id.iv_date_right) void dateAdd(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.add(Calendar.DATE,1);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
        mTextDate.setText(year+"年-"+month+"月-"+day+"日");
        getSignHistory();
    }

}
