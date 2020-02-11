<script id="template-attribute-item" type="text/template">
    <div class="pcard__char js-card-char"><span class="pcard__char-value js-card-char-value"></span></div>
</script>

<script id="template-player-box" type="text/template">
    <div class="ai-player js-player">
        <div class="pcard js-card">
            <div class="pcard__front">
                <div class="pcard__image-box">
                    <img class="pcard__image js-card-image" src="" alt="" />
                </div>
                <div class="pcard__content">
                    <div class="pcard__title js-card-title"></div>
                    <div class="pcard__chars js-card-chars"></div>
                </div>
            </div>
            <div class="pcard__back"></div>
        </div>

        <div class="ai-player__details">
            <div class="ai-player__name js-player-name"></div>
            <div class="ai-player__hand js-player-deck-count"></div>
        </div>
    </div>
</script>

<script id="template-game-over" type="text/template">
    <div class="game-stats-modal">
        <div class="game-stats-modal__winner js-game-over-winner">
            <span class="game-stats-modal__winner-icon"></span>
            <span class="game-stats-modal__winner-name js-game-over-winner-name"></span>
        </div>
        <h3 class="game-stats-modal__header">Rounds won:</h3>
        <div class="game-stats-modal__rounds js-game-over-rounds-wrapper"></div>
    </div>
</script>

<script id="template-rounds-box" type="text/template">
    <div class="game-stats-modal__rounds-box js-game-over-rounds-box">
        <span class="js-game-over-rounds-name"></span>
        <span class="game-stats-modal__rounds-number js-game-over-rounds-number"></span>
    </div>
</script>
