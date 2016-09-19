package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Inês Gomes on 17/07/2016.
 */
public class SchoolYear implements Parcelable{
    private String name;
    private boolean diffWeights; // true se as cadeiras deste ano letivo têm pesos diferentes
    private ArrayList<Epoch> terms = new ArrayList<>();
    private ArrayList<Epoch> holidays = new ArrayList<>();
    private ArrayList<String> classType = new ArrayList<>();
    private int id;
    private static int total = 0;

    /**
     * Construtor com as opcoes default. Tem um id incrementado a cada objeto criado.
     */
    public SchoolYear(){
        this.setName("Name");
        setDiffWeights(false);
        total++;
        id = total;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean hasDiffWeights() {
        return diffWeights;
    }

    public void setDiffWeights(boolean diffWeights) {
        this.diffWeights = diffWeights;
    }

    public ArrayList<Epoch> getTerms() {
        return terms;
    }

    public ArrayList<Epoch> getHolidays() {
        return holidays;
    }

    public ArrayList<String> getClassTypes() {
        return classType;
    }

    public int getId() { return id; }

    /**
     * Retorna true se esta epoca já existir, ou seja, já existe esse id
     * @param e
     * @return
     */
    public boolean verifyEpoch(Epoch e) {
        if(e instanceof Term)
            if (terms.contains(e)) {
                terms.remove(e);  //remove o anterior e coloca este
                terms.add(e);
                return true;
            }
        if(e instanceof Holiday )
            if(holidays.contains(e)){
                holidays.remove(e);
                holidays.add(e);
                return true;
            }
        return false;
    }

    /**
     * Adiciona a nova cadeira ao Semestre correspondente
     * @param newSubject
     * @param e
     */
    public boolean addSubject(Subject newSubject, Epoch e) {
        if(terms.contains(e)){
            e.addSubject(newSubject);
            terms.remove(e);
            terms.add(e);
            return true;
        }
        return false;
    }

    /**
     * Dois objetos são iguais se tiverem o mesmo id
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof SchoolYear)
            return (this.id == ((SchoolYear) object).getId());
        return false;
    }

    /**
     * Retorna o nome do ano escolar
     * @return
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Metodos da interface Parcelable
     * @param in
     */
    public SchoolYear(Parcel in){
        id = in.readInt();
        name = in.readString();
        diffWeights = in.readByte() != 0;
        terms = in.readArrayList(Epoch.class.getClassLoader());
        holidays = in.readArrayList(Epoch.class.getClassLoader());
        classType = in.readArrayList(String.class.getClassLoader());
    }

    public static final Parcelable.Creator<SchoolYear> CREATOR = new Parcelable.Creator<SchoolYear>(){
        public SchoolYear createFromParcel(Parcel in) {
            return new SchoolYear(in);
        }

        public SchoolYear[] newArray(int size) {
            return new SchoolYear[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (diffWeights ? 1 : 0));
        dest.writeList(terms);
        dest.writeList(holidays);
        dest.writeList(classType);
    }
}
