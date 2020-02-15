const Message = (($) => {
    const gameStatusSelector = '.js-game-status-content';
    const gameStatusLogSelector = ".js-game-log";
    const gameLetterSelector = ".game-status__letter";
    const gameLetterClass = "game-status__letter";

    const animateLog = () => {
        // Wrap every letter in a span
        let textWrapper = $(gameStatusLogSelector);
        const text = textWrapper.text().replace(/([^\x00-\x20]|\w)/g, `<span class="${gameLetterClass}">$&</span>`);
        $(gameStatusLogSelector).html(text);

        anime.timeline()
            .add({
                targets: gameStatusSelector,
                opacity: 1,
                duration: 100,
                easing: "easeOutExpo",
            })
            .add({
                targets: gameLetterSelector,
                opacity: [0,1],
                easing: "easeOutExpo",
                duration: 300,
                offset: '-=775',
                delay: (el, i) => 34 * (i+1)
            })
    };

    return {
        animateLog
    }
})(jQuery);