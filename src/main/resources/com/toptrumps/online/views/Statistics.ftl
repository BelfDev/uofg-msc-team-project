<#include "_parts/Header.ftl">

	<main class="statistics-page">
		<div class="statistics-page-top-panel">
			<a href="/toptrumps/" class="button button--outlined">Go back</a>
			<h1 class="game-title">Statistics</h1>
			<a href="javascript:void(0)" class="button js-modal-new-game-button">New game</a>
		</div>
		<div class="statistics-total">
			<div class="statistics-total__item">
				Games Played
				<span class="statistics-total__item-value js-stats-games-played-value">0</span>
			</div>
			<div class="statistics-total__item">
				AI Wins
				<span class="statistics-total__item-value js-stats-ai-wins-value">0</span>
			</div>
			<div class="statistics-total__item">
				Human Wins
				<span class="statistics-total__item-value js-stats-human-wins-value">0</span>
			</div>
			<div class="statistics-total__item">
				Average Draws
				<span class="statistics-total__item-value js-stats-average-draws-value">0</span>
			</div>
			<div class="statistics-total__item">
				Rounds Record
				<span class="statistics-total__item-value js-stats-rounds-record-value">0</span>
			</div>
		</div>

		<table class="statistics-table" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<th>Game ID</th>
					<th>Winner</th>
					<th>Rounds</th>
					<th>Human wins</th>
					<th>AI 1 wins</th>
					<th>AI 2 wins</th>
					<th>AI 3 wins</th>
					<th>AI 4 wins</th>
				</tr>
			</thead>
			<tbody class="js-stats-body"></tbody>
		</table>
	</main>

	<#include "_parts/OpponentsSelection.ftl">

	<script id="template-stats-row" type="text/template">
		<tr>
			<td class="js-game-id-value"></td>
			<td class="js-winner-name"></td>
			<td class="js-total-rounds-value"></td>
			<td class="js-human-wins"></td>
			<td class="js-aione-wins"></td>
			<td class="js-aitwo-wins"></td>
			<td class="js-aithree-wins"></td>
			<td class="js-aifour-wins"></td>
		</tr>
	</script>

	<script src="/assets/scripts/libs/jquery-3.4.1.min.js"></script>
	<script src="/assets/scripts/config/settings.js"></script>
	<script src="/assets/scripts/helpers/network.js"></script>
	<script src="/assets/scripts/plugins/screen.js"></script>
	<script src="/assets/scripts/plugins/modal.js"></script>
	<script src="/assets/scripts/plugins/input-number.js"></script>
	<script src="/assets/scripts/views/StatisticsView.js"></script>
	<script src="/assets/scripts/StatisticsController.js"></script>
</body>
</html>