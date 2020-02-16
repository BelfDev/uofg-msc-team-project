/**
 * Controller for selection screen
 */

const SelectionController = (($) => {
    /** METHODS */

    /**
     * Initialization
     */
    const init = view => {
        view.init();
    }

    /** RUN AFTER PAGE IS LOADED **/
    $(function() {
        SelectionController.init(SelectionView);
    });


    /** EXPOSE PUBLIC METHODS **/
    return {
        init
    }
})(jQuery);