$(function(){
    $('[type=checkbox]').click(function ()
    {
        var checkedChbx = $('[type=checkbox]:checked');
        if (checkedChbx.length === 1)
        {
            $('#editButton').show();
            $('#editButton').css("display", "inline-block");
        }
        else
        {
            $('#editButton').hide();
        }
    });
    $('body').on('click', 'td', function()
    {
        var checkedChbx = $('[type=checkbox]:checked');
        if (checkedChbx.length === 1)
        {
            $('#editButton').show();
            $('#editButton').css("display", "inline-block");
        }
        else
        {
            $('#editButton').hide();
        }
    });
});