# Pre-work - *ToDoList*

**ToDoList** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Felix Huang**

Time spent: **28** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] List anything else that you can get done to improve the app functionality! Please see the following section.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://imgur.com/a/Iyk43' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** Android provides good platform to make flexible and attractive layouts on smart phones. Android app on smart phone has limited space for displaying layouts compared to usual browser or applications on PC or MAC. Android provides rich resources to make GUI display, data processing, web access, and event-handling work together nicely and smoothly. For example, an adapter decouples data processing and GUI display, which is good for programming.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** RecyclerView is used to display the list of ToDo items, and RecyclerView.Adapter is used in adapter/CursorRecyclerViewApapter.java. An Adapter object acts as a bridge between an AdapterView and the underlying data for that view. The adapter decouples data processing and UI view, which is good for programming. The adapter also makes event-handling work more smoothly and effectively. According to https://developer.android.com/reference/android/widget/ArrayAdapter.html, RecyclerView offers similar features as ArrayAdapter with better performance and more flexibility than ListView provides. For example, onItemDismiss() in CursorRecyclerViewAdapter<ItemCursorAdapter.ViewHolder> supports swiping to remove an item and the items after it will roll up and display nicely. This app uses this onItemDismiss() in adapter/ItemCursorAdapter.java to support swiping to remove a ToDo item.

## Notes

Describe any challenges encountered while building the app.
The following issues required more work to get them right: (1) RecyclerView and RecyclerView.Adapter, (2) FloatingActionButton with anchor RecyclerView, (3) how to handle returned values from a DialogFragment properly and effectively, (4) to persist data by net.simonvt.schematic which automatically generates a ContentProvider backed by an SQLite database, (5) to do better code reuse and sharing by using a DialogFragment for both adding and editing ToDo items.

## License

    Copyright 2017 Felix Huang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.## ToDoList 

* To load it into Android studio, we can do 'Open File or Project' and pick ToDoList2/ToDoApp8.
* A user can click (+), a FloatingActionButton, and add a ToDo item. (+) is floating above the UI, and becomes hidden when the list is scrolled past first page. By using a floating add button, this app provides wider view for useful information - item content, due date, and priority.
* DialogFragment is used in main/ItemDialogFragment.java. By that, a user can add or edit a ToDo item. In this pop-up dialog, a user can edit item content, pick a due date (month, day), and select priority (low, medium, high).
* Spinner is used to enable users to pick month, day, and priority for ToDo items.
* A user can tap an item to edit it.
* After a user finishes adding/editing an item, soft keyboard goes away.
* To remove an item, a user can swipe right or swipe left on that item. RemovalDialog is implemented in adapter/ItemCursorAdapter.java to ask a user to confirm removal of an item.
* RecyclerView is used to display the list of ToDo items in main/ToDoListActivity.java and res/layout/activity_my_list.xml.
* [Question 2. Why do you think the adapter is important?] RecyclerView.Adapter is used in adapter/CursorRecyclerViewApapter.java. An Adapter object acts as a bridge between an AdapterView and the underlying data for that view. The adapter decouples data processing and UI view, which is good for programming. The adapter also makes event-handling work more smoothly and effectively. According to https://developer.android.com/reference/android/widget/ArrayAdapter.html, RecyclerView offers similar features as ArrayAdapter with better performance and more flexibility than ListView provides. For example, onItemDismiss() in CursorRecyclerViewAdapter<ItemCursorAdapter.ViewHolder> supports swiping to remove an item and the items after it will roll up and display nicely. This app uses this onItemDismiss() in adapter/ItemCursorAdapter.java to support swiping to remove a ToDo item.
* future work: I try to develop a financial investment app for stock, ETF, FOREX. Some features in this app can be used for stock price display, for example, RecyclerView and RecyclerView.Adapter.
* future work: Give users the option to sort the ToDo items by either date or priority.
* Java code is organized into main, adapter, data, service, and touch_helper.
* Data is persisted by net.simonvt.schematic as https://github.com/SimonVT/schematic . This automatically generates a ContentProvider backed by an SQLite database. By this, users can persist ToDo items and retrieve them properly on app restart.
The following are added to dependencies in build.gradle :
(1) apt 'net.simonvt.schematic:schematic-compiler:0.6.3',
(2) compile 'net.simonvt.schematic:schematic:0.6.3'.
* [Question 1. Compare Android's approach to layouts and other GUI.] Android provides good platform to make flexible and attractive layouts on smart phones. Android app on smart phone has limited space for displaying layouts compared to usual browser or applications on PC or MAC. Android provides rich resources to make GUI display, data processing, web access, and event-handling work together nicely and smoothly. For example, an adapter decouples data processing and GUI display, which is good for programming.
* [Question 3. Describe any challenges encountered.] The following issues required more work to get them right: (1) RecyclerView and RecyclerView.Adapter, (2) FloatingActionButton with anchor RecyclerView, (3) how to handle returned values from a DialogFragment properly and effectively, (4) to persist data by net.simonvt.schematic which automatically generates a ContentProvider backed by an SQLite database, (5) to do better code reuse and sharing by using a DialogFragment for both adding and editing ToDo items.
* README2.gif is created with LiceCap to illustrate the essential operations of this ToDoList app.

