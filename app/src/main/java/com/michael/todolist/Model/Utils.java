package com.michael.todolist.Model;

import android.content.Context;
import android.content.res.TypedArray;

import com.michael.todolist.R;

/**
 * Created by Michael on 2016/9/5.
 */
public class Utils {

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return toolbarHeight;
    }
}
