const NetworkHelper = (($) => {
    /** VARIABLES AND CONSTANTS */
    const REST_API_URL = "http://localhost:7777/toptrumps";

    /** METHODS */
    const makeRequest = async (url, data, type) => {
        return await fetch(`${REST_API_URL}/${url}`, {
            method: type || "POST",
            mode: 'cors',
            body: data ? JSON.stringify(data) : {},
            processData: false,
            headers: {
                'Content-Type': 'application/json',
            }
        }).then(response => response.text())
        .then(text => {
            const response = text.length ? JSON.parse(text) : {};
            Logger.output(`Response from request to /${url}`, "makeRequest", {data, response});
            return response;
        }).catch(response => {
            Logger.output(`Error from request to /${url}`, "makeRequest", { data, response });
            return response.json();
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
