package org.but.feec.bds.project.api;

public class PersonEditView {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "PersonEditView{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
//                ", nickname='" + nickname + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}