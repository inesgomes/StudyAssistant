package org.home.ines.study.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Inês Gomes on 26/07/2016.
 */
public class User implements Parcelable{
    private String name;
    private String course;
    private String college;
    private int currentYear; // posição no vetor
    private ArrayList<SchoolYear> allYears = new ArrayList<SchoolYear>();

    /**
     * Construtor com valores default
     */
    public User(){
        this.name = "Name";
        this.college = "College";
        this.course = "Course";
        currentYear = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public ArrayList<SchoolYear> getAllYears() {
        return allYears;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentYear() { return currentYear; }

    /**
     * Verifica se já existe o objeto no array. Se sim, elimina e troca pelo novo
     * @param sy
     * @return
     */
    public boolean verifyAllYears(SchoolYear sy) {
        if(allYears.contains(sy)) {
            allYears.remove(sy); //remove o objeto que estava la
            allYears.add(sy); //coloca-o novamente com as informaçoes corretas
            return true;
        }
        return false;
    }

    /**
     * Atualiza o ano escolar atual
     * @param sy
     */
    public boolean update(SchoolYear sy) {
        if(allYears.contains(sy)){
            allYears.remove(sy);
            allYears.add(sy);
            return true;
        }
        return false;
    }

    /**
     * Metodos da interface Parcelable
     * @param in
     */
    public User(Parcel in){
        String[] data = new String[3];
        in.readStringArray(data);
        this.name = data[0];
        this.college = data[1];
        this.course = data[2];
        this.currentYear = in.readInt();
        this.allYears = in.readArrayList(SchoolYear.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name, this.college, this.course});
        dest.writeInt(currentYear);
        dest.writeList(allYears);
    }
}
