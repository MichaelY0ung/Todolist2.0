package com.michael.todolist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.todolist.Model.ListUtils;
import com.michael.todolist.Module.translate;
import com.michael.todolist.R;

import java.util.ArrayList;

/**
 * Created by Michael on 2016/9/2.
 */
public class MyDoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int FIRST_HEAD = 0;
    public static final int HEAD =1;
    public static final int NO_HEAD =2;
    private final ArrayList<ListUtils> mArrayList;
    private final Context mContext;
    private PopupWindow popupView;
    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public  interface OnItemClickListener {
        void onClick(View view, int position);
    }
    public MyDoListAdapter(ArrayList<ListUtils> arrayList, Context context){
        mArrayList = arrayList;
        mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_content_with_title,parent,false);
        TodayViewHolder holder = new TodayViewHolder(view, new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (view.getId()){
                    case R.id.recy_item:
                        break;
                    default:
                        Toast.makeText(mContext,"111",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof TodayViewHolder){
            TodayViewHolder todayViewHolder = (TodayViewHolder)holder;
            if(position==0){
                todayViewHolder.card_title.setVisibility(View.VISIBLE);
                todayViewHolder.card_title.setText(" "+translate.cardTitle(mArrayList.get(position).status));
                todayViewHolder.itemView.setTag(FIRST_HEAD);
            }
            else{
                if(mArrayList.get(position).leader==1){
                    todayViewHolder.card_title.setVisibility(View.VISIBLE);
                    todayViewHolder.card_title.setText(" "+translate.cardTitle(mArrayList.get(position).status));
                    todayViewHolder.itemView.setTag(HEAD);
                }
                else{
                    todayViewHolder.card_title.setVisibility(View.GONE);
                    todayViewHolder.card_title.setTag(NO_HEAD);
                  //  Log.d("11111",position+"");
                }
            }
            todayViewHolder.itemView.setContentDescription(translate.cardTitle(mArrayList.get(position).status));
            todayViewHolder.type.setText(mArrayList.get(position).type);
            todayViewHolder.time.setText(mArrayList.get(position).beginTime+"--"+mArrayList.get(position).endTime);
            todayViewHolder.title.setText(mArrayList.get(position).title);
            todayViewHolder.text.setText(mArrayList.get(position).text);
            if(mArrayList.get(position).important) {
                //Log.d("11111",position+"");
                todayViewHolder.important.setBackgroundResource(R.color.importantBackground);
            }
            else{
                todayViewHolder.important.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            todayViewHolder.type.setBackgroundResource(translate.typeChange(mArrayList.get(position).type));
            todayViewHolder.itemView.setTag(position);
        }

    }

    @Override
    public int getItemCount() {
        return null==mArrayList?0:mArrayList.size();
    }
    public class TodayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView card_title;
        TextView type;
        TextView time;
        ImageButton more;
        TextView title;
        TextView text;
        TextView important;
        LinearLayout linearLayout;
        LinearLayout content_liner;
        OnItemClickListener onItemClickListener;
        public TodayViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            card_title = (TextView) itemView.findViewById(R.id.card_title);
            type = (TextView) itemView.findViewById(R.id.type_name);
            time = (TextView) itemView.findViewById(R.id.item_time);
            more = (ImageButton) itemView.findViewById(R.id.more);
            title = (TextView) itemView.findViewById(R.id.item_title);
            text = (TextView) itemView.findViewById(R.id.card_text);
            important = (TextView) itemView.findViewById(R.id.important);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.recy_item);
            content_liner = (LinearLayout)itemView.findViewById(R.id.content_liner);
            this.onItemClickListener = onItemClickListener;
            more.setOnClickListener(this);
            type.setOnClickListener(this);
            linearLayout.setOnClickListener(this);
            text.setOnClickListener(this);
            title.setOnClickListener(this);
            content_liner.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
          Log.d("11111", "" + getAdapterPosition());
            clickListener.onClick(v,getAdapterPosition());
        }
    }
}
//            int[] location = new int[2];
//            v.getLocationInWindow(location);
//            Log.d("TAG", "点击的item的高度:" + v.getHeight() + "x值:" + location[0] + "y值" + location[1]);
//            if (MyUtils.getScreenHeight(context) - MyUtils.getNavigationBarHeight(context) - location[1] < MyUtils.dip2px(context, 80)) {
////                popupView.showAsDropDown(v, MyUtils.getScreenWidth(context) / 2 - (int) MyUtils.dip2px(context, 40), -v.getHeight() - (int) MyUtils.dip2px(context, 80));
//                popupView.showAsDropDown(v, MyUtils.getScreenWidth(context) / 4 - (int) MyUtils.dip2px(context, 40), -v.getHeight() - (int) MyUtils.dip2px(context, 80));
//            } else {
////                popupView.showAsDropDown(v, MyUtils.getScreenWidth(context) / 2 - (int) MyUtils.dip2px(context, 40), 0);
//                popupView.showAsDropDown(v, MyUtils.getScreenWidth(context) / 4 - (int) MyUtils.dip2px(context, 40), 0);
//

