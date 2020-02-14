<#include "_parts/Header.ftl">

    <main class="selection-page">
        <div class="selection-container">
            <h1 class="game-title">Top Trumps</h1>
            <h2 class="game-subtitle"> The Witcher</h2>
            <div class="button-container">
                <button class="button js-modal-new-game-button">New Game</button>
                <a class="button button--outlined" href="/toptrumps/stats">Statistics</a>
            </div>
        </div>

        <form action="/toptrumps/game" method="GET">
            <div class="modal js-modal js-new-game-modal">
                <button type="button" class="modal__close js-modal-close"></button>
                <h2 class="modal__title">Choose the number of AI players</h2>

                <div class="opponents-quantity js-opponents-quantity">
                    <input class="opponents-quantity__input js-opponents-count" name="numberOfOpponents" type="number" min="1" max="4" step="1" value="4">
                    <div class="opponents-quantity__nav">
                        <div class="opponents-quantity__button opponents-quantity__button--down">-</div>
                        <div class="opponents-quantity__button opponents-quantity__button--up">+</div>
                    </div>
                </div>

                <button type="submit" class="button modal__button js-modal-new-game-button">Start game</button>
            </div>
        </form>
    </main>

    <script src="/assets/scripts/libs/jquery-3.4.1.min.js"></script>
    <script src="/assets/scripts/config/settings.js"></script>
    <script src="/assets/scripts/plugins/screen.js"></script>
    <script src="/assets/scripts/plugins/modal.js"></script>
    <script src="/assets/scripts/plugins/input-number.js"></script>
    <script src="/assets/scripts/helpers/dom.js"></script>
    <script src="/assets/scripts/selection.js"></script>
</body>
</html>