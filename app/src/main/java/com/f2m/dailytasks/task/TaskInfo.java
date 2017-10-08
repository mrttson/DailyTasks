package com.f2m.dailytasks.task;

import com.f2m.dailytasks.activities.adapters.EventTypeOptions;
import com.f2m.dailytasks.activities.adapters.ReminderOptions;
import com.f2m.dailytasks.activities.adapters.RepeatOptions;

import java.util.List;

/**
 * Created by sev_user on 9/26/2017.
 */

public class TaskInfo {

    private int id;
    private String title;
    private String notes;
    private long timeStart;
    private long timeEnd;
    private EventTypeOptions type;
    private List<ReminderOptions> reminders;
    private RepeatOptions repeat;

    public TaskInfo(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public EventTypeOptions getType() {
        return type;
    }

    public void setType(EventTypeOptions type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ReminderOptions> getReminders() {
        return reminders;
    }

    public void setReminders(List<ReminderOptions> reminders) {
        this.reminders = reminders;
    }

    public RepeatOptions getRepeat() {
        return repeat;
    }

    public void setRepeat(RepeatOptions repeat) {
        this.repeat = repeat;
    }
}
