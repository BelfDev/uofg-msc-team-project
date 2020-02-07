const Card = (function() {
    const cardSelector = ".js-card";
    const cardActiveClass = "pcard--shown";
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
            console.log("Event: card:attributeSelection");
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
        console.log("Method: highlightAttribute");
        $(cardAttributeSelector).removeClass(cardAttributeActiveClass);
        const $cards = $(cardAttributeSelector)
            .filter(function() {
                // console.log($(this).data("attribute"), attribute);
                return $(this).data("attribute") === attribute.name;
            })
            .addClass(cardAttributeActiveClass);
    };

    const createAttributesNodes = function(playerSelector, attributesData) {
        let attrNodeCollection = [];
        const attrTpl = $(cardAttributeTemplateSelector).html();

        $.each(attributesData, function(i, data) {
            const attrNode = $(attrTpl).clone();
            attrNode.data("attribute", data.name);
            attrNode.prepend(data.name);
            attrNode.find(cardAttributeValueSelector).html(data.value);
            attrNodeCollection.push(attrNode);
        });

        return attrNodeCollection;
    };

    const getAttributesWrapper = function(playerSelector) {
        // console.log("Method: getAttributesWrapper");
        return $(playerSelector).find(cardAttributesWrapperSelector);
    };

    const getAttributes = function(playerSelector) {
        // console.log("Method: getAttributes");
        return $(playerSelector).find(cardAttributeSelector);
    };

    const enableAttributeSelection = function() {
        console.log("Method: enableAttributeSelection");
        let playerSelector = Player.getPlayerSelectorByID(0);

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
                    name: $target.data("attribute"),
                    value: $target.find(".js-card-char-value").text()
                });
            });
    };

    const disableAttributeSelection = function() {
        console.log("Method: disableAttributeSelection");
        let playerSelector = Player.getPlayerSelectorByID(0);

        const $attributesWrapper = getAttributesWrapper(playerSelector);
        $attributesWrapper.removeClass(cardAttributesWrapperActiveClass);

        const $attributes = getAttributes(playerSelector);
        $($attributes).off("click");
    };

    const update = function(playerID, data) {
        console.log("Method: update card");
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
            .html(attrNodeCollection);

        const $card = $(playerSelector).find(cardSelector);

        $card.addClass(cardActiveClass);

        animateCard($card, playerID);
    };

    const reset = function(playerID) {
        console.log("Method: reset card");
        let playerSelector = Player.getPlayerSelectorByID(playerID);

        const $card = $(playerSelector).find(cardSelector);
        animateCard($card, playerID);

        $card.removeClass(cardActiveClass);
    };

    const animateCard = function($card, playerID) {
        var card = Player.getPlayerSelectorByID(playerID).find(".js-card")[0];

        const options = {
            targets: card,
            scale: [{ value: 1 }, { value: 1.3 }, { value: 1, delay: 250 }],
            rotateY: { value: "+=180", delay: 200 },
            easing: "easeInOutSine",
            duration: 400
        };

        anime(options);
    };

    init();

    return {
        update,
        reset,
        enableAttributeSelection,
        disableAttributeSelection,
        highlightAttribute
    };
})();
