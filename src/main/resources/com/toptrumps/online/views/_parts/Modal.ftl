<div class="modal js-modal js-new-game-modal">
    <h2 class="modal__title">Choose the number of AI players</h2>

    <div class="opponents-quantity js-opponents-quantity">
        <input class="opponents-quantity__input js-opponents-count" type="number" min="1" max="4" step="1" value="4">
        <div class="opponents-quantity__nav">
            <div class="opponents-quantity__button opponents-quantity__button--down">-</div>
            <div class="opponents-quantity__button opponents-quantity__button--up">+</div>
        </div>
    </div>

    <button class="button modal__button js-modal-new-game-button">Start game</button>
</div>

<div class="modal js-modal js-round-win-modal">
    <h2 class="modal__title js-modal-title"></h2>
    <p class="modal__hint js-modal-hint"></p>
    <button class="button modal__button js-modal-next-round-button">Next round</button>
</div>

<div class="modal js-modal js-round-draw-modal">
    <h2 class="modal__title js-modal-title"></h2>
    <p class="modal__hint js-modal-hint"></p>
    <button class="button modal__button js-modal-next-round-button">Next round</button>
</div>

<div class="modal js-modal js-end-game-modal">
    <h2 class="modal__title js-modal-title"></h2>
    <div class="modal__hint js-modal-hint"></div>
    <a href="/toptrumps/" class="button modal__button">To main menu</a>
</div>