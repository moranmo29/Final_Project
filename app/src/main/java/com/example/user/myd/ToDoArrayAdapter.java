package com.example.user.myd;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 23/03/2017.
 */

public class ToDoArrayAdapter extends ArrayAdapter<ItemToDo> {

    private final Context context;
    private final ItemToDo[] values;
    private final ArrayAdapter adapter;
    private final int P_HIGH = 1, P_MEDIUM = 2;

    public ToDoArrayAdapter(Context context, ItemToDo[] values) {
        super(context, R.layout.row_todo_item, values);
        this.context = context;
        this.values = values;
        this.adapter = this;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_todo_item, parent, false);
        TextView textDescriptionTodo = (TextView) rowView.findViewById(R.id.tv_toDoDescription);
        TextView textPriorityToDo = (TextView) rowView.findViewById(R.id.toDoPr);
        Button toDoPriority = (Button) rowView.findViewById(R.id.toDoPr); //
        CheckBox deleteToDo = (CheckBox) rowView.findViewById(R.id.checkBoxTodo);

        textDescriptionTodo.setText(values[position].getTitle());
        //textPriorityToDo.setText(""+values[position].getPriority());
        if(values[position].getPriority() == P_HIGH)
        {
            //set background drawable of a view
            toDoPriority.setBackgroundResource(R.drawable.icon_high_priority);
            textPriorityToDo.setText("גבוה");
        }
        else if(values[position].getPriority() == P_MEDIUM)
        {
            toDoPriority.setBackgroundResource(R.drawable.icon_medium_priority);
            textPriorityToDo.setText("בינוני");
        }
        else // ==3
        {
            toDoPriority.setBackgroundResource(R.drawable.icon_low_priority);
            textPriorityToDo.setText("נמוך");
        }

        //toDoPriority.setText("כמות: " + values[position].getqUnitsOrder());
       // deleteToDo.setText("הגעת הזמנה: " + values[position].getDateOrder());


        //user can delete order from the list
        //set the checkBox to V
        deleteToDo.setChecked(false);
        deleteToDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    FirebaseDbHandler.mDatabase.child("users").child(FirebaseDbHandler.mUserId).child("ToDo").child(values[position].getKey()).removeValue();
                } else {
                    //ToDo
                }
            }
        });

        return rowView;
    }

}
