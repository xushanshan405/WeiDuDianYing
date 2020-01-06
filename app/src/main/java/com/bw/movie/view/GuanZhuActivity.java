package com.bw.movie.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Base.BaseActivity;
import com.bw.movie.Base.BasePresenter;
import com.bw.movie.R;
import com.bw.movie.app.App;
import com.bw.movie.fragment.gzfragment.DYGZFragment;
import com.bw.movie.fragment.gzfragment.YYGZFragment;
import com.bw.movie.utils.ActivityCollectorUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;

public class GuanZhuActivity extends BaseActivity {

    @BindView(R.id.gz_tab)
    TabLayout gztab;

    @BindView(R.id.gz_page)
    ViewPager gzpage;

    @BindView(R.id.title_fanhui)
    ImageView titlefanhui;

    @BindView(R.id.title_biaoti)
    TextView titlebiaoti;
    private ArrayList<Fragment> list;
    private ArrayList<String> name;

    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected void initData() {
        list.add(new DYGZFragment());
        list.add(new YYGZFragment());
        name.add("电影");
        name.add("影院");
        gzpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        gztab.setupWithViewPager(gzpage);
    }

    @Override
    protected void initView() {
        ActivityCollectorUtil.addActivity(this);
        list = new ArrayList<>();
        name = new ArrayList<>();
        titlebiaoti.setText("我的关注");
        String sessionId = App.sharedPreferences.getString("sessionId", null);
        String userId = App.sharedPreferences.getString("userId", null);
        if (userId != null && sessionId != null) {

        }else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_guan_zhu;
    }
}
