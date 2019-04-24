package morano.pro.ahmedmohamed.betna;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<ServiceContent> mData;

    public RecyclerViewAdapter(Context context, List<ServiceContent> data) {
        mContext = context;
        mData = data;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.maintenance_service_item, parent, false);

        int width = parent.getMeasuredWidth() / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0,0, 16);
        view.setLayoutParams(params);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.imageViewg.setImageResource(mData.get(position).getServiceIcon());
        holder.textView.setText(mData.get(position).getServiceName());
        holder.serviceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DescriptionPageForMaintenace.class);
                intent.putExtra("Name", mData.get(position).getServiceName());
                intent.putExtra("Image", mData.get(position).getImageResourceId());
                intent.putExtra("Description", mData.get(position).getServiceDescription());
                intent.putExtra("Position", (position + 1));

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout serviceItem;
        ImageView imageViewg;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            serviceItem = (LinearLayout) itemView.findViewById(R.id.service_item);
            imageViewg = (ImageView) itemView.findViewById(R.id.image_id);
            textView = (TextView) itemView.findViewById(R.id.text_id);


        }
    }

}