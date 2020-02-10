<#include "_parts/Header.ftl">

	<div class="js-page-curtain page-curtain"></div>

	<main class="game-page">
		<#include "_parts/GameStatus.ftl">
		<#include "_parts/Opponents.ftl">

		<div class="human-player-panel">
			<#include "_parts/HumanPlayer.ftl">
			<#include "_parts/ActionPanel.ftl">
		</div>
	</main>

	<#include "_parts/Template.ftl">

	<#include "_parts/Modal.ftl">

	<#include "_parts/Countdown.ftl">

	<script src="/assets/scripts/libs/jquery-3.4.1.min.js"></script>
	<script src="/assets/scripts/libs/anime.min.js"></script>
	<script src="/assets/scripts/plugins/screen.js"></script>
	<script src="/assets/scripts/helpers/stats.js"></script>
	<script src="/assets/scripts/helpers/dom.js"></script>
	<script src="/assets/scripts/helpers/network.js"></script>
	<script src="/assets/scripts/plugins/input-number.js"></script>
	<script src="/assets/scripts/plugins/message.js"></script>
	<script src="/assets/scripts/plugins/modal.js"></script>
	<script src="/assets/scripts/plugins/countdown.js"></script>
	<script src="/assets/scripts/models/player.js"></script>
	<script src="/assets/scripts/game.js"></script>
	<script>
		$(function() {
			Screen.init()
			Game.init()
		});
	</script>
</body>
</html>