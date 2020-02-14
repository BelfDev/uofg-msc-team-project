const Logger = (() => {
    const output = (title, method, data) => {
        if (window.APP.ENABLE_LOGGER) {
            console.log(`Method: ${method}`);
            console.log(`${title}: `);
            console.log(data)
            console.log("=================");
        }
    };

    return {
        output
    }
})();