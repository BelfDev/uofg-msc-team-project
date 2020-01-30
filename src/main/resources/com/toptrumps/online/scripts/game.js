const Game = (function() {
    const init = function() {
        testAjax();
        bindEvents();
    };

    const bindEvents = function() {
        const $button = $(".js-new-game-button");
        $($button).on("click", function() {
            // ...
            // access button with $(this)
            $(document).trigger("newGame", { content: "test" });
        });
    };

    const testAjax = function() {
        // General AJAX call
        $.ajax({
            type: "GET", // or POST,
            dataType: "text",
            url: `${restAPIurl}/helloWord`,
            data: {
                Word: "Pedro"
            }
        })
            .done(function(data) {
                console.log(data);
            })
            .fail(function(e) {
                console.error("You have an error: ", e);
            });

        // GET shortcut
        $.get(`${restAPIurl}/helloWord`, { Word: "Anton" }, function(data) {
            console.log(data);
        });

        $.getJSON(`${restAPIurl}/helloJSONList`, function(data) {
            console.log(data);
        });

        // POST shortcut
        $.post(
            `${restAPIurl}/helloJSONList`,
            function(data) {
                console.log(data);
            },
            "json"
        );
    };

    return {
        init
    };
})();

$(document).ready(Game.init);
