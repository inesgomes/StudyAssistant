package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Inês Gomes on 21/09/2016.
 */
public class Presentation extends Evaluation{
    private Calendar endHour;
    private String classroom;
    private ArrayList<String> team = new ArrayList<>();

    public Presentation(Parcel in) {
        super(in);
        endHour = (Calendar)in.readSerializable();
        classroom = in.readString();
        team = in.readArrayList(String.class.getClassLoader());
    }

    public static final Parcelable.Creator<Evaluation> CREATOR = new Parcelable.Creator<Evaluation>(){
        public Evaluation createFromParcel(Parcel in) {
            return new Presentation(in);
        }

        public Evaluation[] newArray(int size) {
            return new Evaluation[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeSerializable(endHour);
        dest.writeString(classroom);
        dest.writeList(team);
    }
}
