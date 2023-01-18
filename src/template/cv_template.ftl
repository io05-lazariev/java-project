<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="../template/styles/pdfstyle.css">
</head>

<body>
    <#if profilePicture??>
        <img id="profilePic" src="${profilePicture}" width="200" height="200">
    </#if>
    <div class="infoSection">
        <span id="nameSection">
            <h1 id="fullname"> ${fullName}</h1>
        </span>
        <span id="contactDetailsSection">
            <h2 id="contactDetailsHeading">Contact details</h2>
            <p id="contacts">
                <#if phone??>Phone: ${phone}<span class="tab"></span></#if><#if email??>Email: ${email}</#if>
            </p>
        </span>
        <span id="studyingSection">
            <h2 id="studyingHeading">Study</h2>
            <p id="study">${study}</p>
        </span>
    </div>
    <div class="attributes">
        <div id="passive">
            <span id="skillsSection">
                <h2 id="skillsHeading">Skills</h2>
                <#list skills as skill>
                <ul>
                    <li>${skill}</li>
                </ul>
                <#else>
                    <p>No skills</p>
                </#list>
            </span>
            <span id="languageSection">
                <h2 id="languageHeading">Languages</h2>
                <#list languages as lang, level>
                <ul>
                    <li>${lang} - ${level}</li>
                </ul>
                <#else>
                    <p>None</p>
                </#list>
            </span>
        </div>
        <span id="experienceSection">       
            <h2 id="experienceHeading">Experience</h2>
            <#list experiences as experience>
            <ul>
                <li class="experienceEntry">
                    <h3>${experience.company}</h3>
                    <h4>${experience.position}</h4>
                    <h5>${experience.years}</h5>
                    <p>${experience.shortDescription}</p>
                </li>
            </ul>
            <#else>
                <p>No experience</p>
            </#list>
        </span>
    </div>
</body>

</html>