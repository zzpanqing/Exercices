package com.qing.xyzreader.xyzreader2.data;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

/**
 * Created by qing on 13/02/17.
 */
public class ArticleLoader extends CursorLoader{
    private ArticleLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, ItemsContract.Items.DEFAULT_SORT);
    }


    public  interface Query {
        String[] PROJECTION = {
          ItemContract.Items.ID,
        };
    }
}
