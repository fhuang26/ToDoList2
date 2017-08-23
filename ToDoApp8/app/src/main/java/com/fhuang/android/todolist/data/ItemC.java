package com.fhuang.android.todolist.data;

// to do item data from Add or Edit dialog fragment
public class ItemC {
	public String msg; // to do message
	public String dueDate;
	public String priority;
	public ItemC (String msg_in, String dueDate_in, String priority_in) {
		msg = msg_in;
		dueDate = dueDate_in;
		priority = priority_in;
	}
}

