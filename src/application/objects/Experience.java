package application.objects;

public class Experience {
    
    protected String company;

    protected String position;

    protected String years;

    protected String shortDescription;

    protected boolean finished;

    public Experience(
        String company,
        String position,
        int yearStarted,
        int yearFinished,
        String shortDescription
    ) {
        this.company = company;
        this.position = position;
        this.shortDescription = shortDescription;
        String finished = Integer.toString(yearFinished);
        this.finished = true;
        String started = Integer.toString(yearStarted);
        String workingYears = started + "-" + finished;
        this.years = workingYears;
    }

    public Experience(
        String company,
        String position,
        int yearStarted,
        String shortDescription
    ) {
        this.company = company;
        this.position = position;
        this.shortDescription = shortDescription;
        String started = Integer.toString(yearStarted);
        this.finished = false;
        String workingYears = started + "-Present";
        this.years = workingYears;
    }

    public String getCompany() {
        return this.company;
    }

    public String getPosition() {
        return this.position;
    }

    public String getYears() {
        return this.years;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public int getStartedYear() {
        String[] years = splitYears();
        return Integer.parseInt(years[0]);
    }

    public int getFinishedYear() {
        String[] years = this.splitYears();
        return Integer.parseInt(years[1]);
    }

    public boolean isFinished() {
        return this.finished;
    }

    protected String[] splitYears() {
        return this.years.split("-");
    }

}
