/**
 * Module makes adjustments to enable UI dynamic resizing
 *
 * This module primary function is to calculate root font-size
 * after window resize. When this font-size is calculated, it
 * is applied to html root element and used in REM measurements
 * in CSS. That approach provides the ability to scale UI
 * according to the device screen size
 */

const Screen = (($) => {
    /** VARIABLES AND CONSTANTS */

    // Constants
    const BASE_WIDTH = 1920;
    const MIN_WIDTH = 800;
    const BASE_SIZE = 10;


    /** METHODS */


    /**
     * Initialization
     */
    const init = () => {
        runScreenResize();
        bindEvents();
    };

    /**
     * Event binding
     */
    const bindEvents = () => {
        $(window).on("resize", () => {
            runScreenResize();
        });
    };

    /**
     * Resize screen
     */
    const runScreenResize = () => {
        // If window size is larger than minimal size - change root font size
        if ($(window).innerWidth() > MIN_WIDTH) {
            changeBaseFontSize();
        }
    }

    /**
     * Gets new font size and sets it for root html element
     */
    const changeBaseFontSize = () => {
        const size = calculateFontSize();
        setPageFontSize(size);
    };

    /**
     * Gets target width and tweaks font size accordingly
     * @returns {string} Font-size string in px
     */
    const calculateFontSize = () => {
        const targetWidth = getTargetWidth();

        return (targetWidth / BASE_WIDTH) * BASE_SIZE + "px"
    };

    /**
     * Compares constants and current window width and
     * returns the lowest
     * @returns {number}
     */
    const getTargetWidth = () => {
        return Math.min($(window).innerWidth(), BASE_WIDTH)
    };

    /**
     * Sets font size for root html element
     * @param size
     */
    const setPageFontSize = (size) => {
        $(document.documentElement).css("font-size", size);
    };

    /** RUN AFTER PAGE IS LOADED **/
    $(function() {
        Screen.init();
    });


    /** EXPOSE PUBLIC METHODS **/

    return {
        init
    }
})(jQuery);