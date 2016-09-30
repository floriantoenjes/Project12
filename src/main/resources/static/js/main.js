$("#add-ingredient").click(function(event) {
    event.preventdefault();
    addingredient();
});

function addingredient() {
    var $lastingredient = $(".ingredient-row:last");

    // get id of last ingredient-row
    var lastid = $lastingredient.children().first().children().first().attr("id").match("\\d+")[0];

    // increment to new id
    var newid = (parseint(lastid) + 1).tostring();

    var $newingredient = $lastingredient.clone();

    // remove id and version hidden input nodes
    $newingredient.children().first().children().first().remove();
    $newingredient.children().first().children().first().remove();

    // replace id and name to be of the new id
    var regex1 = new regexp("ingredients" + lastid, "g");
    var regex2 = new regexp("\\[" + lastid + "\\]", "g");
    var newhtml = $newingredient.html().replace(regex1, "ingredients" + newid).replace(regex2, "[" + newid + "]");

    $newingredient.html(newhtml);
    $lastingredient.after($newingredient);
}

$("#add-step").click(function(event) {
    event.preventdefault();
    addstep();
});

function addstep() {
    var $laststep = $(".step-row:last");

    var lastid = $(".step-row:last").children().first().attr("id").match("\\d+")[0];

    var newid = (parseint(lastid) + 1).tostring();

    var $newstep = $laststep.clone();

    $laststep.children().first().remove();
    $laststep.children().first().remove();

    var regex1 = new regexp("steps" + lastid, "g");
    var regex2 = new regexp("\\[" + lastid + "\\]", "g");

    var newhtml = $newstep.html().replace(regex1, "steps" + newid).replace(regex2, "[" + newid + "]");

    $newstep.html(newhtml);
    $laststep.after($newstep);

}

$("#category-select").change(function(event) {
    var value = $(event.target).val();
    window.location = "/index?category=" + value;
})
