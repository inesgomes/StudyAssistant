package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by Inês Gomes on 17/07/2016.
 */
public class Homework extends Evaluation{

    public Homework(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Evaluation> CREATOR = new Parcelable.Creator<Evaluation>(){
        public Evaluation createFromParcel(Parcel in) {
            return new Homework(in);
        }

        public Evaluation[] newArray(int size) {
            return new Evaluation[size];
        }
    };

}
