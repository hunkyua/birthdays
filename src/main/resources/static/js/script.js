function goBack() {
    window.history.back();
}

function onHover(id) {
    var table = document.getElementsByTagName("table");
    table.deleteRow(id);
}

/**
 * @return {boolean}
 */
function OnButton1()
{
    document.Persons.action = "editPerson";
    document.Persons.submit();
    return true;
}

/**
 * @return {boolean}
 */
function OnButton2()
{
    document.Persons.action = "removePersons";
    document.Persons.submit();
    return true;
}