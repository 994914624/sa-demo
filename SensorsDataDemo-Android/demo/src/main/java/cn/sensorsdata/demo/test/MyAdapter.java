package cn.sensorsdata.demo.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.sensorsdata.demo.R;

/**
 * Created by yang on 2017/10/16
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private ArrayList<String> mData;
    private static String TAG="MyAdapter";

    public MyAdapter(ArrayList<String> data) {
        this.mData = data;

    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        Log.e(TAG,""+viewHolder.hashCode());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // 绑定数据
        holder.mTv.setText(mData.get(position));
//        holder.mTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG,"onClick222");
//            }
//        });
//        holder.mLl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG,"onClick333");
//            }
//        });

//        try {
//            SensorsDataAPI.sharedInstance().setViewProperties(holder.itemView,new JSONObject().put("text",""+position));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"click",Toast.LENGTH_SHORT).show();
                Log.e(TAG,"onClick444");
                SensorsDataAPI.sharedInstance().track("test");

            }
        });
        Log.e(TAG+"###",holder.hashCode()+"  #  :"+holder.mTv.getVisibility());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        LinearLayout mLl;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv_recycleView_item);
            mLl = (LinearLayout) itemView.findViewById(R.id.ll_recycleView_item);
//            mTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e(TAG,"onClick111");
//                }
//            });
        }
    }
}

