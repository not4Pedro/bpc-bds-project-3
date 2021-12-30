package org.but.feec.bds.project.api;

public class PersonAuthView {
    private String password;
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PersonAuthView{" +
                "email='" + lastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
