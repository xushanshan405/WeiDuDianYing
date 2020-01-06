package com.bw.movie.fragment.gpjlFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.Base.BaseFragment;
import com.bw.movie.R;
import com.bw.movie.adapter.YFAdapter;
import com.bw.movie.app.App;
import com.bw.movie.bean.GPJLBean;
import com.bw.movie.bean.ZhiFuBaoBean;
import com.bw.movie.bean.ZhiFuBean;
import com.bw.movie.contract.HomeConteract;
import com.bw.movie.presenter.YFPresenter;
import com.bw.movie.utils.PayResult;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class YFFragment extends BaseFragment<YFPresenter> implements HomeConteract.GPJLContreact.IView {


    @BindView(R.id.yf_recy)
    RecyclerView yfrecy;

    @BindView(R.id.meiyou_tu)
    ImageView meiyou_tu;
    @BindView(R.id.meiyou_xinxi)
    TextView meiyouxinxi;

    @BindView(R.id.zong)
    LinearLayout zong;
    @Override
    protected YFPresenter providePresenter() {
        return new YFPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        String userId = App.sharedPreferences.getString("userId", null);
        String sessionId = App.sharedPreferences.getString("sessionId", null);
        mPresenter.getGPJLPresenter(userId, sessionId, 1, "10", "2");
        yfrecy.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_yf;
    }
    @Override
    public void onGPJLSuccess(GPJLBean data) {
        List<GPJLBean.ResultBean> result = data.getResult();
        if (result != null) {
            yfrecy.setAdapter(new YFAdapter(getActivity(), result));
        }else {
            zong.setVisibility(View.VISIBLE);
            meiyou_tu.setImageResource(R.mipmap.zanwuguanzhu);
            meiyouxinxi.setText("暂无记录");
        }
    }

    @Override
    public void onGPJLFailure(Throwable e) {

    }

    @Override
    public void onZFSuccess(ZhiFuBean data) {

    }

    @Override
    public void onZFFailure(Throwable e) {

    }

    @Override
    public void onZFBSuccess(ZhiFuBaoBean data) {

    }

    @Override
    public void onZFBFailure(Throwable e) {

    }
}
