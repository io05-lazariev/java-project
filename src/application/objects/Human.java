package application.objects;

import java.io.File;
import java.util.ArrayList;

public class Human {

    protected String firstName;

    protected String lastName;

    protected String email;

    protected String phone;

    protected String study;

    protected ArrayList<String> skills;

    protected ArrayList<Language> languages;

    protected ArrayList<Experience> experiences;

    protected File profileImage;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public void addSkill(String skill) {
        this.skills.add(skill);
    }

    public void addLangauge(Language language) {
        this.languages.add(language);
    }

    public void addExperience(Experience experience) {
        this.experiences.add(experience);
    }

    public void setProfileImage(File image) {
        this.profileImage = image;
    }

}
