<div class="modal js-modal js-new-game-modal">
    <h2 class="modal__title">How many players do you want to play against?</h2>

    <div class="opponents-quantity">
        <input class="opponents-quantity__input" type="number" min="1" max="4" step="1" value="1">
    </div>

    <button class="button js-modal-next-round-button">Start game</button>
</div>

<div class="modal js-modal js-round-win-modal">
    <h2 class="modal__title js-modal-title"></h2>
    <button class="button js-modal-next-round-button">Next round</button>
</div>

<div class="modal js-modal js-round-draw-modal">
    <h2 class="modal__title js-modal-title"></h2>
    <p class="modal__hint js-modal-hint"></p>
    <button class="button js-modal-next-round-button">Next round</button>
</div>