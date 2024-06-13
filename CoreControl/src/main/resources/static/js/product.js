const delProductForm = document.getElementById('delete-product-form');
const delProductSubmit = document.querySelectorAll('.delete-product-submit');

delProductSubmit.forEach((button) => {
    button.addEventListener("click", e => {
        e.preventDefault();
        let conf = confirm("Are you sure you want to delete this product?");
        if (conf) {
            delProductForm.submit();
        }
        else {
            return;
        }
    });
});