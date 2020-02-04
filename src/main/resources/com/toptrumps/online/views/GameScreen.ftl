<#include "_parts/header.ftl">

<main class="game-page">
	<#include "_parts/game-status.ftl">
	<#include "_parts/opponents.ftl">

	<div>
		<#include "_parts/human-player.ftl">
		<#include "_parts/action-panel.ftl">
	</div>
</main>

<script id="template-attribute-item" type="text/template">
	<div class="pcard__char js-card-char"><span class="pcard__char-value js-card-char-value"></span></div>
</script>

<div class="countdown js-countdown">
	<span class="countdown__numbers countdown__numbers--1">3</span>
	<span class="countdown__numbers countdown__numbers--2">2</span>
	<span class="countdown__numbers countdown__numbers--3">1</span>
</div>

<#include "_parts/footer.ftl">