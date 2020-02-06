const Counter = (function() {
    const init = function() {
        const $elements = $(".opponents-quantity");

        $elements.each(function() {
            const $el = $(this);
            const $input = $elements.find(".opponents-quantity__input");
            const template = $(`<div class="opponents-quantity__nav">
                <div class="opponents-quantity__button opponents-quantity__button--up">+</div>
                <div class="opponents-quantity__button opponents-quantity__button--down">-</div>
            </div>`).insertAfter($input);

            const $btnUp = $el.find(".opponents-quantity__button--up");
            const $btnDown = $el.find(".opponents-quantity__button--down");
            const min = $input.attr("min");
            const max = $input.attr("max");

            $btnUp.click(function() {
                let newVal;
                const oldValue = parseFloat($input.val());

                if (oldValue >= max) {
                    newVal = oldValue;
                } else {
                    newVal = oldValue + 1;
                }

                $input.val(newVal);
                $input.trigger("change");
            });

            $btnDown.click(function() {
                let newVal;
                const oldValue = parseFloat($input.val());

                if (oldValue <= min) {
                    newVal = oldValue;
                } else {
                    newVal = oldValue - 1;
                }

                $input.val(newVal);
                $input.trigger("change");
            });
        });
    };

    init();
})();
