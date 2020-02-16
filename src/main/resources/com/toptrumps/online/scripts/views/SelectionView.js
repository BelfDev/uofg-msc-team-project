/**
 * View for selection screen
 */

const SelectionView = (($) => {
    /** VARIABLES AND CONSTANTS */
    const newGameButtonSelector = ".js-modal-new-game-button";

    // Modals
    const modalSelectors = {
        ASK_FOR_NUMBER_OF_OPPONENTS: ".js-new-game-modal",
    };

    /** METHODS */


    /**
     * Initialization
     */
    const init = () => {
        bindEvents();
    }

    /**
     * Event binding
     */
    const bindEvents = () => {
        $(newGameButtonSelector).on("click", () => {
            showModal("ASK_FOR_NUMBER_OF_OPPONENTS", false, false)
        });
    }

    /**
     * Reveals modal window with specific ID and configurable title and text
     * @param selectorID - modal selector, matches one of the modalSelectors properties
     * @param title - modal title
     * @param hint - additional text
     */
    const showModal = (selectorID, title, hint) => {
        const targetModalSelector = modalSelectors[selectorID];

        Modal.openModal(targetModalSelector, title, hint);
    };

    /** EXPOSE PUBLIC METHODS **/
    return {
        init
    }
})(jQuery);