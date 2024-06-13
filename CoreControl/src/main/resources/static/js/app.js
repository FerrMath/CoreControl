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
        const priceElement = document.getElementById('cost');
        const profitElement = document.getElementById('profit');
        const priceSale = document.getElementById('price');

        [priceElement, profitElement, priceSale].forEach((element) => {
            
            element.addEventListener('input', () => {
                const price = priceElement.value;
                const profit = profitElement.value;
                const salePrice = parseFloat(price) + (parseFloat(price) * (parseFloat(profit) / 100));
                priceSale.value = salePrice.toFixed(2);
            });
            
        });
    }
})();