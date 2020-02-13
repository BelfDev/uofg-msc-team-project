const Countdown = (($) => {
    /** VARIABLES AND CONSTANTS */
    let countdownSelector;
    let $countdown;

    // Selectors
    const countdownNumberOneSelector = ".js-countdown-number-1";
    const countdownNumberTwoSelector = ".js-countdown-number-2";
    const countdownNumberThreeSelector = ".js-countdown-number-3";

    // CSS classes to represent visual state
    const countdownActiveClass = "countdown--active";


    /** METHODS */

    const init = selector => {
        countdownSelector = selector;
        $countdown = $(selector);
    };

    const run = callback => {
        $countdown.addClass(countdownActiveClass);

        if (window.TEST_MODE) {
            callback();
        } else {
            animateCountdown(callback);
        }
    };

    const animateCountdown = (callback) => {
        let countdown = {
            opacityIn: [0, 1],
            scaleIn: [0.2, 1],
            scaleOut: 3,
            durationIn: DOMHelper.timerBase / 2,
            durationOut: DOMHelper.timerBase / 3,
            delay: DOMHelper.timerBase / 10
        };

        anime
            .timeline({
                complete: () => {
                    $countdown.removeClass("countdown--active");
                    callback();
                }
            })
            .add({
                targets: countdownNumberOneSelector,
                opacity: countdown.opacityIn,
                scale: countdown.scaleIn,
                duration: countdown.durationIn
            })
            .add({
                targets: countdownNumberOneSelector,
                opacity: 0,
                scale: countdown.scaleOut,
                duration: countdown.durationOut,
                easing: "easeInExpo",
                delay: countdown.delay
            })
            .add({
                targets: countdownNumberTwoSelector,
                opacity: countdown.opacityIn,
                scale: countdown.scaleIn,
                duration: countdown.durationIn
            })
            .add({
                targets: countdownNumberTwoSelector,
                opacity: 0,
                scale: countdown.scaleOut,
                duration: countdown.durationOut,
                easing: "easeInExpo",
                delay: countdown.delay
            })
            .add({
                targets: countdownNumberThreeSelector,
                opacity: countdown.opacityIn,
                scale: countdown.scaleIn,
                duration: countdown.durationIn
            })
            .add({
                targets: countdownNumberThreeSelector,
                opacity: 0,
                scale: countdown.scaleOut,
                duration: countdown.durationOut,
                easing: "easeInExpo",
                delay: countdown.delay
            })
            .add({
                targets: countdownSelector,
                opacity: 0,
                duration: DOMHelper.timerBase / 2,
                delay: DOMHelper.timerBase / 2
            });
    };

    return {
        init,
        run
    }

})(jQuery);
