"use strict";

let THROWS = {
    rock: "rock",
    paper: "paper",
    scissors: "scissors",
}

const THROWS_DATA = [
    {
        title: "Rock",
        key: THROWS.rock,
        image: "assets/rock.svg"
    },
    {
        title: "Paper",
        key: THROWS.paper,
        image: "assets/paper.svg"
    },
    {
        title: "Scissors",
        key: THROWS.scissors,
        image: "assets/scissors.svg"
    }
];

function onThrow(key) {
    let throwData = THROWS_DATA.filter(item => key === item.key)[0];
    if (throwData && throwData.key) {
        createModal(throwData);
        getServersThrow(throwData.key);
    }
}

function createModal(throwData) {
    let modal = `<div class="modal" id="modal">
        <div class="modal__overlay" onclick="closeModal()"></div>
            <div class="modal__content">
                <button class="modal__close" onclick="closeModal()">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 21.414 21.414">
                        <g class="close-a" transform="translate(-1195.479 -108.479)">
                            <line class="close-b" x2="28.284" transform="translate(1196.186 109.186) rotate(45)"/>
                            <line class="close-b" x2="28.284" transform="translate(1196.186 129.186) rotate(-45)"/>
                        </g>
                    </svg>
                </button>

                <div class="modal__title">
                    <h2 id="modal-title">WAITING CURB’S CHOOSE</h2>
                </div>

                <div class="modal__body" id="modal-body">
                    <div class="throw-waiting">
                        <div class="throw__item no-hover">
                            <div class="throw__img">
                                 <img src="${throwData.image}"/>
                            </div>
                            <h3>${throwData.title}</h3>
                        </div>
                        <div class="throw__item no-hover" id="loading">
                            <div class="throw__img">
                                <img src="assets/loading.gif"/>
                            </div>
                            <h3>CURB</h3>
                        </div>
                    </div>
                </div>
                <div id="footer"></div>
            </div>
        </div>`

    document.querySelector("body").innerHTML += modal
}

function getServersThrow(userThrow){
    fetch(`/api/v1/rps?throw=${userThrow}`).then(function (response) {
        return response.json()
    }).then(function (data) {
        console.log(data);
        onSuccessResponse(data)
    }).catch(function (err) {
        console.warn('Something went wrong.', err)
    });
}

function onSuccessResponse(data){
    let payload = data.payload
    let serverThrow = payload.serversThrow
    let throwData = THROWS_DATA.filter(item => serverThrow === item.key)[0];
    if (throwData && throwData.key) {
        setModalBody(generateThrowItem(throwData))
        addModalFooter()
        setModalTitle(`YOU ${payload.result}`)
    }


}

function closeModal() {
    if (document.getElementById('modal')) {
        document.getElementById('modal').remove();
    }
}

function setModalBody(modalBody) {
    if (document.getElementById('modal-body')) {
        document.getElementById('modal-body').innerHTML = modalBody;
    }
}

function addModalFooter() {
    if (document.getElementById('footer')) {
        document.getElementById('footer').innerHTML = `<div class="modal__footer">
                            <button class="modal__button" onclick="closeModal()">Ok</button>
                        </div>`;
    }
}
function setModalTitle(title){
    if (document.getElementById('modal-title')) {
        document.getElementById('modal-title').innerHTML = title
    }
}

function generateThrowItem(throwData){
    return `<div class="throw-waiting">
            <div class="throw__item no-hover">
                <div class="throw__img">
                    <img src="${throwData.image}"/>
                </div>
                <h3>${throwData.title}</h3>
            </div>
            </div>`
}
