const Message = (function() {
    const bindEvents = function() {
        $(document).on("newGame", function(data) {
            console.log(data.content);
        });
    };
})();
