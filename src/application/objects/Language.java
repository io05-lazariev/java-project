package application.objects;

public class Language {

    /**
     * Internal id relative to human instance.
     */
    protected int id;
    
    protected String lang;

    protected String level;

    protected String languageLevelCombined;

    public Language(
        String language,
        String level
    ) {
        this.lang = language;
        this.level = level;
        this.languageLevelCombined = language + " - " + level;
    }

    public void setLanguage(String language) {
        this.lang = language;
        this.updateLanguageLevelCombined();
    }

    public String getLanguage() {
        return this.lang;
    }

    public void setLevel(String level) {
        this.level = level;
        this.updateLanguageLevelCombined();
    }

    public String getLevel() {
        return this.level;
    }

    public String getLanguageLevel() {
        return this.languageLevelCombined;
    }

    protected void updateLanguageLevelCombined() {
        this.languageLevelCombined = this.lang + " - " + this.level;
    }

    public void updateLanguage(String updatedLanguageLevelCombined) {
        String[] languageLevelSplit = updatedLanguageLevelCombined.split(" - ");
        String lang = languageLevelSplit[0];
        String level = languageLevelSplit[1];
        this.setLanguage(lang);
        this.setLevel(level);
    }

}
