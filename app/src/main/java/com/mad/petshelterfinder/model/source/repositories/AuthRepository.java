package com.mad.petshelterfinder.model.source.repositories;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.petshelterfinder.model.User;
import com.mad.petshelterfinder.model.source.AuthDataSource;
import com.mad.petshelterfinder.model.source.PetsDataSource;
import com.mad.petshelterfinder.model.source.SheltersDataSource;

import static com.mad.petshelterfinder.util.Constants.USERS_KEY;
import static com.mad.petshelterfinder.util.Constants.USER_PETS_KEY;
import static com.mad.petshelterfinder.util.Constants.USER_SHELTERS_KEY;

/**
 * Helper class to handle operations relating to
 */
public class AuthRepository implements AuthDataSource {
    private static AuthRepository sInstance = null;
    private static final FirebaseAuth sFirebaseAuth = FirebaseAuth.getInstance();
    private static FirebaseDatabase sFirebaseDatabase = FirebaseDatabase.getInstance();
    private static FirebaseUser sFirebaseUser;

    private static final String ERROR_MESSAGE = "Error logging in";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private AuthRepository() {

    }

    /**
     * Create and return a new instance of the firebase auth helper class
     *
     * @return new instance of helper class
     */
    public static AuthRepository getInstance() {
        if (sInstance == null) {
            sInstance = new AuthRepository();
        }
        return sInstance;
    }

    /**
     * Get firebase user's email, return empty string if not logged in
     *
     * @return user's email
     */
    public static String getEmail() {
        String email = "";

        if (isLoggedIn()) {
            email = getUser().getEmail();
        }

        return email;
    }

    /**
     * Check if firebase user is logged in
     *
     * @return true if firebase user exists and is logged in, false other wise
     */
    public static boolean isLoggedIn() {
        return getUser() != null;
    }

    /**
     * Get instance of firebase user
     *
     * @return instance of the firebase user
     */
    private static FirebaseUser getUser() {
        if (sFirebaseUser == null && sFirebaseAuth.getCurrentUser() == null) {
            sFirebaseUser = sFirebaseAuth.getCurrentUser();
        }
        return sFirebaseUser;
    }

    private void writeNewUser(String userId, String email) {
        User newUser = new User(email);
        sFirebaseDatabase.getReference(USERS_KEY).child(userId).setValue(newUser);
    }

    @Override
    public void updateUserFavouritePetById(String petId, boolean newValue) {
        if (sFirebaseUser != null) {
            String userId = sFirebaseUser.getUid();
            sFirebaseDatabase
                    .getReference(USER_PETS_KEY)
                    .child(userId)
                    .child(petId)
                    .setValue(newValue);
        }
    }


    @Override
    public void updateUserShelterById(String shelterId, boolean newValue) {
        if (sFirebaseUser != null) {
            String userId = sFirebaseUser.getUid();
            sFirebaseDatabase
                    .getReference(USER_SHELTERS_KEY)
                    .child(userId)
                    .child(shelterId)
                    .setValue(newValue);
        }
    }

    @Override
    public void loginUser(String email, String password, final AuthCallbacks callback) {
        sFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sFirebaseUser = sFirebaseAuth.getCurrentUser();
                            callback.onSuccess();
                        } else if (task.getException() != null) {
                            callback.onFailure(task.getException().getMessage());
                        } else {
                            callback.onFailure(ERROR_MESSAGE);
                        }
                    }
                });
    }

    @Override
    public void isPetFavouritedByUser(String petId, final GetFavouriteStatusCallback callback) {
        sFirebaseDatabase.getReference(USER_PETS_KEY)
                .child(getUser().getUid())
                .child(petId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean result = false;
                if (dataSnapshot.getValue() != null) {
                    result = (boolean) dataSnapshot.getValue();
                }

                callback.onLoad(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void isShelterSavedByUser(String shelterId, final GetFavouriteStatusCallback callback) {
        sFirebaseDatabase
                .getReference(USER_SHELTERS_KEY)
                .child(getUser().getUid())
                .child(shelterId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean result = false;
                        if (dataSnapshot.getValue() != null) {
                            result = (boolean) dataSnapshot.getValue();
                        }

                        callback.onLoad(result);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void getUserFavouritePetList(final PetsDataSource.GetPetByIdCallback callback) {
        sFirebaseDatabase.getReference(USER_PETS_KEY)
                .child(getUser().getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int count = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if ((boolean) snapshot.getValue()) {
                                count++;
                                PetsRepository.getInstance().getPetById(snapshot.getKey(), callback);
                            }
                        }


                        if (count == 0) {
                            callback.noRecords();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void getUserFavouriteShelters(final SheltersDataSource.GetShelterByIdCallback callback) {
        sFirebaseDatabase.getReference(
                USER_SHELTERS_KEY)
                .child(getUser().getUid())
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int count = 0;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if ((boolean) snapshot.getValue()) {
                                        count++;
                                        SheltersRepository.getInstance().getShelterById(snapshot.getKey(), callback);
                                    }
                                }

                                if (count == 0) {
                                    callback.noRecords();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
    }

    @Override
    public void logout() {
        sFirebaseAuth.signOut();
        sFirebaseUser = sFirebaseAuth.getCurrentUser();
    }

    @Override
    public void registerUser(String email, String password, final AuthCallbacks callback) {
        sFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sFirebaseUser = sFirebaseAuth.getCurrentUser();
                            callback.onSuccess();
                            writeNewUser(sFirebaseUser.getUid(), sFirebaseUser.getEmail());
                        } else if (task.getException() != null) {
                            callback.onFailure(task.getException().getMessage());
                        } else {
                            callback.onFailure(ERROR_MESSAGE);
                        }
                    }
                });
    }
}
