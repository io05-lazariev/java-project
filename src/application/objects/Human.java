package application.objects;

import java.io.File;
import java.util.ArrayList;

public class Human {

    protected String firstName;

    protected String lastName;

    protected String email;

    protected String phone;

    protected String study;

    protected ArrayList<String> skills = new ArrayList<String>();

    protected ArrayList<Language> languages = new ArrayList<Language>();

    protected ArrayList<Experience> experiences = new ArrayList<Experience>();

    protected File profileImage;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
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

    public void setLanguage(Language language, int langIndex) {
        this.languages.set(langIndex, language);
    }

    public void addLangauge(Language language) {
        this.languages.add(language);
    }

    public Language getLanguageByIndex(int languageIndex) {
        return this.languages.get(languageIndex);
    }

    public void addExperience(Experience experience) {
        this.experiences.add(experience);
    }

    public void setProfileImage(File image) {
        this.profileImage = image;
    }

    public File getProfileImage() {
        return this.profileImage;
    }

}
