/**
 * This module creates a custom number increment/decrement element
 * instead of native input number
 */

const InputNumber = (($) => {
    /** VARIABLES AND CONSTANTS */

    // Selectors
    const inputNumberSelector = ".js-opponents-quantity";
    const inputSelector = ".opponents-quantity__input";
    const buttonUpSelector = ".opponents-quantity__button--up";
    const buttonDownSelector = ".opponents-quantity__button--down";


    /** METHODS */

    /**
     * Initialization
     * @param selector
     */
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

    /**
     * Bind events for element
     * @param elements
     * @param data
     */
    const bindEvents = (elements, data) => {
        // Plus button click event
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

        // Minus button click event
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

    /** RUN AFTER PAGE IS LOADED **/
    $(function() {
        InputNumber.init(inputNumberSelector);
    });


    /** EXPOSE PUBLIC METHODS **/

    return {
        init
    }
})(jQuery);