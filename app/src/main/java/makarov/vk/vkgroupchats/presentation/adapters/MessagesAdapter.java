package makarov.vk.vkgroupchats.presentation.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.data.models.Chat;
import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.data.models.Photo;
import makarov.vk.vkgroupchats.data.models.User;
import makarov.vk.vkgroupchats.utils.DateUtils;
import makarov.vk.vkgroupchats.vk.VkManager;

public class MessagesAdapter extends BaseAdapter {

    private static final int CURRENT_USER_MESSAGE = 0;
    private static final int OTHER_USER_MESSAGE = 1;
    private static final int UNKNOWN = -1;

    List<Message> mList = new ArrayList<>();
    private Context mContext;
    @Nullable private Chat mChat;

    public MessagesAdapter(Context context) {
        super();
        mContext = context;
    }

    public int getItemViewType(int position) {
        return getMessageType(getItem(position));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Message getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Message message = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = createViewWithHolder(getItemViewType(position), viewGroup);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            if (viewHolder.mMessageType != getMessageType(message)) {
                convertView = createViewWithHolder(getItemViewType(position), viewGroup);
            }
        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.setMessage(message);

        return convertView;
    }

    public void addItems(List<Message> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    private View createViewWithHolder(int viewType, ViewGroup viewGroup) {
        View convertView = createView(viewType, viewGroup);
        ViewHolder viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
        return convertView;
    }

    private View createView(int viewType,  ViewGroup viewGroup) {
        if (viewType == CURRENT_USER_MESSAGE) {
            return LayoutInflater.from(mContext).inflate(R.layout.right_message_item, viewGroup, false);
        } else {
            return LayoutInflater.from(mContext).inflate(R.layout.left_message_item, viewGroup, false);
        }
    }

    public void setChat(Chat chat) {
        mChat = chat;
    }

    public class ViewHolder {

        @Bind(R.id.message_container) LinearLayout mMessageContainer;
        @Bind(R.id.time) TextView mDate;
        @Bind(R.id.body) TextView mBody;

        List<View> mGeneratedView = new LinkedList<>();

        private int mMessageId;
        public int mMessageType = UNKNOWN;

        private final View mRoot;

        public ViewHolder(View parentView) {
            ButterKnife.bind(this, parentView);
            mRoot = parentView;
        }

        private Context getContext() {
            return mMessageContainer.getContext();
        }

        private void addMessage(String body) {
            if (TextUtils.isEmpty(body)) {
                mBody.setVisibility(View.GONE);
            } else {
                mBody.setVisibility(View.VISIBLE);
                mBody.setText(body);
            }
        }

        private void addImages(List<Photo> photos) {
            for (Photo photo : photos) {
                ImageView imageView = new ImageView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                int imageSize = (int) getContext().getResources().getDimension(R.dimen.message_image_size);

                params.width = imageSize;
                imageView.setLayoutParams(params);

                mMessageContainer.addView(imageView, mMessageContainer.getChildCount() - 1);
                mGeneratedView.add(imageView);
                Picasso.with(getContext()).load(photo.getPhotoUrl()).into(imageView);
            }

        }

        private void reset() {
            for (View view : mGeneratedView) {
                mMessageContainer.removeView(view);
            }
            mGeneratedView.clear();
        }

        public void setMessage(Message message) {
            if (mMessageId == message.getId()) {
                return;
            }

            reset();
            ImageView avatar = (ImageView) mRoot.findViewById(R.id.avatar);
            if (avatar != null) {
                Picasso.with(getContext()).load(getUserPhotoUrl(message.getFromId())).into(avatar);
            }

            mDate.setText(DateUtils.chatLastMessage(mChat.getDate()));

            mMessageId = message.getId();
            mMessageType = getMessageType(message);
            addMessage(message.getBody());

            if (message.hasPhotos()) {
                addImages(message.getPhotos());
            }
        }
    }

    @Nullable
    private String getUserPhotoUrl(String userId) {
        if (mChat == null) {
            return null;
        }

        User user =  mChat.getUser(userId);
        if (user == null) {
            return null;
        }

        return user.getPhoto();
    }

    public static int getMessageType(Message message) {
        return message.getFromId().equals(VkManager.getUserId()) ? CURRENT_USER_MESSAGE :
                OTHER_USER_MESSAGE;
    }
}