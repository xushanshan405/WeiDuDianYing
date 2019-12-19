package com.bw.movie.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Base.BaseFragment;
import com.bw.movie.R;
import com.bw.movie.app.App;
import com.bw.movie.bean.SCTXBean;
import com.bw.movie.bean.ShengRiBean;
import com.bw.movie.bean.UserBean;
import com.bw.movie.bean.XiuGaiShouJIBean;
import com.bw.movie.contract.HomeConteract;
import com.bw.movie.presenter.UserPresenter;
import com.bw.movie.view.MainActivity;
import com.bw.movie.view.WdYpActivity;
import com.bw.movie.view.YuYueActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;


public class WoDeFragment extends BaseFragment<UserPresenter> implements HomeConteract.UserContreact.IView {

    @BindView(R.id.wd_touxiang)
    SimpleDraweeView wdTouxiang;
    @BindView(R.id.img_sz)
    ImageView imgSz;
    @BindView(R.id.wd_nicheng)
    TextView wdNicheng;
    @BindView(R.id.wd_wdyp)
    LinearLayout wdWdyp;
    @BindView(R.id.wd_guanzhu)
    ImageView wdGuanzhu;
    @BindView(R.id.wd_yuyue)
    ImageView wdYuyue;
    @BindView(R.id.goupiao_jilu)
    ImageView goupiaoJilu;
    @BindView(R.id.wd_kanguo)
    ImageView wdKanguo;
    @BindView(R.id.wd_pinglun)
    ImageView wdPinglun;
    @BindView(R.id.wd_fankui)
    ImageView wdFankui;
    @BindView(R.id.wd_xinxi)
    ImageView wdXinxi;
    @BindView(R.id.wd_wdxx)
    SimpleDraweeView wdWdxx;


    @Override
    protected UserPresenter providePresenter() {
        return new UserPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        String sessionId = App.sharedPreferences.getString("sessionId", null);
        String userId = App.sharedPreferences.getString("userId", null);
       if (userId!=null && sessionId!=null){
           mPresenter.getUserPresenter(userId,sessionId);
       }else {
           Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
       }
    }


    protected int provideLayoutId() {
        return R.layout.fragment_my;
    }

    @OnClick({R.id.wd_wdyp, R.id.wd_guanzhu, R.id.wd_yuyue, R.id.goupiao_jilu, R.id.wd_kanguo, R.id.wd_pinglun, R.id.wd_fankui, R.id.wd_xinxi, R.id.wd_wdxx,R.id.img_sz,R.id.wd_touxiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wd_wdyp:
                break;
            case R.id.wd_guanzhu:
                break;
            case R.id.wd_yuyue:
                break;
            case R.id.goupiao_jilu:
                break;
            case R.id.wd_kanguo:
                break;
            case R.id.wd_pinglun:
                break;
            case R.id.wd_fankui:
                break;
            case R.id.wd_xinxi:
                break;
            case R.id.wd_wdxx:
                break;
                case R.id.img_sz:
                break;
                case R.id.wd_touxiang:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
        }
    }


    @Override
    public void onUserSuccess(UserBean data) {
        UserBean.ResultBean result = data.getResult();
        String headPic = result.getHeadPic();
        Uri parse = Uri.parse(headPic);
        wdTouxiang.setImageURI(parse);
        String nickName = result.getNickName();
        wdNicheng.setText(nickName);
    }

    @Override
    public void onUserFailure(Throwable e) {

    }

    @Override
    public void onSCTXSuccess(SCTXBean data) {

    }

    @Override
    public void onSCTXFailure(Throwable e) {

    }

    @Override
    public void onXGSJHSuccess(XiuGaiShouJIBean data) {

    }

    @Override
    public void onXGSJHFailure(Throwable e) {

    }

    @Override
    public void onXGSRSuccess(ShengRiBean data) {

    }

    @Override
    public void onXGSRFailure(Throwable e) {

    }
}
