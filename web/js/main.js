$(document).ready(function () {
    $('.inp-cbx').first().attr('checked', 'checked');

    let canv = document.getElementById("plot");
    let ctx = canv.getContext("2d");
    let canvWidth = canv.width;

    ctx.fillStyle = '#4F93E8';
    ctx.beginPath();
    ctx.arc(canvWidth/2, canvWidth/2, canvWidth/3, 0, Math.PI/2);
    ctx.lineTo(canvWidth/2, canvWidth/2);
    ctx.rect(canvWidth/2, canvWidth/6, canvWidth/3, canvWidth/3);
    ctx.fill();
    ctx.beginPath();
    ctx.moveTo(canvWidth/2, canvWidth/2);
    ctx.lineTo(canvWidth/2, canvWidth*5/6);
    ctx.lineTo(canvWidth/6, canvWidth/2);
    ctx.closePath();
    ctx.fill();
    //Ox arrow
    ctx.beginPath();
    ctx.moveTo(10, canvWidth/2);
    ctx.lineTo(canvWidth-10, canvWidth/2);
    ctx.lineTo(canvWidth-20, canvWidth/2-5);
    ctx.moveTo(canvWidth-10, canvWidth/2);
    ctx.lineTo(canvWidth-20, canvWidth/2+5);
    //Oy arrow
    ctx.moveTo(canvWidth/2, canvWidth-10);
    ctx.lineTo(canvWidth/2, 10);
    ctx.lineTo(canvWidth/2-5, 20);
    ctx.moveTo(canvWidth/2, 10);
    ctx.lineTo(canvWidth/2+5, 20);
    //Dashes
    ctx.moveTo(canvWidth/6, canvWidth/2-4);
    ctx.lineTo(canvWidth/6, canvWidth/2+4);
    ctx.moveTo(canvWidth/2-4, canvWidth/6);
    ctx.lineTo(canvWidth/2+4, canvWidth/6);
    ctx.moveTo(canvWidth*5/6, canvWidth/2-4);
    ctx.lineTo(canvWidth*5/6, canvWidth/2+4);
    ctx.moveTo(canvWidth/2-4, canvWidth*5/6);
    ctx.lineTo(canvWidth/2+4, canvWidth*5/6);

    ctx.moveTo(canvWidth/3, canvWidth/2-4);
    ctx.lineTo(canvWidth/3, canvWidth/2+4);
    ctx.moveTo(canvWidth/2-4, canvWidth/3);
    ctx.lineTo(canvWidth/2+4, canvWidth/3);
    ctx.moveTo(canvWidth*4/6, canvWidth/2-4);
    ctx.lineTo(canvWidth*4/6, canvWidth/2+4);
    ctx.moveTo(canvWidth/2-4, canvWidth*4/6);
    ctx.lineTo(canvWidth/2+4, canvWidth*4/6);

    ctx.closePath();
    ctx.stroke();

    ctx.fillStyle = '#000';
    ctx.font = '14px Arial';
    ctx.fillText("X", canvWidth-20, canvWidth/2-10);
    ctx.fillText("Y", canvWidth/2-16, 20);

    ctx.fillText("-R", canvWidth/6-9, canvWidth/2-5);
    ctx.fillText("-R/2", canvWidth/3-12, canvWidth/2-6);

    ctx.fillText("R", canvWidth/2-17, canvWidth/6+4);
    ctx.fillText("R/2", canvWidth/2-29, canvWidth/3+4);

    ctx.fillText("R", canvWidth*5/6-5, canvWidth/2-5);
    ctx.fillText("R/2", canvWidth*4/6-10, canvWidth/2-6);

    ctx.fillText("-R", canvWidth/2-22, canvWidth*5/6+4);
    ctx.fillText("-R/2", canvWidth/2-34, canvWidth*4/6+4);

    let R;
    canv.addEventListener("click", drawPoint, false);

    function drawPoint(e) {
        R = $('.inp-cbx[name=r]:checked').val();
        if (R === undefined) {
            $('.r-select .warning').addClass('icon-visible')
        } else {
            let dot = getCursorPosition(e);
            console.log(dot.x + " " + dot.y);
            ctx.beginPath();
            ctx.arc(dot.x, dot.y, 4, 0, Math.PI*2);
            let x = (dot.x - canv.width/2) * R / 100;
            let y = -(dot.y - canv.height/2) * R / 100;
            console.log(x + " " + y);
            ctx.fillStyle = 'green';
            ctx.fill();
        }
    }

    function getCursorPosition(e) {
        let x, y;
        if (e.offsetX != undefined && e.offsetY != undefined) {
            x = e.offsetX;
            y = e.offsetY;
        }
        else {
            x = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
            y = e.clientY + document.body.scrollTop + document.documentElement.scrollTop;
        }
        return {x: x, y: y};
    }

    $('.clear-cookie').on('click', function () {
        $('.results tr:not(:first-child)').remove();
        $.post('clearCookie.php');
    });

    $('.cbx').on('click', function () {
        $('.r-select .warning').removeClass('icon-visible');
        // R = $('.inp-cbx[name=r]:checked').val();
    });

    $('#Y-select').on('focus', function () {
        $('.y-select .warning').removeClass('icon-visible');
    });

    $('form').submit(function (e) {
        e.preventDefault();
        var x = $('#X-select').val();
        var r = $('.inp-cbx:checked').val();

        let validY = false;
        let validR = false;
        let ySelect = $('#Y-select').val().trim();
        if (ySelect.match(/^-?[0-9]*[.,]?[0-9]+$/) && ySelect && ySelect !== '-') {
            var y = parseFloat(ySelect);
            if (y >= -5 && y <= 5) {
                validY = true;
            }
        }
        if ($('.inp-cbx:checked').length === 1) {
            validR = true;
        }

        if (!validY) {
            $('.y-select .warning').addClass('icon-visible')
        }
        if (!validR) {
            $('.r-select .warning').addClass('icon-visible')
        }
        if (validY && validR) {
            $.ajax({
                type: 'POST',
                url: 'controller',
                data: {'x_value': x, 'y_value': y, 'r_value': r},
                success: function(data) {
                    $('.results > tbody').append(data);
                },
                error: function (jqXHR) {
                    console.log(jqXHR);
                    $('.alert').text("Ошибка " + jqXHR.status + ": " + jqXHR.statusText).slideDown().delay(2000).slideUp();
                }
            });
        }
    });
});