const NetworkHelper = (($) => {
    /** VARIABLES AND CONSTANTS */
    const REST_API_URL = "http://localhost:7777/toptrumps";

    /** METHODS */
    const makeRequest = (url, data, type) => {
        return $.ajax({
            async: false,
            url: `${REST_API_URL}/${url}`,
            type: type || "POST",
            data: data ? JSON.stringify(data) : {},
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).always(response => {
            Logger.output(`Response from request to /${url}`, "makeRequest", response);
        });
    };

    const getCurrentURLParameterValue = parameterName => {
        const url = new URL(window.location.href);
        return url.searchParams.get(parameterName)
    }

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
        getCurrentURLParameterValue
    }
})(jQuery);
