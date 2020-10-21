package br.ufc.quixada.dsdm.meempresta.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class OfflineUser {

    private final SharedPreferences mSharedPreferences;

    public OfflineUser(Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences(DBCollections.USER_COLLECTION, mContext.MODE_PRIVATE);
    }

   public void storeString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
   }

   public String getString(String key) {
       return mSharedPreferences.getString(key, null);
   }
}
