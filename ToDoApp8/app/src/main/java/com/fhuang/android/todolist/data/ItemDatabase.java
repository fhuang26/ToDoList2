package com.fhuang.android.todolist.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = ItemDatabase.VERSION)
public class ItemDatabase {
  private ItemDatabase(){}

  public static final int VERSION = 7;

  @Table(ItemColumns.class) public static final String QUOTES = "quotes";
}
