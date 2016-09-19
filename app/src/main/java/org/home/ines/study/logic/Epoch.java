package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by Inês Gomes on 12/09/2016.
 */
public abstract class Epoch implements Parcelable {
    private String name;
    private Calendar init;
    private Calendar end;
    private static int total = 0;
    private int id;

    /**
     * Constructor with default options.
     * O id é incrementado a cada novo objeto criado
     */
    public Epoch(){
        name = "Name";
        init = Calendar.getInstance();
        end = Calendar.getInstance();
        total++;
        id = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getInit() {
        return init;
    }

    public void setInit(Calendar init) {
        this.init = init;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public int getId() { return id; }

    /**
     * Nome da Epoca
     * @return
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Devolve true se os id's forem iguais
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Epoch)
            return (this.id == ((Epoch) object).getId());
        return false;
    }

    /**
     * Metodos da interface Parcelable
     * @param in
     */
    public Epoch(Parcel in){
        name = in.readString();
        init = (Calendar)in.readSerializable();
        end = (Calendar)in.readSerializable();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeSerializable(init);
        dest.writeSerializable(end);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public abstract void addSubject(Subject newSubject);

    public abstract ArrayList<Subject> getSubjects();
}
