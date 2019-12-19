package com.bw.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.bean.FindBean;
import com.bw.movie.view.GouPiaoActivity;

import java.util.List;

public class DqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FindBean.ResultBean> list;
    private setChangListenter setChangListenter;

    public DqAdapter(GouPiaoActivity gouPiaoActivity, List<FindBean.ResultBean> result) {
        this.context = gouPiaoActivity;
        this.list = result;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dq_layout, parent, false);
        return new MyHoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHoder){
            ((MyHoder) holder).find_name.setText(list.get(position).getRegionName());
            ((MyHoder) holder).find_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (setChangListenter!=null){
                        setChangListenter.getChang(list.get(position).getRegionId(),position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyHoder extends RecyclerView.ViewHolder{

        private final TextView find_name;
        private final LinearLayout find_linear;

        public MyHoder(@NonNull View itemView) {
            super(itemView);
             find_name = itemView.findViewById(R.id.dq_name);
            find_linear = itemView.findViewById(R.id.dq_linear);
        }
    }
    public void setOnClickListenter(setChangListenter setChangListenter){
        this.setChangListenter = setChangListenter;
    }
    public interface setChangListenter{
        void getChang(String id,int postion);
    }
}
