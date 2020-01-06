package com.bw.movie.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Base.BaseActivity;
import com.bw.movie.R;
import com.bw.movie.app.App;
import com.bw.movie.bean.QXYYGZBean;
import com.bw.movie.bean.YYGZBean;
import com.bw.movie.bean.YingYuanXQBean;
import com.bw.movie.contract.HomeConteract;
import com.bw.movie.fragment.yingyuanxiangqing.YYPJFragment;
import com.bw.movie.fragment.yingyuanxiangqing.YYXQFragment;
import com.bw.movie.presenter.YingPingPresenter;
import com.bw.movie.presenter.YingYuanXQPresenter;
import com.google.android.material.tabs.TabLayout;

import java.nio.charset.Charset;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class YyXqActivity extends BaseActivity<YingYuanXQPresenter> implements HomeConteract.YingYuanXqContreact.IView {
    @BindView(R.id.invalid_name)
    TextView invalidname;
    @BindView(R.id.yyxq_recy)
    TextView yyxqrecy;
    @BindView(R.id.yyxq_tab)
    TabLayout yyxqtab;
    @BindView(R.id.yyxq_page)
    ViewPager yyxqpage;
    @BindView(R.id.yyxq_guanzhu)
    ImageView yyxqguanzhu;
    @BindView(R.id.yyxq_paiqi)
    Button yyxqpaiqi;


    private ArrayList<Fragment> list;
    private ArrayList<String> name;
    private String id;
    private boolean guanzhu;

    @Override
    protected YingYuanXQPresenter providePresenter() {
        return new YingYuanXQPresenter();
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        list.add(new YYXQFragment());
        list.add(new YYPJFragment());
        name = new ArrayList<>();
        name.add("电影详情");
        name.add("影院评价");
        yyxqpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return name.get(position);
            }
        });

        yyxqtab.setupWithViewPager(yyxqpage);
    }

    @Override
    protected void initView() {
        String sessionId = App.sharedPreferences.getString("sessionId", null);
        String userId = App.sharedPreferences.getString("userId", null);
        SharedPreferences sharedPreferences = getSharedPreferences("yyid", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");
        if (userId != null && sessionId != null) {
            mPresenter.getYingYuanXqPresenter(userId, sessionId, id);
        }else {
            mPresenter.getYingYuanXqPresenter("0", null, id);
        }
    }
    @Override
    protected int provideLayoutId() {
        return R.layout.activity_yy_xq;
    }

    @Override
    public void onYingYuanXqSuccess(YingYuanXQBean data) {
        String followCinema = data.getResult().getFollowCinema();
        int i = Integer.parseInt(followCinema);
        if (i == 1) {
            guanzhu = true;
            yyxqguanzhu.setImageResource(R.mipmap.xinxin);
        } else if (i == 2) {
            guanzhu = false;
            yyxqguanzhu.setImageResource(R.mipmap.emptyheart);
        }
        invalidname.setText(data.getResult().getName());
        invalidname.setText(data.getResult().getName());
        String label = data.getResult().getLabel();
        yyxqrecy.setText(label);

    }



    @Override
    public void onYingYuanXqFailure(Throwable e) {

    }

    @Override
    public void onYYGZSuccess(YYGZBean data) {

    }

    @Override
    public void onYYGZXqFailure(Throwable e) {

    }

    @Override
    public void onQXYYGZSuccess(QXYYGZBean data) {

    }

    @Override
    public void onQXYYGZXqFailure(Throwable e) {

    }

    @OnClick({R.id.yyxq_guanzhu, R.id.yyxq_paiqi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yyxq_guanzhu:
                guanzhu = !guanzhu;
                String sessionId = App.sharedPreferences.getString("sessionId", null);
                String userId = App.sharedPreferences.getString("userId", null);
                if (userId != null && sessionId != null) {
                    if (guanzhu) {
                        mPresenter.getYYGZXqPresenter(userId, sessionId, id);
                        yyxqguanzhu.setImageResource(R.mipmap.xinxin);
                    }else {
                        mPresenter.getQXYYGZXqPresenter(userId, sessionId, id);
                        yyxqguanzhu.setImageResource(R.mipmap.emptyheart);
                    }
                }else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,MainActivity.class));
                }
                break;
            case R.id.yyxq_paiqi:
                startActivity(new Intent(this, PaiQiActivity.class));
                break;
        }
    }
}
