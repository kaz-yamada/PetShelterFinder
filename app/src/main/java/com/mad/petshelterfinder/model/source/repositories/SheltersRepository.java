package com.mad.petshelterfinder.model.source.repositories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.petshelterfinder.model.Shelter;
import com.mad.petshelterfinder.model.source.SheltersDataSource;

import static com.mad.petshelterfinder.util.Constants.SHELTERS_KEY;

/**
 * Class to query the shelter table from Firebase
 */
public class SheltersRepository implements SheltersDataSource {
    private static SheltersRepository sInstance = null;
    private static FirebaseDatabase sFirebaseInstance = FirebaseDatabase.getInstance();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private SheltersRepository() {
    }

    /**
     * Create and return a new instance of the firebase model class
     *
     * @return new instance of firebase model class
     */
    public static SheltersRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SheltersRepository();
        }
        return sInstance;
    }

    @Override
    public void getShelters(@NonNull final LoadShelterListCallback callback) {
        DatabaseReference ref = sFirebaseInstance.getReference(SHELTERS_KEY);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Shelter shelter = dataSnapshot.getValue(Shelter.class);
                if (shelter != null) {
                    shelter.setShelterId(dataSnapshot.getKey());
                    callback.onSheltersLoaded(shelter);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                callback.onShelterRemoved(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled();
            }
        });

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onLoadFinish((int) dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled();
            }
        });
    }

    @Override
    public void getShelterById(String shelterId, @NonNull final GetShelterByIdCallback callback) {
        DatabaseReference ref = sFirebaseInstance.getReference(SHELTERS_KEY + shelterId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Shelter shelter = dataSnapshot.getValue(Shelter.class);
                if (shelter != null) {
                    shelter.setShelterId(dataSnapshot.getKey());
                    callback.onLoaded(shelter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled();
            }
        });
    }
}
