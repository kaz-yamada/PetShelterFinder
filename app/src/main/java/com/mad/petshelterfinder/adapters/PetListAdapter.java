package com.mad.petshelterfinder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.model.Pet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Recyclerview adapter to display pets
 */
public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.ViewHolder> {

    private PetItemListener mItemListener;
    private Context mContext;
    private ArrayList<Pet> mPetList;

    private final static int IMAGE_SIZE = 220;

    /**
     * Constructor
     *
     * @param itemListener listener to handle row item clicks
     * @param context      the context the recycler view lives in
     */
    public PetListAdapter(PetItemListener itemListener, Context context) {
        mPetList = new ArrayList<>();
        mItemListener = itemListener;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet model = mPetList.get(position);

        holder.mNameTv.setText(model.getName());
        holder.mAgeTv.setText(String.format(mContext.getResources().getString(R.string.age_format), model.getAge()));
        holder.mBreedTv.setText(model.getBreed());
        holder.setStatus(model.getStatus());

        Picasso.get()
                .load(model.getImageUrl())
                .resize(IMAGE_SIZE, IMAGE_SIZE)
                .centerCrop()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mPetList.size();
    }

    /**
     * Get the pet id by it's position on the list
     *
     * @param position the position on the list to get the id from
     * @return the pet id
     */
    public String getPetItemId(int position) {
        return mPetList.get(position).getPetId();
    }

    /**
     * Add a pet object to the list
     *
     * @param pet the pet to add to the list
     */
    public void addPetItem(Pet pet) {
        mPetList.add(pet);
        notifyItemInserted(mPetList.size());
    }

    /**
     * Clear the recycler view list
     */
    public void resetList() {
        if (mPetList.size() > 0) {
            mPetList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * Removes pet item from list by unique id
     *
     * @param petId unique id to remove
     */
    public void removeItem(String petId) {
        for (int i = 0; i < mPetList.size(); i++) {
            if (mPetList.get(i).getPetId().equals(petId)) {
                mPetList.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    /**
     * View holder for recycler view
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.pet_name_text_view)
        TextView mNameTv;

        @BindView(R.id.pet_age_text_view)
        TextView mAgeTv;

        @BindView(R.id.pet_breed_text_view)
        TextView mBreedTv;

        @BindView(R.id.pet_status_text_view)
        TextView mStatusTv;

        @BindView(R.id.pet_image_view)
        ImageView mImageView;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mItemListener.itemLoaded();
            itemView.setOnClickListener(this);
        }

        void setStatus(String status) {
            int color = ContextCompat.getColor(mContext, R.color.colorOther);
            switch (status) {
                case "Available":
                    color = ContextCompat.getColor(mContext, R.color.colorAvailable);
                    break;
                case "Adopted":
                    color = ContextCompat.getColor(mContext, R.color.colorAdopted);
                    break;
                case "Foster":
                    color = ContextCompat.getColor(mContext, R.color.colorFoster);
                    break;
            }

            mStatusTv.setTextColor(color);
            mStatusTv.setText(status);
        }

        @Override
        public void onClick(View v) {
            mItemListener.onItemClick(getAdapterPosition());
        }
    }
}
