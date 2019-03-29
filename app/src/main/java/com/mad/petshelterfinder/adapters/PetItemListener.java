package com.mad.petshelterfinder.adapters;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 */
public interface PetItemListener {
    /**
     * Handles click on item row
     *
     * @param position the position of the clicked item
     */
    void onItemClick(int position);

    /**
     * Hides the placeholder.
     */
    void itemLoaded();
}
