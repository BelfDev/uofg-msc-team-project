const Modal = (function() {
    const modalSelector = ".js-modal";
    const openedModalClass = "modal--opened";

    const openModal = function(targetModalSelector, title, hint) {
        showBackdrop();
        $(targetModalSelector).addClass(openedModalClass);

        if (title)
            $(targetModalSelector)
                .find(".js-modal-title")
                .text(title);
        if (hint)
            $(targetModalSelector)
                .find(".js-modal-hint")
                .text(hint);
    };

    const showBackdrop = function() {
        $(document.body).append(`<div class="backdrop"></div>`);
    };

    const removeBackdrop = function() {
        $(".backdrop").remove();
    };

    const closeActiveModal = function() {
        removeBackdrop();
        $(modalSelector).removeClass(openedModalClass);
    };

    return {
        openModal,
        closeActiveModal
    };
})();
