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
			<div class="ai-player__hand"><span class="js-player-hand-size">?</span> cards left</div>
		</div>
	</div>
</script>

<#include "_parts/modal.ftl" >

<div class="countdown js-countdown">
	<span class="countdown__numbers countdown__numbers--1">3</span>
	<span class="countdown__numbers countdown__numbers--2">2</span>
	<span class="countdown__numbers countdown__numbers--3">1</span>
</div>

<#include "_parts/footer.ftl">