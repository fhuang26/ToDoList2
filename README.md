## ToDoList 
This Android app helps users to maintain a list of ToDo items. To load it into Android studio, we can do 'open file or project' and pick ToDoList2/ToDoApp8.
* By FloatingActionButton, a user can click + and add a ToDo item.
* DialogFragment is used in ItemDialogFragment class. By that, a user can add or edit a ToDo item. In this pop-up dialog, a user can edit item content, pick a date (month, day), and select priority (low, medium, high).
* Spinner is used to enable users to pick month, day, priority for ToDo items.
* A user can click an item to begin to edit it.
* To remove an item, a user can swipe right or swipe left on an item. RemovalDialog is implemented in ItemCursorAdapter class to ask a user to confirm removal of an item.
* Data is persisted by net.simonvt.schematic as https://github.com/SimonVT/schematic 
The following are added to dependencies in build.gradle :
(1) apt 'net.simonvt.schematic:schematic-compiler:0.6.3',
(2) compile 'net.simonvt.schematic:schematic:0.6.3'
* README2.gif is recorded by 
