package com.bw.movie.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.Base.BaseActivity;
import com.bw.movie.Base.BasePresenter;
import com.bw.movie.R;
import com.bw.movie.app.App;
import com.bw.movie.fragment.gpjlFragment.DFFragment;
import com.bw.movie.fragment.gpjlFragment.YFFragment;
import com.bw.movie.utils.ActivityCollectorUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GouPiaoJiLuActivity extends BaseActivity {
    @BindView(R.id.gpjl_tab)
    TabLayout gpjltab;
    @BindView(R.id.gpjl_page)
    ViewPager gpjlpage;
    @BindView(R.id.title_fanhui)
    ImageView titlefanhui;
    @BindView(R.id.title_biaoti)
    TextView titlebiaoti;
    private ArrayList<String> name;
    private ArrayList<Fragment> list;

    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected void initData() {
        list.add(new DFFragment());
        list.add(new YFFragment());
        name.add("待付款");
        name.add("已付款");
        gpjlpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        gpjltab.setupWithViewPager(gpjlpage);
        titlebiaoti.setText("购票记录");
    }

    @Override
    protected void initView() {
        String userId = App.sharedPreferences.getString("userId", null);
        String sessionId = App.sharedPreferences.getString("sessionId", null);
        if (userId != null && sessionId != null) {
        }else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        ActivityCollectorUtil.addActivity(this);
        list = new ArrayList<>();
        name = new ArrayList<>();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_gou_piao_ji_lu;
    }
    @OnClick(R.id.title_fanhui)
    public void onViewClicked() {
        finish();
    }
}
