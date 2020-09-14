$(function() {
    $('#personTable').on('click', 'tr', function(event) {
        if(!this.className.includes('highlight')) {
            $(this).addClass('highlight');
            var checkbox  = $(this).find(":checkbox")
            checkbox[0].checked = true;
        } else {
            $(this).removeClass('highlight');
            var checkbox  = $(this).find(":checkbox")
            checkbox[0].checked = false;
        }

    });

    $('#btnRowClick').click(function(e) {
        var rows = getHighlightRow();
        if (rows != undefined) {
            alert(rows.attr('id'));
        }
    });

    var getHighlightRow = function() {
        return $('table > tr.highlight');
    }

});