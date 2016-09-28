$("#add-ingredient").click(function(event) {
    event.preventDefault();
    addIngredient();
});

function addIngredient() {
    var $ingredient = $(".ingredient-row:last");
    var inputIdName = $ingredient.children().first().children().first().attr("name");
    var current = parseInt(inputIdName.match("\\d"));
    var n = current + 1;

    alert(n);

    $ingredient.after($('<div class="ingredient-row">' +
            '<div class="prefix-20 grid-30">' +
                '<input id="ingredients' + current + '.id"' + ' name="ingredients[' + current + '].id" type="hidden"/>' +
                '<input id="ingredients' + current + '.version"' + ' name="ingredients[' + current + '].version" type="hidden"/>' +
                '<p>' +
                    '<select id="ingredients' + current + '.item.name" name="ingredients[' + current + '].item.name">' +

                    '</select>' +
                '</p>' +
            '</div>' +
        '</div>'
    ));
}