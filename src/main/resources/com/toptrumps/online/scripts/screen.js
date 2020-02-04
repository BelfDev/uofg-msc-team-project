const Screen = (function() {
    let $html;
    let $window;
    const baseWidth = 1920;
    const minWidth = 800;
    const baseSize = 10;

    const init = function() {
        $html = $(document.documentElement);
        $window = $(window);

        bindEvents();
        changeBaseFontSize();
    };

    const bindEvents = function() {
        $window.on("resize", function() {
            changeBaseFontSize();
        });
    };

    const changeBaseFontSize = function() {
        if ($window.innerWidth() < minWidth) return false;

        const size = calcFontSize();
        setFontSize(size);
    };

    const calcFontSize = function() {
        const targetWidth = Math.min($window.innerWidth(), baseWidth);
        const size = (targetWidth / baseWidth) * baseSize + "px";
        return size;
    };

    const setFontSize = function(size) {
        $html.css("font-size", size);
    };

    init();
})();
