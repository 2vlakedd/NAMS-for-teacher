package com.forappnams.nfcattendance;

public class DataModal {


     String IDNumber;

  String StudentName;


    public DataModal() {}

    public DataModal(String IDNumber, String StudentName ) {
        this.IDNumber = IDNumber;

        this.StudentName = StudentName;

    }

    public String getIDNumber(){
        return IDNumber;
    }

    public String getStudentName(){
        return StudentName;
    }


}


