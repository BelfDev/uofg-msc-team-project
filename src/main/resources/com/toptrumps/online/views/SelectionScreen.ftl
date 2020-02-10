<#include "_parts/Header.ftl">

<main class="selection-page">
    <div class="selection-container">
        <h1 class="game-title">Top Trumps</h1>
        <h2 class="game-subtitle"> The Witcher</h2>
		<div class="button-container">
			<a class="button" href="/toptrumps/game">New Game</a>
			<a class="button button--outlined" href="/toptrumps/stats">Statistics</a>
		</div>
    </div>
</main>

    <script src="/assets/scripts/libs/jquery-3.4.1.min.js"></script>
    <script src="/assets/scripts/plugins/screen.js"></script>
    <script>
        $(function() {
            Screen.init()
        });
    </script>
</body>
</html>