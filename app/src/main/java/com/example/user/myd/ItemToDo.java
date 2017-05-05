package com.example.user.myd;

import com.google.firebase.database.Exclude;

/***************************************
 * This class Represents a to-do task.
 * priority: 1 = high priority,
 *           2 = medium priority,
 *           3 = low priority
 **************************************/

public class ItemToDo {

    private String title;
    private boolean done;
    //Firebase needs a key property
    private String key;

    // Default constructor required for calls to DataSnapshot.getValue(ItemToDo.class)
    public ItemToDo() {}

    public ItemToDo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
