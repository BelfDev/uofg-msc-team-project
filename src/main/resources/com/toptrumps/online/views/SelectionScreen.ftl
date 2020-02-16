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

        <#include "_parts/OpponentsSelection.ftl">
    </main>

    <script src="/assets/scripts/libs/jquery-3.4.1.min.js"></script>
    <script src="/assets/scripts/config/settings.js"></script>
    <script src="/assets/scripts/plugins/screen.js"></script>
    <script src="/assets/scripts/plugins/modal.js"></script>
    <script src="/assets/scripts/plugins/input-number.js"></script>
    <script src="/assets/scripts/views/SelectionView.js"></script>
    <script src="/assets/scripts/SelectionController.js"></script>
</body>
</html>