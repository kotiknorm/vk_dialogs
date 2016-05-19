package makarov.vk.vkgroupchats.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.data.models.Photo;
import makarov.vk.vkgroupchats.vk.VkManager;

public class MessagesAdapter extends BaseAdapter {

    private static final int CURRENT_USER_MESSAGE = 0;
    private static final int OTHER_USER_MESSAGE = 1;
    private static final int UNKNOWN = -1;

    List<Message> mList = new ArrayList<>();
    Context mContext;

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
        ViewHolder  viewHolder = new ViewHolder((ViewGroup) convertView.findViewById(R.id.message_container));
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

    private class ViewHolder {

        private final ViewGroup mParentView;
        private int mMessageId;
        public int mMessageType = UNKNOWN;

        ViewHolder(ViewGroup parentView) {
            mParentView = parentView;
        }

        private Context getContext() {
            return mParentView.getContext();
        }

        private void addMessage(String body) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            textView.setText(body);
            mParentView.addView(textView);
        }

        private void addImages(List<Photo> photos) {
            for (Photo photo : photos) {
                ImageView imageView = new ImageView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                int imageSize = (int) getContext().getResources().getDimension(R.dimen.message_image_size);

                params.width = imageSize;
                params.height = imageSize;
                imageView.setLayoutParams(params);

                mParentView.addView(imageView);
                Picasso.with(getContext()).load(photo.getPhotoUrl()).into(imageView);
            }

        }

        private void reset() {
            mParentView.removeAllViews();
        }

        public void setMessage(Message message) {
            if (mMessageId == message.getId()) {
                return;
            }

            reset();
            mMessageId = message.getId();
            mMessageType = getMessageType(message);
            if (message.hasBody()) {
                addMessage(message.getBody());
            }

            if (message.hasPhotos()) {
                addImages(message.getPhotos());
            }
        }

    }

    public static int getMessageType(Message message) {
        return message.getFromId().equals(VkManager.getUserId()) ? CURRENT_USER_MESSAGE :
                OTHER_USER_MESSAGE;
    }
}