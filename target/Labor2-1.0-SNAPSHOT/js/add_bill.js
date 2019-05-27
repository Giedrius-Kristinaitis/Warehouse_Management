var _my_bill_id = 0;

function addHtml() {
    // find the dynamic content div
    var content = document.querySelector("#dynamic-content");

    // make it visible if it is not
    if (content.style.display === 'none') {
        content.style.display = 'initial';
    }

    // add a new div that will hold single bill
    var bill = document.createElement('div');

    bill.classList.add('dynamic_item');

    bill.innerHTML = '<h5>New Bill</h5>' +
        '<input type="text" class="form-control" name="billName' + _my_bill_id + '" placeholder="Name"/>' +
        '<input type="text" class="form-control" name="billDescription' + _my_bill_id + '" placeholder="Description"/>' +
        '<input type="text" class="form-control" name="billAmount' + _my_bill_id + '" placeholder="Amount"/>';

    // create and add a remove bill button
    var removeButton = document.createElement('div');
    removeButton.setAttribute('role', 'button');
    removeButton.classList.add('btn');
    removeButton.classList.add('btn-dark');
    removeButton.innerHTML = 'Remove This Bill';

    // add a click listener to the remove button
    removeButton.addEventListener('click', function() {
        content.removeChild(bill);
    });

    bill.appendChild(removeButton);

    // increment bill id
    _my_bill_id++;

    // append the bill
    content.appendChild(bill);
}

// execute code only when the content is done loading
window.addEventListener('load', function() {
    var button = document.querySelector("#addBill");

    button.addEventListener('click', function() {
       addHtml();
    });
});