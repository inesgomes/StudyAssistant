package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Inês Gomes on 21/09/2016.
 */
public abstract class Evaluation implements Parcelable{
    private String name;
    private Calendar day;
    private Calendar initHour;
    private int weigth; //percentagem de peso na nota final
    private String info;

    public Evaluation(){
        name = "Name";
        day = Calendar.getInstance();
        weigth = 0;
        initHour = Calendar.getInstance(); // ???
    }

    /**
     * Metodos da interface Parcelable
     * @param in
     */
    public Evaluation(Parcel in){
        name = in.readString();
        day = (Calendar)in.readSerializable();
        initHour = (Calendar)in.readSerializable();
        weigth = in.readInt();
        info = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeSerializable(day);
        dest.writeSerializable(initHour);
        dest.writeInt(weigth);
        dest.writeString(info);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
