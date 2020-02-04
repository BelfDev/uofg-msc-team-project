const Countdown = (function() {
    const init = function() {
        bindEvents();
    };

    const runCountdown = function() {
        $(".js-countdown").addClass("countdown--active");

        let countdown = {
            opacityIn: [0, 1],
            scaleIn: [0.2, 1],
            scaleOut: 3,
            durationIn: 800,
            durationOut: 600,
            delay: 500
        };

        anime
            .timeline()
            .add({
                targets: ".countdown__numbers--1",
                opacity: countdown.opacityIn,
                scale: countdown.scaleIn,
                duration: countdown.durationIn
            })
            .add({
                targets: ".countdown__numbers--1",
                opacity: 0,
                scale: countdown.scaleOut,
                duration: countdown.durationOut,
                easing: "easeInExpo",
                delay: countdown.delay
            })
            .add({
                targets: ".countdown__numbers--2",
                opacity: countdown.opacityIn,
                scale: countdown.scaleIn,
                duration: countdown.durationIn
            })
            .add({
                targets: ".countdown__numbers--2",
                opacity: 0,
                scale: countdown.scaleOut,
                duration: countdown.durationOut,
                easing: "easeInExpo",
                delay: countdown.delay
            })
            .add({
                targets: ".countdown__numbers--3",
                opacity: countdown.opacityIn,
                scale: countdown.scaleIn,
                duration: countdown.durationIn
            })
            .add({
                targets: ".countdown__numbers--3",
                opacity: 0,
                scale: countdown.scaleOut,
                duration: countdown.durationOut,
                easing: "easeInExpo",
                delay: countdown.delay
            })
            .add({
                targets: ".countdown",
                opacity: 0,
                duration: 500,
                delay: 500
            });
    };

    const bindEvents = function() {
        $(document).on("countdown:run", function(event, data) {
            runCountdown();

            setTimeout(function() {
                $(".js-countdown").removeClass("countdown--active");
                data.callback();
            }, 6000);
        });
    };

    $(document).ready(function() {
        init();
    });
})();
