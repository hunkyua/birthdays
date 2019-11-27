$(function(){
    $('[type=checkbox]').click(function ()
    {
        var checkedChbx = $('[type=checkbox]:checked');
        if (checkedChbx.length > 0)
        {
            $('#removeButton').show();
            $('#removeButton').css("display", "block");
        }
        else
        {
            $('#removeButton').hide();
        }
    });
    $('body').on('click', 'td', function()
    {
        var checkedChbx = $('[type=checkbox]:checked');
        if (checkedChbx.length > 0)
        {
            $('#removeButton').show();
            $('#removeButton').css("display", "block");
        }
        else
        {
            $('#removeButton').hide();
        }
    });
});