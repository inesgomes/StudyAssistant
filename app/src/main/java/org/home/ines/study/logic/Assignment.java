package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Inês Gomes on 17/07/2016.
 */
public class Assignment extends Evaluation {
    private ArrayList<String> team = new ArrayList<>();

    public Assignment(Parcel in) {
        super(in);
        in.readArrayList(String.class.getClassLoader());
    }

    public static final Parcelable.Creator<Evaluation> CREATOR = new Parcelable.Creator<Evaluation>(){
        public Evaluation createFromParcel(Parcel in) {
            return new Assignment(in);
        }

        public Evaluation[] newArray(int size) {
            return new Evaluation[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeList(team);
    }
}
