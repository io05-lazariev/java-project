<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="styles/pdfstyle.css">
    <title>${fullName} CV</title>
</head>

<body>
    <#if profilePicture??>
        <img id="profilePic" src="${profilePicture}" width="200" height="200">
    </#if>
    <div class="infoSection" id="nameSection">
        <h1 id="fullname"> ${fullName}</h1>
    </div>
    <div class="infoSection" id="contactDetailsSection">
        <h2 id="contactDetailsHeading">Contact details:</h2>
        <p id="phone">Phone: ${phone}</p>
        <p id="email">Email: ${email}</p>
    </div>
    <div class="infoSection" id="studyingSection">
        <h2 id="studyingHeading">Study</h2>
        <p id="study">${study}</p>
    </div>
    <div class="attributes" id="skillsSection">
        <h2 id="skillsHeading">Skills</h2>
        <ul>
            <#list skills as skill>
                <li>${skill}</li>
            </#list>
        </ul>
    </div>
    <div class="attributes" id="experienceSection">
        <h2 id="experienceHeading">Experience</h2>
        <ul>
            <#list experiences as experience>
                <li class="experienceEntry">
                    <h3>${experience.company}</h3>
                    <h4>${experience.position}</h4>
                    <h5>${experience.years}</h5>
                    <p>${experience.shortDescription}</p>
                </li>
            </#list>
        </ul>
    </div>
    <div class="attributes" id="languageSection">
        <h2 id="languageHeading">Languages</h2>
        <ul>
            <#list languages as lang, level>
                <li>${lang} - ${level}</li>
            </#list>
        </ul>
    </div>
</body>

</html>