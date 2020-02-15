$(function() {
    const inputNumberSelector = ".js-opponents-quantity";
    const newGameButtonSelector = ".js-modal-new-game-button";

    Screen.init();
    InputNumber.init(inputNumberSelector);

    $(newGameButtonSelector).on("click", () => {
        DOMHelper.showModal("ASK_FOR_NUMBER_OF_OPPONENTS", false, false)
    });
});