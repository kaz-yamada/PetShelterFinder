package com.mad.petshelterfinder.model.source.repositories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.petshelterfinder.model.Pet;
import com.mad.petshelterfinder.model.Shelter;
import com.mad.petshelterfinder.model.source.PetsDataSource;

import java.util.ArrayList;

import static com.mad.petshelterfinder.util.Constants.PETS_KEY;
import static com.mad.petshelterfinder.util.Constants.SHELTERS_KEY;

/**
 * Class to query pets table in the database
 */
public class PetsRepository implements PetsDataSource {

    private static PetsRepository sInstance = null;
    private static FirebaseDatabase sFirebaseInstance = FirebaseDatabase.getInstance();
    private static DatabaseReference sDatabaseReference = sFirebaseInstance.getReference();

    private static final String SPECIES_KEY = "species";

    private final static int CHAR_INDEX = 1;

    /**
     * Constructor
     */
    private PetsRepository() {
    }

    /**
     * Return static instance of this class
     *
     * @return new instance of the class
     */
    public static PetsRepository getInstance() {
        if (sInstance == null) {
            sInstance = new PetsRepository();
        }
        return sInstance;
    }

    @Override
    public void getPetList(final LoadPetListCallback callback) {
        DatabaseReference ref = sFirebaseInstance.getReference(PETS_KEY);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Pet pet = dataSnapshot.getValue(Pet.class);
                if (pet != null) {
                    pet.setPetId(dataSnapshot.getKey());
                    callback.onPetLoaded(pet);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                callback.onPetRemoved(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled();
            }
        });

        // Fires after all of rows from table are fetched
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onLoadFinished();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled();
            }
        });
    }

    @Override
    public void getPetById(final String petId, final GetPetByIdCallback callback) {
        DatabaseReference ref = sFirebaseInstance.getReference(PETS_KEY + petId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pet pet = dataSnapshot.getValue(Pet.class);
                if (pet != null) {
                    pet.setPetId(petId);
                    getPetShelterById(pet, callback);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled();
            }
        });
    }

    @Override
    public void getSpeciesList(final GetSpeciesCallback callback) {
        DatabaseReference ref = sFirebaseInstance.getReference(PETS_KEY);
        ref.orderByChild(SPECIES_KEY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> speciesList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pet pet = snapshot.getValue(Pet.class);
                    if (pet != null && !speciesList.contains(pet.getSpecies())) {
                        speciesList.add(pet.getSpecies());
                    }
                }
                callback.onLoaded(speciesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Get the shelter the pet belongs to
     *
     * @param pet      Pet object to get the shelter from
     * @param callback callback to handle firebase events
     */
    private void getPetShelterById(final Pet pet, final GetPetByIdCallback callback) {
        DatabaseReference shelterRef = sDatabaseReference.child(SHELTERS_KEY + pet.getShelterId());
        shelterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Shelter shelter = dataSnapshot.getValue(Shelter.class);
                if (shelter != null) {
                    String shelterName = shelter.getName();
                    callback.onLoaded(pet, shelterName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled();
            }
        });
    }
}
