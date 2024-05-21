(() => {
    addListeners();

    function addListeners() {
        hidderMenus();
    }

    function hidderMenus(){
        const hidden = document.querySelectorAll('.hiden_container_btn');
        hidden.forEach((element) => {
            let ele = element.nextElementSibling;
            console.log(ele);
            element.addEventListener("click", () => {
                ele.classList.toggle('hidden');
            });
        });
    }
})();