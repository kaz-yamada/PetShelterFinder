package com.mad.petshelterfinder;

/**
 * Base view interface for the purposes of extension
 * @param <T>
 */
public interface BaseView<T> {
    /**
     * bind presenter to view
     * @param presenter the presenter to send and receive data to and from
     */
    void setPresenter(T presenter);
}
