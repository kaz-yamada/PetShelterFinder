package com.mad.petshelterfinder.util;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import com.mad.petshelterfinder.R;

/**
 * Static classes to help set up app menus
 */
public class MenuUtils {

    /**
     * Sets up handler for hamburger menu click
     *
     * @param drawerLayout the layout that contains the toolbar with hamburger menu
     * @param itemId       the id of the clicked item
     * @return return false to continue menu processing or true to consume it here
     */
    public static boolean setUpDrawerButton(DrawerLayout drawerLayout, int itemId) {
        switch (itemId) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return false;
    }


    /**
     * Sets up drawer menu items
     *
     * @param navigationView drawer menu to modify
     * @param email          user's login email
     * @param isUser         checks if user is logged in
     */
    public static void setNavigationHeader(NavigationView navigationView, String email, boolean isUser) {
        int menuId = R.menu.guest_menu;

        if (isUser) {
            View header = navigationView.getHeaderView(0);
            TextView textView = header.findViewById(R.id.nav_header_text_view);
            textView.setText(email);
            menuId = R.menu.user_menu;
        }

        if (navigationView.getMenu().size() > 0) {
            navigationView.getMenu().clear();
        }

        navigationView.inflateMenu(menuId);
    }
}
