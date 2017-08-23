package com.fhuang.android.todolist.main;

import android.app.LoaderManager;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.fhuang.android.todolist.R;
import com.fhuang.android.todolist.adapter.ItemCursorAdapter;
import com.fhuang.android.todolist.adapter.RecyclerViewItemClickListener;
import com.fhuang.android.todolist.adapter.Utils;
import com.fhuang.android.todolist.data.ItemC;
import com.fhuang.android.todolist.data.ItemColumns;
import com.fhuang.android.todolist.data.QuoteProvider;
import com.fhuang.android.todolist.service.ItemIntentService;
import com.fhuang.android.todolist.touch_helper.SimpleItemTouchHelperCallback;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

//  Felix Huang  8-14-2017

public class ToDoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ItemDialogListener {

  private CharSequence mTitle;
  private Intent mServiceIntent;
  private ItemTouchHelper mItemTouchHelper;
  private static final int CURSOR_LOADER_ID = 0;
  private ItemCursorAdapter mCursorAdapter;
  private Context mContext;
  private Cursor mCursor;
  private View flToDoList;
  boolean isConnected;
  private ConnectivityManager cm;
  public static ToDoListActivity currActivity;
  public NetworkInfo activeNetwork;
  public boolean isNetworkConnected() {
    activeNetwork = cm.getActiveNetworkInfo();
    boolean flagConnected = (activeNetwork != null) && activeNetwork.isConnectedOrConnecting();
    return flagConnected;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContext = this;
    currActivity = this;
    cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

    // activeNetwork = cm.getActiveNetworkInfo();
    isConnected = isNetworkConnected();
    setContentView(R.layout.activity_my_list);
    // The intent service is for executing immediate pulls from the Yahoo API
    // GCMTaskService can only schedule tasks, they cannot execute immediately
    mServiceIntent = new Intent(this, ItemIntentService.class);
    if (savedInstanceState == null){
      // Run the initialize task service so that some stocks appear upon an empty database
      mServiceIntent.putExtra("tag", "init");
      if (isConnected){
        startService(mServiceIntent);
      } else{
        networkToast();
      }
    }

    flToDoList = (View) findViewById(R.id.flToDoList);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

    mCursorAdapter = new ItemCursorAdapter(this, null);
    recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this,
            new RecyclerViewItemClickListener.OnItemClickListener() {
              @Override
              public void onItemClick(View v, int position) {
                // String msg = Integer.toString(position);
                // Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                mCursorAdapter.saveItemForEdit(mContext, v, position);
                FragmentManager fm = getSupportFragmentManager();
                ItemDialogFragment itemDialogFrag = ItemDialogFragment.newInstance("Edit an Item");
                itemDialogFrag.show(fm, "fragment_item");
              }
            }));
    recyclerView.setAdapter(mCursorAdapter);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.attachToRecyclerView(recyclerView);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        isConnected = currActivity.isNetworkConnected();
        if (isConnected){
          FragmentManager fm = getSupportFragmentManager();
          ItemDialogFragment itemDialogFrag = ItemDialogFragment.newInstance("Add an Item");
          itemDialogFrag.show(fm, "fragment_item");
        } else {
          networkToast();
        }

      }
    });

    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mCursorAdapter);
    mItemTouchHelper = new ItemTouchHelper(callback);
    mItemTouchHelper.attachToRecyclerView(recyclerView);

    mTitle = getTitle();
    isConnected = isNetworkConnected();
  }

  @Override
  public void hideSoftKeyboard() {
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(flToDoList.getWindowToken(), 0);
  }

  @Override
  public void onFinishItemDialog(ItemC p) {
    // String item = p.msg + " + " + p.dueDate + " + " + p.priority;
    // Toast.makeText(this, item, Toast.LENGTH_LONG).show();
    ArrayList<ItemC> itemArr = new ArrayList<>();
    itemArr.add(p);
    try {

      ArrayList<ContentProviderOperation> batchArr = Utils.itemsToContentVals(itemArr);
      mContext.getContentResolver().applyBatch(QuoteProvider.AUTHORITY, batchArr);
    } catch (RuntimeException | RemoteException | OperationApplicationException e) {

    }

  }

  @Override
  public void onResume() {
    super.onResume();
    getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
  }

  public void networkToast(){
    Toast.makeText(mContext, getString(R.string.network_toast), Toast.LENGTH_SHORT).show();
  }

  public void restoreActionBar() {
    ActionBar actionBar = getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.setTitle(mTitle);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.my_stocks, menu);
      restoreActionBar();
      return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args){
    // This narrows the return to only the stocks that are most current.
    return new CursorLoader(this, QuoteProvider.Quotes.CONTENT_URI,
        new String[]{ ItemColumns._ID, ItemColumns.SYMBOL, ItemColumns.BIDPRICE,
            ItemColumns.PERCENT_CHANGE, ItemColumns.CHANGE, ItemColumns.ISUP},
        ItemColumns.ISCURRENT + " = ?",
        new String[]{"1"},
        null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data){
    mCursorAdapter.swapCursor(data);
    mCursor = data;
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader){
    mCursorAdapter.swapCursor(null);
  }

}
