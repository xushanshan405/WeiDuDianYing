package com.bw.movie.fragment.gpjlFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.Base.BaseFragment;
import com.bw.movie.R;
import com.bw.movie.adapter.DFAdapter;
import com.bw.movie.app.App;
import com.bw.movie.bean.GPJLBean;
import com.bw.movie.bean.ZhiFuBaoBean;
import com.bw.movie.bean.ZhiFuBean;
import com.bw.movie.contract.HomeConteract;
import com.bw.movie.presenter.DFPresenter;
import com.bw.movie.utils.PayResult;
import com.bw.movie.view.ZuoActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class DFFragment extends BaseFragment<DFPresenter> implements HomeConteract.GPJLContreact.IView {
    private int SDK_PAY_FLAG =1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Toast.makeText(getActivity(), "" + msg.obj, Toast.LENGTH_SHORT).show();
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            Toast.makeText(getActivity(), payResult.getResult(),
                    Toast.LENGTH_LONG).show();
        }

        ;
    };
    private String orderId;
    private double fare;
    @BindView(R.id.df_recy)
    XRecyclerView dfrecy;

    @BindView(R.id.meiyou_tu)
    ImageView meiyoutu;

    @BindView(R.id.meiyou_xinxi)
    TextView meiyouxinxi;

    @BindView(R.id.zong)
    LinearLayout zong;

    @Override
    protected DFPresenter providePresenter() {
        return new DFPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        String userId = App.sharedPreferences.getString("userId", null);
        String sessionId = App.sharedPreferences.getString("sessionId", null);
        mPresenter.getGPJLPresenter(userId, sessionId, 1, "10", "1");
        dfrecy.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_df;

    }

    @Override
    public void onGPJLSuccess(GPJLBean data) {
        List<GPJLBean.ResultBean> result = data.getResult();
        if (result != null) {
            DFAdapter dfAdapter = new DFAdapter(getActivity(), result);
            dfrecy.setAdapter(dfAdapter);
            dfAdapter.getChange(new DFAdapter.onorderId() {
                @Override
                public void getorderId(String orderId) {
                    if (orderId != null) {
                        Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
                        View inflate = View.inflate(getActivity(), R.layout.weizhi_layout, null);
                        RadioButton weixin = inflate.findViewById(R.id.weixin);
                        RadioButton zhifubao = inflate.findViewById(R.id.zhifubao);
                        dialog.setContentView(inflate);
                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.BOTTOM);
                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        String s = String.valueOf(fare);
                        dialog.show();
                        inflate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        weixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                String sessionId = App.sharedPreferences.getString("sessionId", null);
                                String userId = App.sharedPreferences.getString("userId", null);
                                mPresenter.getZFs(userId, sessionId, "1", orderId);
                                dialog.dismiss();
                            }
                        });
                        zhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                String sessionId = App.sharedPreferences.getString("sessionId", null);
                                String userId = App.sharedPreferences.getString("userId", null);
                                mPresenter.getZFBs(userId, sessionId, "1", orderId);
                                dialog.dismiss();
                            }
                        });
                    }
                }
            });
        } else {
            zong.setVisibility(View.VISIBLE);
            meiyoutu.setImageResource(R.mipmap.zanwuguanzhu);
            meiyouxinxi.setText("暂无记录");
        }
    }

    @Override
    public void onGPJLFailure(Throwable e) {

    }

    @Override
    public void onZFSuccess(ZhiFuBean data) {
        String message = data.getMessage();
        if (data.getStatus().equals("0000")) {
            PayReq payReq = new PayReq();
            payReq.appId = data.getAppId();
            payReq.nonceStr = data.getNonceStr();
            payReq.partnerId = data.getPartnerId();
            payReq.prepayId = data.getPrepayId();
            payReq.sign = data.getSign();
            payReq.timeStamp = data.getTimeStamp();
            payReq.packageValue = data.getPackageValue();
            payReq.extData = "app data";
            App.api.sendReq(payReq);
        }
    }

    @Override
    public void onZFFailure(Throwable e) {

    }

    @Override
    public void onZFBSuccess(ZhiFuBaoBean data) {
        if (data.getStatus().equals("0000")) {
            Toast.makeText(getActivity(), "支付宝", Toast.LENGTH_SHORT).show();
            final String orderInfo = orderId;
            // 订单信息
            String results = data.getResult();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(getActivity());
                    Map<String, String> result = alipay.payV2(results, true);

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }

    @Override
    public void onZFBFailure(Throwable e) {

    }
}
