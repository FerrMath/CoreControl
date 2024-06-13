const modal = document.querySelector("[data-modal]");
const modalSpan = document.querySelector("[data-modal-span]");
const modalForm = document.querySelector("[data-modal-form]");
const modalSubmit = document.querySelector("[data-modal-submit]");
const modalInputs = document.querySelector("[data-modal-inputs]");
const openModal = document.querySelectorAll(".data-modal-open");
const closeModal = document.querySelector("[data-modal-close]");
const cancelBtn = document.querySelector("[data-modal-cancel]");



openModal.forEach((button) => {
    button.addEventListener("click", () => openNewModal(button));
});

closeModal.addEventListener("click", cleanAndCloseModal)
cancelBtn.addEventListener("click", e => {
    e.preventDefault();
    cleanAndCloseModal();
})

window.addEventListener('keydown', (event) => {
    if (event.key === 'Escape' && modal.open) {
        cleanAndCloseModal();
    }
});

function openNewModal(button) {
    setCorretInput(button.value);
    modal.showModal();
}

function cleanAndCloseModal() {
    modalSpan.innerText = "";
    modalInputs.innerHTML = "";
    modal.close();
}

function setCorretInput(value) {
    let input = document.createElement("input");
    
    switch (value) {
        case "addDiscount":
            console.log("addDiscount");
            modalSpan.innerText = "Add Discount";
            input.setAttribute("type", "number");
            input.setAttribute("name", "discount");
            input.setAttribute("placeholder", "Discount");
            break;
        case "remDiscount":
            modalSpan.innerText = "Zero the Discount";
            let p = document.createElement("p");
            p.innerText = "Are you sure you want to remove all the discount?";
            modalInputs.appendChild(p);
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "discount");
            input.setAttribute("value", 0.0);
            break;
        case "addStock":
            console.log("addStock");
            modalSpan.innerText = "Add Stock";
            input.setAttribute("type", "number");
            input.setAttribute("name", "stock");
            input.setAttribute("placeholder", "Stock");
            break;
        default:
            return;
    }
    modalInputs.appendChild(input);
}

