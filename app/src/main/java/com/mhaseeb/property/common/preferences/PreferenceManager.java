package com.mhaseeb.property.common.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.mhaseeb.property.common.service.ApplicationContext;
import com.mhaseeb.property.common.service.ServiceLocator;

/**
 * Created by Mohammad.Haseeb on 9/7/2018
 */

public class PreferenceManager {

    private PreferenceManager() {

    }

    public static String PROPERTY_PREF = "propertyPrefs";

    private static PreferenceManager instance;
    private SharedPreferences sharedPref;

    //Constants
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PASSWORD = "password";
    private static final String PHONE_NO = "phoneNo";
    private static final String GENDER = "gender";
    private static final String IS_GUEST_LOGGED_IN = "isGuestLoggedIn";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    public static PreferenceManager getInstance() {
        if (instance == null) {
            synchronized (PreferenceManager.class) {

                if (instance == null)
                    instance = new PreferenceManager();
            }
        }
        return instance;

    }

    public void setUserSession(Context context, String firstName, String lastName, String email, String phoneNo, String gender) {

        sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.putString(EMAIL, email);
        editor.putString(PHONE_NO, phoneNo);
        editor.putString(GENDER, gender);
        editor.commit();
    }

    public void setId(Context context, String id) {

        sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ID, id);
        editor.commit();
    }

    public String getId(Context context) {
        try {
            sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);
            String id = sharedPref.getString(ID, "");
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setEmail(Context context, String email) {

        sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getEmail(Context context) {
        try {
            sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);
            String email = sharedPref.getString(EMAIL, "");
            return email;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFirstName(Context context) {
        try {
            sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);
            String firstName = sharedPref.getString(FIRST_NAME, "");
            return firstName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLastName(Context context) {
        try {
            sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);
            String lastName = sharedPref.getString(LAST_NAME, "");
            return lastName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPhoneNo(Context context) {
        try {
            sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);
            String phoneNo = sharedPref.getString(PHONE_NO, "");
            return phoneNo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean disposeAll(Context context) {
        sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        boolean result = editor.commit();
        return result;

    }

    public boolean disposeSpecific(Context context) {
        sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.remove("membershipID");
        editor.remove("membershipExpiryDate");
        editor.remove("userName");
        editor.remove("inputUserName");
        editor.remove("password");
        editor.remove("userID");
        editor.remove("memberName");
        editor.remove("type");
        editor.remove("imageBinary");
        editor.remove("city");
        editor.remove("constituency");
        editor.remove("memberSince");
        boolean result = editor.commit();
        return result;

    }


    public void setIsLoggedIn(Context context, boolean isLoggedIn) {

        sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean getIsLoggedIn() {
        try {
            sharedPref = ServiceLocator.getService(ApplicationContext.class).getContext().getSharedPreferences(PROPERTY_PREF, ServiceLocator.getService(ApplicationContext.class).getContext().MODE_PRIVATE);
            boolean isLoggedIn = sharedPref.getBoolean(IS_LOGGED_IN, false);
            return isLoggedIn;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void setIsGuestLogin(Context context, boolean isGuestLoggedIn) {

        sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_GUEST_LOGGED_IN, isGuestLoggedIn);
        editor.commit();
    }

    public boolean getIsGuestLoggedIn(Context context) {
        try {
            sharedPref = context.getSharedPreferences(PROPERTY_PREF, Context.MODE_PRIVATE);
            boolean isGuestLoggedIn = sharedPref.getBoolean(IS_GUEST_LOGGED_IN, false);
            return isGuestLoggedIn;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
