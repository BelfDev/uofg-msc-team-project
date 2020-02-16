/**
 * Helper to provide common method for ajax requests
 */

const NetworkHelper = (($) => {

    /** METHODS */

    /**
     * Wrapper function for jQuery ajax method
     * @param url
     * @param data
     * @param type - by default POST
     * @returns {jqXHR}
     */
    const makeRequest = (url, data, type) => {
        return $.ajax({
            url: `${window.APP.REST_API_BASE_URL}/${url}`,
            type: type || "POST",
            data: data ? JSON.stringify(data) : {},
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).catch(() => {
            alert("Network problem! Check your connection and try again");
            window.location.replace(window.APP.BASE_URL);
        });
    };

    /**
     * Finds URL parameter value by name
     * @param parameterName
     * @returns {string}
     */
    const getCurrentURLParameterValue = parameterName => {
        const url = new URL(window.location.href);
        return url.searchParams.get(parameterName);
    };

    /**
     * Sends simple HEAD request to verify if resource exists
     * @param url
     * @returns {jqXHR}
     */
    const checkIfResourceExists = url => {
        return $.ajax({
            url: url,
            type: 'HEAD',
        }).then(() => true).catch(() => false);
    };


    /** EXPOSE PUBLIC METHODS **/

    return {
        makeRequest,
        checkIfResourceExists,
        getCurrentURLParameterValue
    };
})(jQuery);
