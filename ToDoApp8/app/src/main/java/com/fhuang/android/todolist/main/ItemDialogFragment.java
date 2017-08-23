package com.fhuang.android.todolist.main;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.fhuang.android.todolist.R;
import com.fhuang.android.todolist.adapter.ItemCursorAdapter;
import com.fhuang.android.todolist.data.ItemC;

//  Felix Huang  8-14-2017

public class ItemDialogFragment extends DialogFragment implements OnEditorActionListener {
    private EditText mEditText;

    public ItemDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static ItemDialogFragment newInstance(String title) {
        ItemDialogFragment frag = new ItemDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public String month = "Aug";
    class MonthOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            month = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            month = "Oct";
        }
    }

    public String day = "21";
    class DayOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            day = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            day = "21";
        }
    }

    public String priority = "Medium";
    class PriorityOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            priority = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            priority = "Medium";
        }
    }

    public ItemC inputEditItem = null;
    public static final int N_MONTHS = 12;
    public static final int N_DAYS = 31;
    public static final int N_PRI = 3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container);

        mEditText = (EditText) view.findViewById(R.id.etEditItem);
        String title = getArguments().getString("title", "Add an item");
        if (title.substring(0, 4).equals("Edit")) {
            inputEditItem = ItemCursorAdapter.itemSavedForEdit;
            mEditText.setText(inputEditItem.msg);
        } else { // Add
            mEditText.setText("");
            inputEditItem = null;
        }
        getDialog().setTitle(title);
        // Show soft keyboard automatically

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mEditText.setOnEditorActionListener((OnEditorActionListener) this);

        Spinner spMonth = (Spinner) view.findViewById(R.id.spMonth);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.month_array, android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spMonth
        spMonth.setAdapter(adapter);

        spMonth.setScrollBarSize(N_MONTHS);

        String initMonth = "Aug";
        if (inputEditItem != null) { // Edit
            initMonth = inputEditItem.dueDate.substring(0, 3);

        }

        for (int k = 0; k < N_MONTHS; ++k) {
            spMonth.setSelection(k);
            String s = spMonth.getSelectedItem().toString();
            if (s.equals(initMonth)) {
                break;
            }
        }

        spMonth.setOnItemSelectedListener(new MonthOnItemSelectedListener());

        Spinner spDay = (Spinner) view.findViewById(R.id.spDay);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.day_array, android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spDay
        spDay.setAdapter(adapter2);

        spDay.setScrollBarSize(N_DAYS);

        String initDay = "1";
        if (inputEditItem != null) { // Edit
            initDay = inputEditItem.dueDate.substring(4, inputEditItem.dueDate.length());
        }
        for (int k = 0; k < N_DAYS; ++k) {
            spDay.setSelection(k);
            String s = spDay.getSelectedItem().toString();
            if (s.equals(initDay)) {
                break;
            }
        }

        spDay.setOnItemSelectedListener(new DayOnItemSelectedListener());

        // priority spinner
        Spinner spPriority = (Spinner) view.findViewById(R.id.spPriority);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.priority_array, android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spPriority
        spPriority.setAdapter(adapter3);

        spPriority.setScrollBarSize(N_PRI);

        String initPri = "Medium";
        if (inputEditItem != null) { // Edit
            initPri = inputEditItem.priority;
        }
        for (int k = 0; k < N_PRI; ++k) {
            spPriority.setSelection(k);
            String s = spPriority.getSelectedItem().toString();
            if (s.equals(initPri)) {
                break;
            }
        }

        spPriority.setOnItemSelectedListener(new PriorityOnItemSelectedListener());

        Button btDone = (Button) view.findViewById(R.id.btDone);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_edit_done(v);
            }
        });
        return view;
    }

    // In this case, when the "Done" button is pressed
    // REQUIRES a 'soft keyboard' (virtual keyboard)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {

            ToDoListActivity.currActivity.hideSoftKeyboard();

            return true;
        }
        return false;
    }

    public void add_edit_done(View v) {
        ItemDialogListener listener = (ItemDialogListener) getActivity();
        String msg = mEditText.getText().toString();
        ItemC p = new ItemC(msg, month + " " + day, priority);

        listener.onFinishItemDialog(p);
        dismiss();
    }
}
