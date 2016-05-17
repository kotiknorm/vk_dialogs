package makarov.vk.vkgroupchats.presentation.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.utils.DateUtils;

public class ChatsAdapter extends RecyclerListAdapter<ChatsAdapter.ViewHolder, Chat> {

    public ChatsAdapter(Context context, List<Chat> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Chat chat = getList().get(position);

        holder.chatName.setText(chat.getTitle());
        holder.date.setText(DateUtils.chatLastMessage(chat.getDate()));
        holder.itemView.setTag(chat);

        Picasso.with(getContext()).load(chat.getPhoto()).into(holder.image);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chat_name) TextView chatName;
        @Bind(R.id.chat_date) TextView date;
        @Bind(R.id.chat_body) TextView body;
        @Bind(R.id.chat_image) ImageView image;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }
    }
}
