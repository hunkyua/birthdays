function goBack() {
    window.history.back();
}

function onHover(id) {
    // var elm = document.getElementsByTagName("tr")[id];

    // if (elm.getElementsByTagName('button').length > 0) {
    //     elm.lastChild.remove();
    // } else {
    //     var btn = document.createElement("button");
    //     btn.setAttribute("class", "removeButton");
    //     btn.innerHTML = "Remove";
    //     var body = document.getElementsByTagName("tr")[id];
    //     body.appendChild(btn);

    var table = document.getElementsByTagName("table");
    table.deleteRow(id);
}