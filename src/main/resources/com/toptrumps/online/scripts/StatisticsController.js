/**
 * Controller for statistics screen
 */

const StatisticsController = (($) => {
    /** VARIABLES AND CONSTANTS */
    let view;

    /** METHODS */

    /**
     * Initialization
     */
    const init = v => {
        view = v;
        view.init();

        fetchStats();
    }

    /**
     * Fetch statistics from the server and render results
     */
    const fetchStats = () => {
        NetworkHelper.makeRequest("statistics", false, "GET").then(response => {
            view.renderStats(response);
        });
    }

    /** RUN AFTER PAGE IS LOADED **/
    $(function() {
        StatisticsController.init(StatisticsView);
    });


    /** EXPOSE PUBLIC METHODS **/
    return {
        init
    }
})(jQuery);
