const Modal = (($) => {
    /** VARIABLES AND CONSTANTS */
    // Selectors
    const modalSelector = ".js-modal";
    const backdropSelector = ".js-backdrop";
    const modalTitleSelector = ".js-modal-title";
    const modalHintSelector = ".js-modal-hint";
    const closeButtonSelector = ".js-modal-close";

    // Templates
    const backdropTemplate = '<div class="backdrop js-backdrop"></div>';

    // CSS classes to represent visual state
    const openedModalClass = "modal--opened";

    const openModal = (selector, title, hint) => {
        showBackdrop();
        $(selector).addClass(openedModalClass);

        setModalTitle(selector, title || "");
        setModalHint(selector, hint || "");

        $(closeButtonSelector).one("click", () => {
            closeActiveModal();
        });
    };

    const showBackdrop = function() {
        $(document.body).append(backdropTemplate);
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
        removeBackdrop();
        $(modalSelector).removeClass(openedModalClass);
    };

    const removeBackdrop = function() {
        $(backdropSelector).remove();
    };

    return {
        openModal,
        closeActiveModal
    }
})(jQuery);