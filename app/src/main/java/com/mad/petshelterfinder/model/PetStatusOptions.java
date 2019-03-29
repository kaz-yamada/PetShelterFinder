package com.mad.petshelterfinder.model;

import java.io.Serializable;

public enum PetStatusOptions implements Serializable {
    /**
     * Available for adoption at shelter
     */
    AVAILABLE,

    /**
     * Pet is in foster care
     */
    FOSTER_CARE,

    /**
     * Pet has been adopted
     */
    ADOPTED
}
