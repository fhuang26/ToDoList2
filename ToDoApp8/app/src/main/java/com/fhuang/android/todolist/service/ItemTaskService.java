package com.fhuang.android.todolist.service;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.RemoteException;

import com.fhuang.android.todolist.adapter.Utils;
import com.fhuang.android.todolist.data.ItemC;
import com.fhuang.android.todolist.data.ItemColumns;
import com.fhuang.android.todolist.data.QuoteProvider;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ItemTaskService extends GcmTaskService{
  private String LOG_TAG = ItemTaskService.class.getSimpleName();

  private OkHttpClient client = new OkHttpClient();
  private Context mContext;
  private StringBuilder mStoredSymbols = new StringBuilder();
  private boolean isUpdate;
  String stockInput;

  public ItemTaskService(){}

  public ItemTaskService(Context context){
    mContext = context;
  }
  String fetchData(String url) throws IOException{
    Request request = new Request.Builder()
        .url(url)
        .build();

    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  @Override
  public int onRunTask(TaskParams params){
    Cursor initQueryCursor;
    if (mContext == null){
      mContext = this;
    }
    StringBuilder urlStringBuilder = new StringBuilder();
    try{
      // Base URL for the Yahoo query
      urlStringBuilder.append("https://query.yahooapis.com/v1/public/yql?q=");
      urlStringBuilder.append(URLEncoder.encode("select * from yahoo.finance.quotes where symbol "
        + "in (", "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    stockInput = ""; // used for add
    if (params.getTag().equals("init")){
      isUpdate = true;
      initQueryCursor = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
          new String[] { "Distinct " + ItemColumns.SYMBOL }, null,
          null, null);
      if (initQueryCursor == null || initQueryCursor.getCount() == 0){
        // Init task. Populates DB with items as below
        ArrayList<ItemC> itemArr = new ArrayList<>();
        ItemC p1 = new ItemC("codepath prework", "Aug 22", "High");
        itemArr.add(p1);
        ItemC p2 = new ItemC("pick up kid at 5pm", "Aug 25", "Medium");
        itemArr.add(p2);
        ItemC p3 = new ItemC("buy milk", "Aug 19", "Low");
        itemArr.add(p3);

        try {
          ArrayList<ContentProviderOperation> batchArr = Utils.itemsToContentVals(itemArr);
          mContext.getContentResolver().applyBatch(QuoteProvider.AUTHORITY, batchArr);
        } catch (RuntimeException | RemoteException | OperationApplicationException e) {
        }
        return GcmNetworkManager.RESULT_SUCCESS;
      } else if (initQueryCursor != null){
        DatabaseUtils.dumpCursor(initQueryCursor);
        initQueryCursor.moveToFirst();
        for (int i = 0; i < initQueryCursor.getCount(); i++){
          mStoredSymbols.append("\""+
              initQueryCursor.getString(initQueryCursor.getColumnIndex("symbol"))+"\",");
          initQueryCursor.moveToNext();
        }
        mStoredSymbols.replace(mStoredSymbols.length() - 1, mStoredSymbols.length(), ")");
        try {
          urlStringBuilder.append(URLEncoder.encode(mStoredSymbols.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      }
    } else if (params.getTag().equals("add")) {
      isUpdate = false;
    } else {
      isUpdate = true;
    }

    return GcmNetworkManager.RESULT_SUCCESS;
  }

}
