const Logger = (() => {
    const output = (title, method, data) => {
        if (window.APP.TEST_MODE) {
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