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

public class MessagesAdapter extends BaseAdapter {

    List<Message> mList = new ArrayList<>();
    Context mContext;

    public MessagesAdapter(Context context) {
        super();
        mContext = context;
    }

    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Message getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (convertView == null) {
            if (getItemViewType(i) == 0) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.right_message_item, viewGroup, false);
            } else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.left_message_item, viewGroup, false);
            }
            viewHolder = new ViewHolder((ViewGroup) convertView.findViewById(R.id.message_container));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Message message = mList.get(i);
        viewHolder.setMessage(message);

        return convertView;
    }

    public void addItems(List<Message> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    private static class ViewHolder {

        private final ViewGroup mParentView;
        private int mMessageId;

        ViewHolder(ViewGroup parentView) {
            mParentView = (ViewGroup) parentView;
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
            if (message.hasBody()) {
                addMessage(message.getBody());
            }

            if (message.hasPhotos()) {
                addImages(message.getPhotos());
            }
        }

    }
}