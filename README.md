## ToDoList 
This Android app helps users to maintain a list of ToDo items. To load it into Android studio, we can do 'Open File or Project' and pick ToDoList2/ToDoApp8.
* By FloatingActionButton, a user can click (+) and add a ToDo item.
* DialogFragment is used in main/ItemDialogFragment.java. By that, a user can add or edit a ToDo item. In this pop-up dialog, a user can edit item content, pick a date (month, day), and select priority (low, medium, high).
* Spinner is used to enable users to pick month, day, and priority for ToDo items.
* A user can click an item to begin to edit it.
* To remove an item, a user can swipe right or swipe left on that item. RemovalDialog is implemented in adapter/ItemCursorAdapter.java to ask a user to confirm removal of an item.
* RecyclerView is used to display the list of ToDo items in main/ToDoListActivity.java and res/layout/activity_my_list.xml.
* RecyclerView.Adapter is used in adapter/CursorRecyclerViewApapter.java.
* Java code is organized into main, adapter, data, service, and touch_helper.
* After a user finishes adding/editing an item, soft keyboard goes away.
* Data is persisted by net.simonvt.schematic as https://github.com/SimonVT/schematic . This automatically generates a ContentProvider backed by an SQLite database. By this, users can persist ToDo items and retrieve them properly on app restart.
The following are added to dependencies in build.gradle :
(1) apt 'net.simonvt.schematic:schematic-compiler:0.6.3',
(2) compile 'net.simonvt.schematic:schematic:0.6.3'
* README2.gif is created with LiceCap to walk through essential operations of this ToDoList app.

