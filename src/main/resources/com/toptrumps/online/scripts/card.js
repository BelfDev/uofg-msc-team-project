const Card = (function() {
    const cardAttributeSelector = ".js-card-char";
    const cardAttributeValueSelector = ".js-card-char-value";
    const cardAttributesWrapperSelector = ".js-card-chars";
    const cardImageSelector = ".js-card-image";
    const cardTitleSelector = ".js-card-title";

    const getImagePath = function(cardName) {
        const imageName = cardName.replace(" ", "_").toLowerCase();
        return `/assets/images/${imageName}.png`;
    };

    const getAttributes = function(playerSelector, attributesData) {
        let attrNodeCollection = [];
        const attrTpl = $(playerSelector).find(cardAttributeSelector);

        $.each(attributesData, function(name, value) {
            const attrNode = $(attrTpl).clone();
            attrNode.prepend(name);
            attrNode.find(cardAttributeValueSelector).html(value);
            attrNodeCollection.push(attrNode);
        });

        return attrNodeCollection;
    };

    const update = function(playerID, data) {
        let playerSelector = Player.getPlayerSelector(playerID);

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
        const attrNodeCollection = getAttributes(
            playerSelector,
            data.attributes
        );

        // Set attributes
        $(playerSelector)
            .find(cardAttributesWrapperSelector)
            .append(attrNodeCollection);
    };

    return {
        update
    };
})();
