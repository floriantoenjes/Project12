$("#add-ingredient").click(function(event) {
    event.preventDefault();
    addIngredient();
});

function addIngredient() {
    var $lastIngredient = $(".ingredient-row:last");

    // get id of last ingredient-row
    var lastId = $lastIngredient.children().first().children().first().attr("id").match("\\d+")[0];

    // increment to new id
    var newId = (parseInt(lastId) + 1).toString();

    var $newIngredient = $lastIngredient.clone();

    // remove id and version hidden input nodes
    $newIngredient.children().first().children().first().remove();
    $newIngredient.children().first().children().first().remove();

    // replace id and name to be of the new id
    var regex1 = new RegExp("ingredients" + lastId, "g");
    var regex2 = new RegExp("\\[" + lastId + "\\]", "g");
    var newHtml = $newIngredient.html().replace(regex1, "ingredients" + newId).replace(regex2, "[" + newId + "]");

    $newIngredient.html(newHtml);
    $lastIngredient.after($newIngredient);
}

$("#add-step").click(function(event) {
    event.preventDefault();
    addStep();
});

function addStep() {
    var $lastStep = $(".step-row:last");

    var lastId = $(".step-row:last").children().first().attr("id").match("\\d+")[0];

    var newId = (parseInt(lastId) + 1).toString();

    var $newStep = $lastStep.clone();

    $lastStep.children().first().remove();
    $lastStep.children().first().remove();

    var regex1 = new RegExp("steps" + lastId, "g");
    var regex2 = new RegExp("\\[" + lastId + "\\]", "g");

    var newHtml = $newStep.html().replace(regex1, "steps" + newId).replace(regex2, "[" + newId + "]");

    $newStep.html(newHtml);
    $lastStep.after($newStep);

}

$("#category-select").change(function(event) {
    var value = $(event.target).val();
    window.location = "/index?category=" + value;
})
