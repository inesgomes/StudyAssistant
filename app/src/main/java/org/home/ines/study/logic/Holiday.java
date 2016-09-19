package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Inês Gomes on 17/07/2016.
 */
public class Holiday extends Epoch {

    public Holiday(){
        super();
    }

    public Holiday(Parcel in){super(in);}

    @Override
    public void addSubject(Subject newSubject) {
        return;
    }

    @Override
    public ArrayList<Subject> getSubjects() {
        return null;
    }

    public static final Parcelable.Creator<Epoch> CREATOR = new Parcelable.Creator<Epoch>(){
        public Epoch createFromParcel(Parcel in) {
            return new Holiday(in);
        }

        public Epoch[] newArray(int size) {
            return new Epoch[size];
        }
    };

}
