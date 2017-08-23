package com.fhuang.android.todolist.main;

import com.fhuang.android.todolist.data.ItemC;

public interface ItemDialogListener {
    void onFinishItemDialog(ItemC a);
    void hideSoftKeyboard();
}
