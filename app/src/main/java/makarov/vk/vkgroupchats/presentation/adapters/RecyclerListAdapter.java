package makarov.vk.vkgroupchats.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class RecyclerListAdapter<ViewHolder extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<ViewHolder> {
    private List<T> mList;
    private Context mContext;

    public RecyclerListAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void changeList(List<T> newList) {
        mList = newList;
    }

    public List<T> getList() {
        return mList;
    }

    public Context getContext() {
        return mContext;
    }
}