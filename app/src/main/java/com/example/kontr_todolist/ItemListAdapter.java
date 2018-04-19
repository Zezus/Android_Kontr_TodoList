package com.example.kontr_todolist;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by СадвакасовР on 19.04.2018.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private final Context context;
    private final List<ItemList> itemList;
    private MainActivity mainActivity;
    private Dialog dialog;


    public ItemListAdapter(Context context, List<ItemList> itemList, MainActivity mainActivity) {
        this.context = context;
        this.itemList = itemList;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.itemlist, null);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_item_list);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemListAdapter.ViewHolder holder, int position) {
        final ItemList item = itemList.get(position);
        holder.nameTV.setText(item.getName());
        holder.titleTV.setText(item.getTitle());

        holder.relativeLayout.setOnClickListener(view1 -> {
            mainActivity.changeFragmentClicked(null, item);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;
        TextView titleTV;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.il_name_tv);
            titleTV = itemView.findViewById(R.id.il_title_tv);
            relativeLayout = itemView.findViewById(R.id.il_item_all_rl);

        }
    }
}
