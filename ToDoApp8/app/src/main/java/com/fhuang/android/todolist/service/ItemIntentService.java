package com.fhuang.android.todolist.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.TaskParams;


public class ItemIntentService extends IntentService {

  public ItemIntentService(){
    super(ItemIntentService.class.getName());
  }

  public ItemIntentService(String name) {
    super(name);
  }

  @Override protected void onHandleIntent(Intent intent) {
    ItemTaskService itemTaskService = new ItemTaskService(this);
    Bundle args = new Bundle();
    if (intent.getStringExtra("tag").equals("add")){
      args.putString("symbol", intent.getStringExtra("symbol"));
    }
    // We can call OnRunTask from the intent service to force it to run immediately instead of
    // scheduling a task.
    itemTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
  }
}
