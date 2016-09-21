package org.home.ines.study.logic;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by Inês Gomes on 17/07/2016.
 */
public class Exam extends Evaluation{
    private Calendar endHour;
    private String classroom;

    public Exam(Parcel in) {
        super(in);
        endHour = (Calendar)in.readSerializable();
        classroom = in.readString();
    }

    public static final Parcelable.Creator<Evaluation> CREATOR = new Parcelable.Creator<Evaluation>(){
        public Evaluation createFromParcel(Parcel in) {
            return new Exam(in);
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
    }
}
