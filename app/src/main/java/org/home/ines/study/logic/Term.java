package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Inês Gomes on 17/07/2016.
 */
public class Term extends Epoch {
    //private ArrayList<String> classes = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();

    public Term(Parcel in){
        super(in);
        //classes = in.readArrayList(String.class.getClassLoader());
        subjects = in.readArrayList(Subject.class.getClassLoader());
    }

    @Override
    public void addSubject(Subject newSubject) {
        subjects.add(newSubject);
    }

    @Override
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public Term(){
        super();
    }

    public static final Parcelable.Creator<Epoch> CREATOR = new Parcelable.Creator<Epoch>(){
        public Epoch createFromParcel(Parcel in) {
            return new Term(in);
        }

        public Epoch[] newArray(int size) {
            return new Epoch[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        //dest.writeList(classes);
        dest.writeList(subjects);
    }
}
