const Modal = (($) => {
    /** VARIABLES AND CONSTANTS */
    // Selectors
    const modalSelector = ".js-modal";
    const modalTitleSelector = ".js-modal-title";
    const modalHintSelector = ".js-modal-hint";
    const closeButtonSelector = ".js-modal-close";

    // CSS classes to represent visual state
    const openedModalClass = "modal--opened";

    const openModal = (selector, title, hint) => {
        DOMHelper.showBackdrop();
        $(selector).addClass(openedModalClass);

        setModalTitle(selector, title || "");
        setModalHint(selector, hint || "");

        $(closeButtonSelector).one("click", () => {
            closeActiveModal();
        });
    };

    const setModalTitle = (targetModalSelector, title) => {
        if (!title) return;

        $(targetModalSelector)
            .find(modalTitleSelector)
            .html(title);
    };

    const setModalHint = (targetModalSelector, hint) => {
        if (!hint) return;

        $(targetModalSelector)
            .find(modalHintSelector)
            .html(hint);
    };

    const closeActiveModal = () => {
        DOMHelper.removeBackdrop();
        $(modalSelector).removeClass(openedModalClass);
    };

    return {
        openModal,
        closeActiveModal
    }
})(jQuery);