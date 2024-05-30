(() => {
    addListeners();

    function addListeners() {
        hidderMenus();
        updateSalePrice();
    }

    function hidderMenus(){
        const hidden = document.querySelectorAll('.hiden_container_btn');
        hidden.forEach((element) => {
            let ele = element.nextElementSibling;
            element.addEventListener("click", () => {
                ele.classList.toggle('hidden');
            });
        });
    }

    function updateSalePrice() {
        const priceElement = document.getElementById('price');
        const profitElement = document.getElementById('profit');
        const priceSale = document.getElementById('priceSale');

        [priceElement, profitElement].forEach((element) => {
            if (element === null) return;

            if (element.value === '') {
                priceSale.value = '0.00';
                return;
            }
            
            element.addEventListener('input', () => {
                const price = priceElement.value;
                const profit = profitElement.value;
                const salePrice = parseFloat(price) + (parseFloat(price) * (parseFloat(profit) / 100));
                priceSale.value = salePrice.toFixed(2);
            });
            
        });
    }
})();