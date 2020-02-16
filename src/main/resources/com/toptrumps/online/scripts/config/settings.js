/**
 * Global application settings
 */

const APP = {
    TEST_MODE: true,
    TIMER_BASE: 1000,
    STARTUP_DELAY: 1000,
    NEXT_ROUND_TIMER: 2000,
    ACTIVE_PLAYER_TIMER: 1000,
    BASE_URL: "/toptrumps",
    REST_API_BASE_URL: "/toptrumps/api"
};

if (APP.TEST_MODE) {
    APP.TIMER_BASE = 100;
    APP.NEXT_ROUND_TIMER = 1;
    APP.ACTIVE_PLAYER_TIMER = 1;
}

window.APP = APP;