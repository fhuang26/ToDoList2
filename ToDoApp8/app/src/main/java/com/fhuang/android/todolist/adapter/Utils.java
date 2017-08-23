package com.fhuang.android.todolist.adapter;

import android.content.ContentProviderOperation;

import com.fhuang.android.todolist.data.ItemC;
import com.fhuang.android.todolist.data.ItemColumns;
import com.fhuang.android.todolist.data.QuoteProvider;

import java.util.ArrayList;

public class Utils {

  private static String LOG_TAG = Utils.class.getSimpleName();

  public static boolean showPercent = true;

  public static ArrayList itemsToContentVals(ArrayList<ItemC> itemArr) {
    ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
    for (int i = 0; i < itemArr.size(); i++){
      ItemC p = itemArr.get(i);
      batchOperations.add(buildBatchOperation2(p));
    }
    return batchOperations;
  }

  public static ContentProviderOperation buildBatchOperation2(ItemC p){
    ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
        QuoteProvider.Quotes.CONTENT_URI);
    builder.withValue(ItemColumns.SYMBOL, p.msg);
    builder.withValue(ItemColumns.BIDPRICE, p.dueDate);
    builder.withValue(ItemColumns.PERCENT_CHANGE, p.priority);
    builder.withValue(ItemColumns.CHANGE, p.priority);
    builder.withValue(ItemColumns.ISCURRENT, 1);
    builder.withValue(ItemColumns.ISUP, 1);

    return builder.build();
  }
}
