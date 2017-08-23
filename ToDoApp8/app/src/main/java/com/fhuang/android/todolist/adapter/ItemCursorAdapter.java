package com.fhuang.android.todolist.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fhuang.android.todolist.R;
import com.fhuang.android.todolist.data.ItemC;
import com.fhuang.android.todolist.data.ItemColumns;
import com.fhuang.android.todolist.data.QuoteProvider;
import com.fhuang.android.todolist.main.ToDoListActivity;
import com.fhuang.android.todolist.touch_helper.ItemTouchHelperAdapter;
import com.fhuang.android.todolist.touch_helper.ItemTouchHelperViewHolder;

public class ItemCursorAdapter extends CursorRecyclerViewAdapter<ItemCursorAdapter.ViewHolder>
    implements ItemTouchHelperAdapter{

  private static Context mContext;
  private static Typeface robotoLight;
  private boolean isPercent;
  public ItemCursorAdapter(Context context, Cursor cursor){
    super(context, cursor);
    mContext = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    robotoLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_quote, parent, false);
    ViewHolder vh = new ViewHolder(itemView);
    return vh;
  }

  @Override
  public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor){
    viewHolder.symbol.setText(cursor.getString(cursor.getColumnIndex("symbol")));
    viewHolder.bidPrice.setText(cursor.getString(cursor.getColumnIndex("bid_price")));
    int sdk = Build.VERSION.SDK_INT;
    if (cursor.getInt(cursor.getColumnIndex("is_up")) == 1){
      if (sdk < Build.VERSION_CODES.JELLY_BEAN){
        viewHolder.change.setBackgroundDrawable(
            mContext.getResources().getDrawable(R.drawable.percent_change_pill_purple));
      }else {
        viewHolder.change.setBackground(
            mContext.getResources().getDrawable(R.drawable.percent_change_pill_purple));
      }
    } else{
      if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
        viewHolder.change.setBackgroundDrawable(
            mContext.getResources().getDrawable(R.drawable.percent_change_pill_purple));
      } else{
        viewHolder.change.setBackground(
            mContext.getResources().getDrawable(R.drawable.percent_change_pill_purple));
      }
    }
    if (Utils.showPercent){
      viewHolder.change.setText(cursor.getString(cursor.getColumnIndex("percent_change")));
    } else{
      viewHolder.change.setText(cursor.getString(cursor.getColumnIndex("change")));
    }
  }

  public int _removal_index = 0;
  public boolean removalDialog (int index)
  {
    _removal_index = index;
    AlertDialog.Builder ad = new AlertDialog.Builder(ToDoListActivity.currActivity);
    ad.setTitle("Removal Prompt");

    ad.setMessage(R.string.AlertDialogMsg);

    ad.setPositiveButton(
            "Yes",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int arg1) {
                int position = _removal_index;
                Cursor c = getCursor();
                c.moveToPosition(position);
                String item_msg = c.getString(c.getColumnIndex(ItemColumns.SYMBOL));
                mContext.getContentResolver().delete(QuoteProvider.Quotes.withSymbol(item_msg), null, null);
                notifyItemRemoved(position);
              }
            }
    );

    ad.setNegativeButton(
            "No",
            new DialogInterface.OnClickListener(){
              public void onClick(DialogInterface dialog, int arg1) {
                ContentValues contentValues = new ContentValues();

                contentValues.put(ItemColumns.ISCURRENT, 1);
                mContext.getContentResolver().update(QuoteProvider.Quotes.CONTENT_URI, contentValues,
                          null, null);
              }
            }
    );

    ad.show();
    return false;
  }

  // A user swipes on an item to remove it.
  @Override public void onItemDismiss(int position) {
    removalDialog(position);
  }

  public static ItemC itemSavedForEdit = null;
  public void saveItemForEdit(Context context, View v, int position) {
    v.setBackgroundColor(0);
    Cursor c = getCursor();
    c.moveToPosition(position);
    String item_msg = c.getString(c.getColumnIndex(ItemColumns.SYMBOL));
    String dueDate = c.getString(c.getColumnIndex(ItemColumns.BIDPRICE));
    String priority = c.getString(c.getColumnIndex(ItemColumns.PERCENT_CHANGE));
    itemSavedForEdit = new ItemC(item_msg, dueDate, priority);
    context.getContentResolver().delete(QuoteProvider.Quotes.withSymbol(item_msg), null, null);
    notifyItemRemoved(position);
  }

  @Override public int getItemCount() {
    return super.getItemCount();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder
      implements ItemTouchHelperViewHolder, View.OnClickListener{
    public final TextView symbol;
    public final TextView bidPrice;
    public final TextView change;
    public ViewHolder(View itemView){
      super(itemView);
      symbol = (TextView) itemView.findViewById(R.id.stock_symbol);
      symbol.setTypeface(robotoLight);
      bidPrice = (TextView) itemView.findViewById(R.id.bid_price);
      change = (TextView) itemView.findViewById(R.id.change);
    }

    @Override
    public void onItemSelected(){
      itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear(){
      itemView.setBackgroundColor(0);
    }

    @Override
    public void onClick(View v) {

    }
  }
}
