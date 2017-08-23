## ToDoList 
##### Felix Huang
This Android app helps users to maintain a list of ToDo items. To load it into Android studio, we can do 'Open File or Project' and pick ToDoList2/ToDoApp8.
* A user can click (+), a FloatingActionButton, and add a ToDo item. (+) is floating above the UI, and becomes hidden when the list is scrolled past first page. By using a floating add button, this app provides wider view for useful information - item content, due date, and priority.
* DialogFragment is used in main/ItemDialogFragment.java. By that, a user can add or edit a ToDo item. In this pop-up dialog, a user can edit item content, pick a due date (month, day), and select priority (low, medium, high).
* Spinner is used to enable users to pick month, day, and priority for ToDo items.
* A user can tap an item to edit it.
* After a user finishes adding/editing an item, soft keyboard goes away.
* To remove an item, a user can swipe right or swipe left on that item. RemovalDialog is implemented in adapter/ItemCursorAdapter.java to ask a user to confirm removal of an item.
* RecyclerView is used to display the list of ToDo items in main/ToDoListActivity.java and res/layout/activity_my_list.xml.
* [Question 2. Why do you think the adapter is important?] RecyclerView.Adapter is used in adapter/CursorRecyclerViewApapter.java. An Adapter object acts as a bridge between an AdapterView and the underlying data for that view. The adapter decouples data processing and UI view, which is good for programming. The adapter also makes event-handling work more smoothly and effectively. According to https://developer.android.com/reference/android/widget/ArrayAdapter.html, RecyclerView offers similar features as ArrayAdapter with better performance and more flexibility than ListView provides. For example, onItemDismiss() in CursorRecyclerViewAdapter<-> supports swiping to remove an item and the items after it will roll up and display nicely. This app uses this onItemDismiss() in adapter/ItemCursorAdapter.java to support swiping to remove a ToDo item.
* future work: I try to develop a financial investment app for stock, ETF, FOREX. Some features in this app can be used for stock price display, for example, RecyclerView and RecyclerView.Adapter.
* future work: Give users the option to sort the ToDo items by either date or priority.
* Java code is organized into main, adapter, data, service, and touch_helper.
* Data is persisted by net.simonvt.schematic as https://github.com/SimonVT/schematic . This automatically generates a ContentProvider backed by an SQLite database. By this, users can persist ToDo items and retrieve them properly on app restart.
The following are added to dependencies in build.gradle :
(1) apt 'net.simonvt.schematic:schematic-compiler:0.6.3',
(2) compile 'net.simonvt.schematic:schematic:0.6.3'.
* [Question 1. Compare Android's approach to layouts and other GUI.] Android provides good platform to make flexible and attractive layouts on smart phones. Android app on smart phone has limited space for displaying layouts compared to usual browser or applications on PC or MAC. Android provides rich resources to make GUI display, data processing, web access, and event-handling work together nicely and smoothly. For example, an adapter decouples data processing and GUI display, which is good for programming.
* [Question 3. Describe any challenges encountered.] The following issues required more work to get them right: (1) RecyclerView and RecyclerView.Adapter, (2) FloatingActionButton with anchor RecyclerView, (3) how to handle returned values from a DialogFragment properly and effectively, (4) to persist data by net.simonvt.schematic which is backed by an SQLite database, (5) to do better code reuse and sharing by using a DialogFragment for both adding and editing ToDo items.
* README2.gif is created with LiceCap to illustrate the essential operations of this ToDoList app.

