const InputNumber = (($) => {
    /** VARIABLES AND CONSTANTS */
    const inputSelector = ".opponents-quantity__input";
    const buttonUpSelector = ".opponents-quantity__button--up";
    const buttonDownSelector = ".opponents-quantity__button--down";

    /** METHODS */
    const init = selector => {
        const $elements = $(selector);

        $elements.each((i, el) => {
            const $el = $(el);
            const elements = {
                $input: $el.find(inputSelector),
                $btnUp: $el.find(buttonUpSelector),
                $btnDown: $el.find(buttonDownSelector),
            };

            const data = {
                min: elements.$input.attr("min"),
                max: elements.$input.attr("max"),
            };

            bindEvents(elements, data);
        });
    };

    const bindEvents = (elements, data) => {
        elements.$btnUp.on("click", () => {
            let newValue;
            const oldValue = parseFloat(elements.$input.val());

            if (oldValue >= data.max) {
                newValue = oldValue;
            } else {
                newValue = oldValue + 1;
            }

            elements.$input.val(newValue);
            elements.$input.trigger("change");
        });

        elements.$btnDown.on("click", () => {
            let newValue;
            const oldValue = parseFloat(elements.$input.val());

            if (oldValue <= data.min) {
                newValue = oldValue;
            } else {
                newValue = oldValue - 1;
            }

            elements.$input.val(newValue);
            elements.$input.trigger("change");
        })
    };

    return {
        init
    }
})(jQuery);