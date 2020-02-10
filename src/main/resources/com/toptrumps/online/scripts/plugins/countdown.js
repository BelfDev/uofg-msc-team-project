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

        animateCountdown(callback);
    };

    const animateCountdown = (callback) => {
        let countdown = {
            opacityIn: [0, 1],
            scaleIn: [0.2, 1],
            scaleOut: 3,
            durationIn: 500,
            durationOut: 300,
            delay: 100
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
                duration: 500,
                delay: 500
            });
    };

    return {
        init,
        run
    }

})(jQuery);
