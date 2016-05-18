package makarov.vk.vkgroupchats.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import makarov.vk.vkgroupchats.R;
import makarov.vk.vkgroupchats.data.models.Message;
import makarov.vk.vkgroupchats.data.models.Photo;

public class MessagesAdapter extends RecyclerListAdapter<MessagesAdapter.ViewHolder, Message> {

    public MessagesAdapter(Context context) {
        super(context, new ArrayList<Message>());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Message message = getList().get(position);

        if (message.hasBody()) {
            holder.addMessage(message.getBody());
        }
//
//        if (message.hasPhotos()) {
//            holder.addImages(message.getPhotos());
//        }

        holder.itemView.setTag(message);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text) TextView text;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }

        public void addMessage(String body) {
//            TextView textView = new TextView(getContext());
//            textView.setLayoutParams(new
//                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT));
//
            text.setText(body);
//            ((ViewGroup) itemView).addView(textView);
        }

        public void addImages(List<Photo> photos) {
            for (Photo photo : photos) {
                ImageView imageView = new ImageView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                int imageSize = (int) getContext().getResources().getDimension(R.dimen.message_image_size);

                params.width = imageSize;
                params.height = imageSize;
                imageView.setLayoutParams(params);

                ((ViewGroup) itemView).addView(imageView);
                Picasso.with(getContext()).load(photo.getPhotoUrl()).into(imageView);
            }

        }
    }
}