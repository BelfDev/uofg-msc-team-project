/**
 * Loader animation plugin
 */

const Loader = (($) => {
    /** VARIABLES AND CONSTANTS */

        // Selectors
    const loaderSelector = ".js-loader";
    const loaderTextSelector = ".js-loader-text";

    // CSS classes to represent visual state
    const openedModalClass = "loader--shown";
    const activeLoaderBoxClass = "active-loader"

    // Templates
    const loaderTemplate = `<div class="loader js-loader">
        <div class="loader__spinner"></div>
        <span class="js-loader-text">Loading...</span>
    </div>`;


    /** METHODS */

    /**
     * Shows loader
     */
    const showLoader = ($targetBox, text) => {
        $targetBox.addClass(activeLoaderBoxClass);
        $(document.body).append(loaderTemplate);
        $(loaderTextSelector).text(text);
    };

    /**
     * Removes loader
     */
    const removeLoader = $targetBox => {
        $targetBox.removeClass(activeLoaderBoxClass);
        $(loaderSelector).remove();
    };

    /** EXPOSE PUBLIC METHODS **/

    return {
        removeLoader,
        showLoader,
    }
})(jQuery);