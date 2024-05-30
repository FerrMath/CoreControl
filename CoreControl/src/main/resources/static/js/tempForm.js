const submitBtn = document.getElementById('save-btn');

submitBtn.addEventListener('click', e => {
    e.preventDefault();
    let form = document.getElementById('product-form');
    let formData = new FormData(form);
    let data = {};
    data['id'] = Date.now();
    formData.forEach((value, key) => {
        data[key] = value;
    });
    alert("Product saved!\nNot really, but the data is: \n" + JSON.stringify(data, null, 2) + "\n\nNow you will be redirected to the home page.");
    form.submit();
});