package com.mad.petshelterfinder.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.model.Shelter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Recycler view adapter class to display list of shelters
 */
public class SheltersAdapter extends RecyclerView.Adapter<SheltersAdapter.ViewHolder> {

    private final ShelterItemListener mItemListener;
    private ArrayList<Shelter> mShelterList;

    public SheltersAdapter(ShelterItemListener itemListener) {
        mShelterList = new ArrayList<>();
        mItemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shelter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Shelter shelter = getItem(position);

        holder.mNameTv.setText(shelter.getName());
        holder.mAddressTv.setText(shelter.getFullAddress());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mItemListener) {
                    mItemListener.onShelterItemClick(shelter.getShelterId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShelterList.size();
    }

    /**
     * add shelter to recycler view
     *
     * @param item shelter object to add to the list
     */
    public void addShelterItem(Shelter item) {
        mShelterList.add(item);
    }

    /**
     * get shelter item by its position on the list
     *
     * @param position position on the list
     * @return a shelter object
     */
    private Shelter getItem(int position) {
        return mShelterList.get(position);
    }

    /**
     * Add a shelter object from the list by unique id
     *
     * @param shelterId id of the shelter to remove
     */
    public void removeItem(String shelterId) {
        for (int i = 0; i < mShelterList.size(); i++) {
            if (mShelterList.get(i).getShelterId().equals(shelterId)) {
                mShelterList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    /**
     * Removes all items from the list
     */
    public void resetList() {
        if (mShelterList.size() > 0) {
            mShelterList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * View holder class to display shelter items
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;

        @BindView(R.id.shelter_name_text_view)
        TextView mNameTv;

        @BindView(R.id.shelter_address_text_view)
        TextView mAddressTv;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
