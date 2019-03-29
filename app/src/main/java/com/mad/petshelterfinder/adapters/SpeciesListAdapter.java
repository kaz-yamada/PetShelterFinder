package com.mad.petshelterfinder.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.mad.petshelterfinder.R;
import com.mad.petshelterfinder.model.PetSpecies;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mad.petshelterfinder.util.StringUtils.capitaliseString;

/**
 * Recycler view to display a list of pet species
 */
public class SpeciesListAdapter extends RecyclerView.Adapter<SpeciesListAdapter.ViewHolder> {
    private ArrayList<PetSpecies> mSpeciesList;

    public SpeciesListAdapter() {
        mSpeciesList = new ArrayList<>();
    }

    /**
     * Add species to recycler view list
     *
     * @param species name of the species to display
     */
    public void addToList(String species) {
        mSpeciesList.add(new PetSpecies(species, true));
        notifyItemInserted(mSpeciesList.size());
    }

    /**
     * Get the list of checked items in the recycler view
     *
     * @return list of checked items including species name
     */
    public ArrayList<String> getCheckedItems() {
        ArrayList<String> speciesList = new ArrayList<>();

        for (PetSpecies species : mSpeciesList) {
            if (species.isChecked()) speciesList.add(species.getName());
        }

        return speciesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_species, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String species = mSpeciesList.get(position).getName();

        holder.mCheckBox.setChecked(mSpeciesList.get(position).isChecked());
        holder.mCheckBox.setText(capitaliseString(species));
    }

    @Override
    public int getItemCount() {
        return mSpeciesList.size();
    }

    /**
     * View holder class to display pet species
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.species_name_text_view)
        CheckBox mCheckBox;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mSpeciesList.get(getAdapterPosition()).setChecked(isChecked);
                }
            });
        }
    }
}
