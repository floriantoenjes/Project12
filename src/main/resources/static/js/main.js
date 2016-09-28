$("#add-ingredient").click(function(event) {
    event.preventDefault();
    addIngredient();
});

function addIngredient() {
    var $lastIngredient = $(".ingredient-row:last");
    var lastId = $lastIngredient.children().first().children().first().attr("id").match("\\d+")[0];
    var newId = (parseInt(lastId) + 1).toString();
    var $newIngredient = $lastIngredient.clone();

    var regex1 = new RegExp("ingredients" + lastId, "g");
    var regex2 = new RegExp("\\[" + lastId + "\\]", "g");

    var newHtml = $newIngredient.html().replace(regex1, "ingredients" + newId).replace(regex2, "[" + newId + "]");
    $newIngredient.html(newHtml);
    $lastIngredient.after($newIngredient);
}