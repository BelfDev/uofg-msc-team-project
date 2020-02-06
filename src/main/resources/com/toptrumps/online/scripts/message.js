const Message = (function() {
    const init = function() {
        bindEvents();
    };

    const bindEvents = function() {
        $(document).on("message:log", function(event, data) {
            updateGameLog(data.content);
        });
    };

    const updateGameLog = function(content) {
        const $gameStatusBox = $(".js-game-log");
        $gameStatusBox.html(content);
    };

    init();
})();
