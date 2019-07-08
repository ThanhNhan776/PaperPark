const DEFAULT_MAKE_TIME_HOURS_PER_DAY = 3;

function handleTimePickerChangeTotalHours() {
    let totalHoursEl = document.getElementById("makeTimeTotalHours");
    let daysEl = document.getElementById("makeTimeDay");
    let hoursPerDayEl = document.getElementById("makeTimeHoursPerDay");

    let totalHours = totalHoursEl.value;
    let days = daysEl.value;
    let hoursPerDay = hoursPerDayEl.value;

    if (days > 0) {
        hoursPerDay = totalHours / days;
        if (hoursPerDay > 24) {
            hoursPerDay = 24;
            days = Math.ceil(totalHours / hoursPerDay);
        }
    } else {
        hoursPerDay = DEFAULT_MAKE_TIME_HOURS_PER_DAY > totalHours
                ? (totalHours > 24 ? 24 : totalHours)
                : DEFAULT_MAKE_TIME_HOURS_PER_DAY;
        days = Math.ceil(totalHours / hoursPerDay);
    }

    daysEl.value = days;
    hoursPerDayEl.value = (hoursPerDay*1).toFixed(1);
}

function handleTimePickerChangeHoursPerDay() {
    let totalHoursEl = document.getElementById("makeTimeTotalHours");
    let daysEl = document.getElementById("makeTimeDay");
    let hoursPerDayEl = document.getElementById("makeTimeHoursPerDay");

    let totalHours = totalHoursEl.value;
    let days = daysEl.value;
    let hoursPerDay = hoursPerDayEl.value;

    if (totalHours === 0) {
        totalHours = days * hoursPerDay;
    } else {
        if (days === 0) {
            days = Math.ceil(totalHours / hoursPerDay);
        } else {
            totalHours = days * hoursPerDay;
        }
    }

    daysEl.value = days;
    totalHoursEl.value = (totalHours*1).toFixed(1);
}

function handleTimePickerChangeDays() {
    let totalHoursEl = document.getElementById("makeTimeTotalHours");
    let daysEl = document.getElementById("makeTimeDay");
    let hoursPerDayEl = document.getElementById("makeTimeHoursPerDay");

    let totalHours = totalHoursEl.value;
    let days = daysEl.value;
    let hoursPerDay = hoursPerDayEl.value;

    if (totalHours === 0) {
        totalHours = days * hoursPerDay;
    } else {
        hoursPerDay = totalHours / days;
        if (hoursPerDay > 24) {
            hoursPerDay = 24;
            totalHours = days * hoursPerDay;
        }
    }
    
    totalHoursEl.value = (totalHours*1).toFixed(1);
    hoursPerDayEl.value = (hoursPerDay*1).toFixed(1);
}

function handleChangeLevel(element) {
    switch (element.value) {
        case '1':
            element.className = element.className.replace(/bg-.*/, 'bg-super-easy');
            break;
        case '2':
            element.className = element.className.replace(/bg-.*/, 'bg-easy');
            break;
        case '3':
            element.className = element.className.replace(/bg-.*/, 'bg-normal');
            break;
        case '4':
            element.className = element.className.replace(/bg-.*/, 'bg-hard');
            break;
        case '5':
            element.className = element.className.replace(/bg-.*/, 'bg-super-hard');
            break;
    }
}

function suggestModels() {
    let skillLevel = document.getElementById("selectSkillLevel").value;
    let difficulty = document.getElementById("selectDifficulty").value;
    let makeTime = document.getElementById("makeTimeTotalHours").value;
    
    alert('skillLevel: ' + skillLevel + '\ndifficulty: ' + difficulty + '\nmakeTime: ' + makeTime);
    
    let div = document.getElementById("section-search-result");
    div.style.display = "block";
    div.scrollIntoView({
        behavior: 'smooth'
    });
}

function searchModels() {
    let modelName = document.getElementById("modelName").value;
    alert('modelName: ' + modelName);
    
    let div = document.getElementById("section-search-result");
    div.style.display = "block";
    div.scrollIntoView({
       behavior: 'smooth' 
    });
    
    
}