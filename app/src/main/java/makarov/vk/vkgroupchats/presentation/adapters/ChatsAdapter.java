package makarov.vk.vkgroupchats.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.presentation.image.ImageLoaderStrategy;
import makarov.vk.vkgroupchats.presentation.image.ImageLoaderStrategyImpl;
import makarov.vk.vkgroupchats.presentation.presenters.ChatsListPresenter;
import makarov.vk.vkgroupchats.utils.DateUtils;

public class ChatsAdapter extends RecyclerListAdapter<ChatsAdapter.ViewHolder, Chat> {

    private final ChatsListPresenter mChatsListPresenter;
    private final ImageLoaderStrategy mImageLoaderStrategy;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Chat chat = (Chat) view.getTag();
            mChatsListPresenter.onClickChat(chat);
        }
    };

    public ChatsAdapter(Context context, List<Chat> list, ChatsListPresenter chatsListPresenter) {
        super(context, list);
        mChatsListPresenter = chatsListPresenter;
        mImageLoaderStrategy = new ImageLoaderStrategyImpl(context);
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
        holder.body.setText(getBody(chat));

        holder.itemView.setTag(chat);
        holder.itemView.setOnClickListener(mOnClickListener);

        mImageLoaderStrategy.loadImage(chat, holder.imagePlace);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chat_name) TextView chatName;
        @Bind(R.id.chat_date) TextView date;
        @Bind(R.id.chat_body) TextView body;
        @Bind(R.id.chat_image) ImageView imagePlace;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }
    }

    private String getBody(Chat chat) {
        String body = chat.getBody();
        if (!TextUtils.isEmpty(body)) {
            return body;
        }

        return getContext().getString(R.string.chat_with_attachments);
    }
}
