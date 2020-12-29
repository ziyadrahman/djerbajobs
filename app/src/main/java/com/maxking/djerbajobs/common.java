package com.maxking.djerbajobs;

import android.util.Log;

public class common {
    String makeFirstLetterCap(String string)
    {
        try {
            return string.substring(0, 1).toUpperCase() + string.substring(1);
        }
        catch (NullPointerException e)
        {
            Log.d("tags","Null PointerException");
            return  e.getMessage();
        }


    }
}
