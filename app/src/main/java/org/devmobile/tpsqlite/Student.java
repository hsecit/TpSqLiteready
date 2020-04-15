package org.devmobile.tpsqlite;

public class Student {
    private  long id;
    private  String nom;
    private  String tel;

    public long getId() { return id; }

    public String getNom() { return nom; }

    public String getTel() { return tel; }

    public void setId(long id) { this.id = id; }

    public void setNom(String nom) { this.nom = nom; }

    public void setTel(String tel) { this.tel = tel; }



    @Override
    public String toString() {
        return nom + "  " + tel ;
    }

    public Student(){

    }
}
