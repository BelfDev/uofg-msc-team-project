/**
 * This module is responsible for animating countdown
 */

const Countdown = (($) => {
    /** VARIABLES AND CONSTANTS */

    // Selectors
    const countdownSelector = ".js-countdown";
    const countdownNumberOneSelector = ".js-countdown-number-1";
    const countdownNumberTwoSelector = ".js-countdown-number-2";
    const countdownNumberThreeSelector = ".js-countdown-number-3";

    // CSS classes to represent visual state
    const countdownActiveClass = "countdown--active";

    // Animation variables
    const timerBase = window.APP.TIMER_BASE;
    const options = {
        opacityIn: [0, 1],
        scaleIn: [0.2, 1],
        scaleOut: 3,
        durationIn: timerBase / 3,
        durationOut: timerBase / 5,
        delay: timerBase / 15
    };

    /** METHODS */

    /**
     * Method to run animation with callback after it finishes
     * @param callback
     */
    const run = callback => {
        const $countdown = $(countdownSelector);
        $countdown.addClass(countdownActiveClass);

        // If test mode enabled - run callback immediately
        if (window.TEST_MODE) {
            callback();
        } else {
            animateCountdown($countdown, callback);
        }
    };

    /**
     * Animates countdown
     * Three numbers appear one after another
     * @param $countdown
     * @param callback
     */
    const animateCountdown = ($countdown, callback) => {
        anime
            .timeline({
                complete: () => {
                    $countdown.removeClass("countdown--active");
                    callback();
                }
            })
            .add({
                targets: countdownNumberOneSelector,
                opacity: options.opacityIn,
                scale: options.scaleIn,
                duration: options.durationIn
            })
            .add({
                targets: countdownNumberOneSelector,
                opacity: 0,
                scale: options.scaleOut,
                duration: options.durationOut,
                easing: "easeInExpo",
                delay: options.delay
            })
            .add({
                targets: countdownNumberTwoSelector,
                opacity: options.opacityIn,
                scale: options.scaleIn,
                duration: options.durationIn
            })
            .add({
                targets: countdownNumberTwoSelector,
                opacity: 0,
                scale: options.scaleOut,
                duration: options.durationOut,
                easing: "easeInExpo",
                delay: options.delay
            })
            .add({
                targets: countdownNumberThreeSelector,
                opacity: options.opacityIn,
                scale: options.scaleIn,
                duration: options.durationIn
            })
            .add({
                targets: countdownNumberThreeSelector,
                opacity: 0,
                scale: options.scaleOut,
                duration: options.durationOut,
                easing: "easeInExpo",
                delay: options.delay
            })
            .add({
                targets: countdownSelector,
                opacity: 0,
                duration: timerBase / 2,
                delay: 0
            });
    };


    /** EXPOSE PUBLIC METHODS **/

    return {
        run
    }

})(jQuery);
