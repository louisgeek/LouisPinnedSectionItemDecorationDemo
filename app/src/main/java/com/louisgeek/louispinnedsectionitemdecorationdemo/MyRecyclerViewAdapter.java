package com.louisgeek.louispinnedsectionitemdecorationdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kiguruming.recyclerview.itemdecoration.PinnedHeaderItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/4/19.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements PinnedHeaderItemDecoration.PinnedHeaderAdapter {

    public final static int TYPE_SECTION = 0;
    public final static int TYPE_DATA = 1;
    public final static int TYPE_FOOTER = 2;

    OnItemClickListener onItemClickListener;
    OnHeaderClickListener onHeaderClickListener;
    OnFooterClickListener onFooterClickListener;


    private List<Map<String, Object>> mapList = new ArrayList<>();
    private Context mContext;

    public MyRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;

        //模拟数据
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "AA" + i);
            if(i==0) {
                map.put("type", TYPE_SECTION);
            }else if(i==4-1){
                map.put("type", TYPE_FOOTER);
            }else{
                map.put("type", TYPE_DATA);
            }
            mapList.add(map);
        }
        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "BB" + i);
            if(i==0) {
                map.put("type", TYPE_SECTION);
            }else if(i==6-1){
                map.put("type", TYPE_FOOTER);
            }else{
                map.put("type", TYPE_DATA);
            }
            mapList.add(map);
        }
        for (int i = 0; i < 8; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "CC" + i);
            map.put("type", TYPE_FOOTER);
            if(i==0) {
                map.put("type", TYPE_SECTION);
            }else if(i==8-1){
                map.put("type", TYPE_FOOTER);
            }else{
                map.put("type", TYPE_DATA);
            }
            mapList.add(map);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
       // return new MyDataViewHolder(view);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case TYPE_SECTION:
                view = inflater.inflate(R.layout.list_header, parent, false);
                return new MyHeaderViewHolder(view);

            case TYPE_DATA:
                view = inflater.inflate(R.layout.list_item, parent, false);
                return new MyViewDataHolder(view);

            case TYPE_FOOTER:
                view = inflater.inflate(R.layout.list_footer, parent, false);
                return new MyFooterViewHolder(view);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //面向接口
        MyViewHolderBase myViewHolderBase= (MyViewHolderBase) holder;
        myViewHolderBase.bindViewDataEvent(holder,position);

    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }

    @Override
    public boolean isPinnedViewType(int viewType) {
        if (viewType == TYPE_SECTION) {
            return true;
        } else {
            return false;
        }
    }
        //手动重写
    @Override
    public int getItemViewType(int position) {
       // return super.getItemViewType(position);
       return Integer.parseInt(mapList.get(position).get("type").toString());
    }

    //自定义抽象类
    public abstract class MyViewHolderBase extends RecyclerView.ViewHolder{
        public MyViewHolderBase(View itemView) {
            super(itemView);
        }
        //绑定的抽象方法
        public abstract void bindViewDataEvent(RecyclerView.ViewHolder viewHolder,int position);
    }

    public  class MyViewDataHolder extends MyViewHolderBase{
        public TextView mTextView;

        public MyViewDataHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.id_tv_item);
        }

        @Override
        public void bindViewDataEvent(RecyclerView.ViewHolder viewHolder, final int position) {
            mTextView.setText(mapList.get(position).get("name").toString());

            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null) {
                        onItemClickListener.onItemClick(position, mapList.get(position));
                    }
                   // Toast.makeText(mContext, "onItemClick pos:" + position, Toast.LENGTH_SHORT).show();
                }
            });
            mTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemClickListener!=null) {
                        onItemClickListener.onItemLongClick(position, mapList.get(position));
                    }
                    //Toast.makeText(mContext, "onItemLongClick pos:" + position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }


    }

    public    class MyHeaderViewHolder extends MyViewHolderBase {

        TextView title;

        public MyHeaderViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.id_tv_head_item);
        }

        @Override
        public void bindViewDataEvent(RecyclerView.ViewHolder viewHolder, final int position) {
            title.setText(mapList.get(position).get("name").toString());

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHeaderClickListener!=null) {
                        onHeaderClickListener.onHeaderClick(position, mapList.get(position));
                    }
                   // Toast.makeText(mContext, "onHeaderClick pos:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public  class MyFooterViewHolder extends MyViewHolderBase{

        Button button;

        public MyFooterViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.id_foot_button);
        }

        @Override
        public void bindViewDataEvent(RecyclerView.ViewHolder viewHolder, final int position) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFooterClickListener!=null) {
                        onFooterClickListener.onFooterClick(position, mapList.get(position));
                    }
                    //Toast.makeText(mContext, "onFooterClick pos:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, Object object);

        void onItemLongClick(int position, Object object);
    }

    public interface OnHeaderClickListener {
        void onHeaderClick(int position, Object object);

    }
    public interface OnFooterClickListener {
        void onFooterClick(int position, Object object);
    }
    /**
     * 设置监听方法
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.onHeaderClickListener = onHeaderClickListener;
    }
    public void setOnFooterClickListener(OnFooterClickListener onFooterClickListener) {
        this.onFooterClickListener = onFooterClickListener;
    }


    /*    //自己解决pos动态改变问题
     void addHeaderItem(int insertPosition,Object object) {
         Map<String, Object> map = new HashMap<>();
         map.put("name", "新的标题" + insertPosition);
         map.put("type", TYPE_SECTION);
        notifyItemRangeInserted(insertPosition,1);
    }*/

    //自己解决pos动态改变问题
    void addDataItem(int position, Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "新的数据" + position);
        map.put("type", TYPE_DATA);
        mapList.add(position, map);
        notifyDataSetChanged();
        //notifyItemInserted(position);
    }
   /* //自己解决pos动态改变问题
    void addFooterItem(int insertPosition,Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "footer" + insertPosition);
        map.put("type", TYPE_FOOTER);
        mapList.add(insertPosition, map);
        notifyItemRangeInserted(insertPosition, 1);
    }*/
    //自己解决pos动态改变问题
    void deleteDataItem(int position) {
        mapList.remove(position);
        //暂时解决RecyclerView删除第一项报错问题  IndexOutOfBoundsException Invalid item position
        if (position == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRemoved(position);
        }
        //后来发现https://github.com/lucasr/twoway-view/issues/134   有同样的问题 2016-4-12 20:10:49
    }
}
