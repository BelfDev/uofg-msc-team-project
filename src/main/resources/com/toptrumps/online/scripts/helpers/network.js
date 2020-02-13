const NetworkHelper = (($) => {
    /** VARIABLES AND CONSTANTS */
    const REST_API_URL = "http://localhost:7777/toptrumps";

    /** METHODS */
    const makeRequest = (url, data, type) => {
        const parameters = {}
        return $.ajax({
            url: `${REST_API_URL}/${url}`,
            type: type || "POST",
            data: data ? JSON.stringify(data) : {},
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        });
    };

    const checkIfResourceExists = url => {
        return $.ajax({
            url: url,
            type: 'HEAD'
        }).then(function() {
            return true;
        }).catch(function() {
            return false;
        })
    };

    return {
        makeRequest,
        checkIfResourceExists,
    }
})(jQuery);
