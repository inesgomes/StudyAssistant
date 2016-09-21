package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Inês Gomes on 17/07/2016.
 */
public class Subject implements Parcelable{
    private String color;
    private String name;
    private int ects;   // só se o ano letivo estiver a true.
    private ArrayList<Schedule> schedules = new ArrayList<>();
    private ArrayList<Task> tasks = new ArrayList<>();

    public Subject(){
        setName("Name");
        setEcts(0);
        // cor none
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public ArrayList<Schedule> getSchedule() { return schedules; }

    /**
     * Nome da disciplina
     * @return
     */
    @Override
    public String toString() {
        return name;
    }

    public Subject(Parcel in){
        name = in.readString();
        color = in.readString();
        ects = in.readInt();
        schedules = in.readArrayList(Schedule.class.getClassLoader());
        tasks = in.readArrayList(Task.class.getClassLoader());
    }

    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>(){
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(color);
        dest.writeInt(ects);
        dest.writeList(schedules);
        dest.writeList(tasks);
    }
}
