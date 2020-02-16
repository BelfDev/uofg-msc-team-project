/**
 * This module is responsible for showing and hiding modal windows
 */

const Modal = (($) => {
    /** VARIABLES AND CONSTANTS */

    // Selectors
    const modalSelector = ".js-modal";
    const modalTitleSelector = ".js-modal-title";
    const modalHintSelector = ".js-modal-hint";
    const closeButtonSelector = ".js-modal-close";
    const backdropSelector = ".js-backdrop";

    // CSS classes to represent visual state
    const openedModalClass = "modal--opened";

    // Templates
    const backdropTemplate = '<div class="backdrop js-backdrop"></div>';


    /** METHODS */

    /**
     * Opens modal with ID (selector) and sets title and text
     * @param selector
     * @param title
     * @param hint - additional text
     */
    const openModal = (selector, title, hint) => {
        showBackdrop();

        $(selector).addClass(openedModalClass);

        setModalTitle(selector, title || "");
        setModalHint(selector, hint || "");

        $(closeButtonSelector).one("click", () => {
            closeActiveModal();
        });
    };

    /**
     * Sets modal title
     * @param targetModalSelector
     * @param title
     */
    const setModalTitle = (targetModalSelector, text) => {
        if (!text) return;

        $(targetModalSelector)
            .find(modalTitleSelector)
            .html(text);
    };

    /**
     * Sets modal hint (additional) text
     * @param targetModalSelector
     * @param text
     */
    const setModalHint = (targetModalSelector, text) => {
        if (!text) return;

        $(targetModalSelector)
            .find(modalHintSelector)
            .html(text);
    };

    /**
     * Closes active modal (only one allowed)
     */
    const closeActiveModal = () => {
        removeBackdrop();
        $(modalSelector).removeClass(openedModalClass);
    };

    /**
     * Shows backdrop (black semi-transparent background)
     */
    const showBackdrop = () => {
        $(document.body).append(backdropTemplate);
    };

    /**
     * Removes backdrop
     */
    const removeBackdrop = () => {
        $(backdropSelector).remove();
    };


    /** EXPOSE PUBLIC METHODS **/

    return {
        closeActiveModal,
        openModal,
        removeBackdrop,
        showBackdrop
    }
})(jQuery);