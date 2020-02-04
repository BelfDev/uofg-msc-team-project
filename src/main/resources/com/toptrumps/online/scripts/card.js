const Card = (function() {
    const cardAttributeTemplateSelector = "#template-attribute-item";
    const cardAttributeSelector = ".js-card-char";
    const cardAttributeActiveClass = "pcard__char--active";
    const cardAttributeValueSelector = ".js-card-char-value";
    const cardAttributesWrapperSelector = ".js-card-chars";
    const cardAttributesWrapperActiveClass = "pcard__chars--active";
    const cardImageSelector = ".js-card-image";
    const cardTitleSelector = ".js-card-title";

    const init = function() {
        bindEvents();
    };

    const bindEvents = function() {
        $(document).on("card:attributeSelection", function(event, data) {
            if (data.active === true) {
                enableAttributeSelection();
            } else {
                disableAttributeSelection();
            }
        });
    };

    const getImagePath = function(cardName) {
        const imageName = cardName.replace(" ", "_").toLowerCase();
        return `/assets/images/${imageName}.png`;
    };

    const highlightAttribute = function(attribute) {
        $(cardAttributeSelector).removeClass(cardAttributeActiveClass);
        $(cardAttributeSelector)
            .filter(function() {
                return $(this).data("attribute") == attribute;
            })
            .addClass(cardAttributeActiveClass);
    };

    const createAttributesNodes = function(playerSelector, attributesData) {
        let attrNodeCollection = [];
        const attrTpl = $(cardAttributeTemplateSelector).html();

        $.each(attributesData, function(name, value) {
            const attrNode = $(attrTpl).clone();
            attrNode.data("attribute", name);
            attrNode.prepend(name);
            attrNode.find(cardAttributeValueSelector).html(value);
            attrNodeCollection.push(attrNode);
        });

        return attrNodeCollection;
    };

    const getAttributesWrapper = function(playerSelector) {
        return $(playerSelector).find(cardAttributesWrapperSelector);
    };

    const getAttributes = function(playerSelector) {
        return $(playerSelector).find(cardAttributeSelector);
    };

    const enableAttributeSelection = function() {
        let playerSelector = Player.getHumanPlayerSelector();

        const $attributesWrapper = getAttributesWrapper(playerSelector);
        $attributesWrapper.addClass(cardAttributesWrapperActiveClass);

        const $attributes = getAttributes(playerSelector);
        $($attributes)
            .off("click")
            .on("click", function() {
                const $target = $(this);
                $($attributes).removeClass(cardAttributeActiveClass);
                $target.addClass(cardAttributeActiveClass);

                $(document).trigger("game:attributeSelect", {
                    attribute: $target.data("attribute")
                });
            });
    };

    const disableAttributeSelection = function() {
        let playerSelector = Player.getHumanPlayerSelector();

        const $attributesWrapper = getAttributesWrapper(playerSelector);
        $attributesWrapper.removeClass(cardAttributesWrapperActiveClass);

        const $attributes = getAttributes(playerSelector);
        $($attributes).off("click");
    };

    const update = function(playerID, data) {
        let playerSelector = Player.getPlayerSelectorByID(playerID);

        // Set image src and title
        $(playerSelector)
            .find(cardImageSelector)
            .attr("src", getImagePath(data.name))
            .attr("title", data.name);

        // Set card title
        $(playerSelector)
            .find(cardTitleSelector)
            .html(data.name);

        // Get attributes html
        const attrNodeCollection = createAttributesNodes(
            playerSelector,
            data.attributes
        );

        // Set attributes
        $(playerSelector)
            .find(cardAttributesWrapperSelector)
            .append(attrNodeCollection);

        $(document).trigger("game.cardsShown");
    };

    init();

    return {
        update,
        enableAttributeSelection,
        disableAttributeSelection,
        highlightAttribute
    };
})();
