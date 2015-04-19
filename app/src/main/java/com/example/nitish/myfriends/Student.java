package com.example.nitish.myfriends;

public class Student {

    private int _id;
    private String _name;
    private String _email;
    private String _phoneNumber;

    public Student(){
        this._id = 0;
        this._name = null;
        this._email = null;
        this. _phoneNumber = null;
    }
    public Student(String studentName, String studentEmail, String studentPhoneNumber){
        this._name = studentName;
        this._email = studentEmail;
        this._phoneNumber = studentPhoneNumber;
    }
    public Student(int studentID, String studentName, String studentEmail, String studentPhoneNumber){
        this._id = studentID;
        this._name = studentName;
        this._email = studentEmail;
        this._phoneNumber = studentPhoneNumber;
    }
    //Setters
    public void setID(int studentID) {
        this._id = studentID;
    }
    public void setName(String studentName) {
        this._name = studentName;
    }
    public void setEmail(String studentEmail){
        this._email = studentEmail;
    }
    public void setPhoneNumber(String studentPhoneNumber){ this._phoneNumber = studentPhoneNumber;
    }
    //Getters
    public int getID() {
        return this._id;
    }
    public String getName(){
        return this._name;
    }
    public String getEmail(){
        return this._email;
    }
    public String getPhoneNumber(){
        return this._phoneNumber;
    }
}
