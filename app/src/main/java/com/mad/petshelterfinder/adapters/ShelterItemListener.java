package com.mad.petshelterfinder.adapters;

/**
 * Interface to handle listening on click methods for recycler view items
 */
public interface ShelterItemListener {
    /**
     * Passes shelter id from recycler view row
     *
     * @param shelterId the unique key of the row
     */
    void onShelterItemClick(String shelterId);
}
