package org.but.feec.javafx.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.feec.javafx.api.*;
import org.but.feec.javafx.data.PersonRepository;

import java.util.List;

/**
 * Class representing business logic on top of the Persons
 */
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDetailView getPersonDetailView(Long id) {
        return personRepository.findPersonDetailedView(id);
    }

    public List<PersonBasicView> getPersonsBasicView() {
        return personRepository.getPersonsBasicView();
    }

    public List<PersonBasicView> getPersonsBasicViewCustomer() {
        return personRepository.getPersonsBasicViewCustomer();
    }

    public List<PersonBasicView> getPersonsBasicViewCustomer_1() {
        return personRepository.getPersonsBasicViewCustomer_1();
    }

    public List<PersonBasicView> getPersonsInjectionView1(String lastName) {
        return personRepository.getPersonsInjectionView1(lastName);
    }

    public List<PersonBasicView> getPersonsInjectionView2(String lastName) {
        return personRepository.getPersonsInjectionView2(lastName);
    }


    public void createPerson(PersonCreateView personCreateView) {
        // the following three lines can be written in one code line (only for more clear explanation it is written in three lines
        char[] originalPassword = personCreateView.getPwd();
        char[] hashedPassword = hashPassword(originalPassword);
        personCreateView.setPwd(hashedPassword);

        personRepository.createPerson(personCreateView);
    }

    public void editPerson(PersonEditView personEditView) {
        personRepository.editPerson(personEditView);
    }

    public void deletePerson(PersonDeleteView personDeleteView){
        personRepository.deletePerson(personDeleteView);
    }

    public List<PersonBasicView> getPersonsFilterView(String lastName) {
        return personRepository.getPersonsFilterView(lastName);
    }

    /**
     * <p>
     * Note: For implementation details see: https://github.com/patrickfav/bcrypt
     * </p>
     *
     * @param password to be hashed
     * @return hashed password
     */
    private char[] hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToChar(12, password);
    }


}